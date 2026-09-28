package com.array64.grassmessage.internal.data;

import com.array64.grassmessage.internal.components.InstantiationContext;
import com.array64.grassmessage.internal.misc.Color;
import com.array64.grassmessage.internal.misc.Evaluation;
import net.kyori.adventure.text.format.TextColor;

import java.util.*;
import java.util.function.BiConsumer;

public class GradientData {
    private final List<ColorStop> colorStops = new ArrayList<>();

    public void addStop(float position, String color) {
        colorStops.add(new ColorStop(position, color));
    }

    public void onEnd() {
        colorStops.sort(Comparator.comparing(ColorStop::position));
        if(colorStops.get(0).position != 0f || colorStops.get(colorStops.size() - 1).position != 1f)
            throw new IllegalStateException("[THIS SHOULD NEVER HAPPEN] Gradients must have a start and end.");
    }

    public void evaluate(int length, BiConsumer<TextColor, Integer> callback, InstantiationContext ctx) {
        ColorStop previous = colorStops.get(0); // Initialization is just to remove editor warnings. previous is guaranteed to have been assigned at least once before statement LERP triggers.

        int i = 0;
        for(ColorStop colorStop : colorStops) {
            float position;
            while(i < length && (position = i / Math.max(1f, length - 1)) <= colorStop.position) {
                if(position == colorStop.position) {
                    callback.accept(Evaluation.evalTextColor(colorStop.color, ctx), i);
                    previous = colorStop;
                }
                else if(position < colorStop.position) {
                    Color color,
                        cur = new Color(Evaluation.evalTextColor(colorStop.color, ctx)),
                        prev = new Color(Evaluation.evalTextColor(previous.color, ctx));

                    LERP:
                    color = cur.add(
                            cur.sub(prev)
                            .div(colorStop.position - previous.position)
                            .mul(position - colorStop.position)
                    );
                    callback.accept(color.toAdventureColor(), i);
                }
                i++;
            }
            previous = colorStop;
        }
    }
    private record ColorStop(float position, String color) {}
}
