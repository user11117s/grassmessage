package com.array64.grassmessage.internal.misc;

import com.array64.grassmessage.internal.components.InstantiationContext;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.dialog.DialogLike;
import net.kyori.adventure.nbt.api.BinaryTagHolder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickCallback;
import net.kyori.adventure.text.event.DataComponentValue;
import net.kyori.adventure.text.format.ShadowColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.util.ARGBLike;

import java.time.Duration;
import java.time.temporal.TemporalAmount;
import java.util.UUID;

public class Evaluation {
    private static boolean isVar(String unsub) {
        return unsub.charAt(0) == '$';
    }

    private static String getVarName(String unsub) {
        return unsub.substring(2, unsub.length() - 1);
    }

    @SuppressWarnings("unchecked")
    private static <T> T getValueOrMsg(String varName, InstantiationContext ctx, String articleType) {
        try {
            return (T) ctx.getVarRaw(varName);
        }
        catch(ClassCastException e) {
            throw new IllegalArgumentException(
                "Variable '" + varName +
                "', is not " + articleType +
                " as expected."
            );
        }
    }

    public static int evalInt(String unsub, InstantiationContext ctx) {
        if(isVar(unsub)) return getValueOrMsg(getVarName(unsub), ctx, "an integer");

        else return Integer.parseInt(unsub);
    }

    public static UUID evalUUID(String unsub, InstantiationContext ctx) {
        if(isVar(unsub)) return getValueOrMsg(getVarName(unsub), ctx, "a UUID");

        else return UUID.fromString(unsub);
    }

    public static DataComponentValue evalData(String unsub, InstantiationContext ctx) {
        if(isVar(unsub)) return getValueOrMsg(getVarName(unsub), ctx, "a DataComponentValue");

        else return BinaryTagHolder.binaryTagHolder(ctx.substituteVars(unsub));
    }

    public static BinaryTagHolder evalNBT(String unsub, InstantiationContext ctx) {
        if(isVar(unsub)) return getValueOrMsg(getVarName(unsub), ctx, "a BinaryTagHolder");

        else throw new IllegalStateException("[THIS SHOULD NEVER HAPPEN] NBT must be supplied through a variable, not text.");
    }

    public static DialogLike evalDialog(String unsub, InstantiationContext ctx) {
        if(isVar(unsub)) return getValueOrMsg(getVarName(unsub), ctx, "a DialogLike");

        else throw new IllegalStateException("[THIS SHOULD NEVER HAPPEN] A dialog must be supplied through a variable, not text.");
    }

    public static Component evalAdventureComponent(String unsub, InstantiationContext ctx) {
        if(isVar(unsub)) return getValueOrMsg(getVarName(unsub), ctx, "a net.kyori.adventure.text.Component");

        else throw new IllegalStateException("[THIS SHOULD NEVER HAPPEN] An adventure component must be supplied through a variable, not text.");
    }

    public static ClickCallback<Audience> evalClickCallback(String unsub, InstantiationContext ctx) {
        if(isVar(unsub)) return getValueOrMsg(getVarName(unsub), ctx, "a ClickCallback<Audience>");

        else throw new IllegalStateException("[THIS SHOULD NEVER HAPPEN] A click callback must be supplied through a variable, not text.");
    }

    public static TemporalAmount evalDuration(String unsub, InstantiationContext ctx) {
        if(isVar(unsub)) return getValueOrMsg(getVarName(unsub), ctx, "a TemporalAmount");
        else {
            Duration dur = Duration.ZERO;
            int value = 0;
            for(int i = 0; i < unsub.length(); i++) {
                char c = unsub.charAt(i);
                if(Character.isDigit(c)) {
                    value *= 10;
                    value += c - '0';
                } else {
                    dur = switch(c) {
                        case 'd' -> dur.plusDays(value);
                        case 'h' -> dur.plusHours(value);
                        case 'm' -> dur.plusMinutes(value);
                        case 's' -> dur.plusSeconds(value);
                        default -> throw new IllegalStateException("[THIS SHOULD NEVER HAPPEN] Unexpected non-digit character in duration: " + c);
                    };
                    value = 0;
                }
            }
            return dur;
        }
    }

    public static TextColor evalTextColor(String unsub, InstantiationContext ctx) {
        if(isVar(unsub)) return getValueOrMsg(getVarName(unsub), ctx, "a TextColor");

        else return TextColor.fromHexString(unsub);
    }

    public static ARGBLike evalShadowColor(String unsub, InstantiationContext ctx) {
        if(isVar(unsub)) return getValueOrMsg(getVarName(unsub), ctx, "a ShadowColor");

        else return ShadowColor.fromHexString(unsub);
    }
}
