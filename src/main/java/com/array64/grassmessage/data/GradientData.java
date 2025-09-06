package com.array64.grassmessage.data;

import com.array64.grassmessage.misc.Color;

import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class GradientData {
    private final SortedSet<ColorStop> colorStops = new TreeSet<>(Comparator.comparing(ColorStop::position));

    public void addStop(float position, Color color) {
        colorStops.add(new ColorStop(position, color));
    }

    public Color evaluate(float position) {
        if(position < 0 || position > 1)
            throw new IllegalArgumentException("position must be between 0 and 1.");

        Iterator<ColorStop> stopIterator = colorStops.iterator();
        ColorStop previous = stopIterator.next();

        while(stopIterator.hasNext()) {
            ColorStop next = stopIterator.next();
            if(next.position == position) return next.color;

            if(next.position > position) {
                return next.color.add(
                    next.color.sub(previous.color)
                        .div(next.position - previous.position)
                        .mul(position - next.position)
                );
            } else previous = next;
        }
        throw new IllegalStateException("gradient does not have end stop yet.");
    }
    private record ColorStop(float position, Color color) {}
}
