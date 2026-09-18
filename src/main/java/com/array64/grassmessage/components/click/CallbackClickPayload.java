package com.array64.grassmessage.components.click;

import com.array64.grassmessage.components.InstantiationContext;
import net.kyori.adventure.text.event.ClickEvent;

import java.time.Duration;

public class CallbackClickPayload implements ClickPayload {
    private final String var;
    private final String duration;
    private final String uses;

    public CallbackClickPayload(String var, String duration, String uses) {
        this.var = var;
        this.duration = duration;
        this.uses = uses;
    }

    @Override
    public ClickEvent.Payload getPayload(InstantiationContext ctx) {
        throw new IllegalStateException("This is a dummy payload, not meant to be called by getPayload().");
    }

    public String getVar() {
        return var;
    }

    public Duration getDuration(InstantiationContext ctx) {
        if(duration.charAt(0) == '$') {
            String varName = duration.substring(2, duration.length() - 1);
            try {
                return (Duration) ctx.getVarRaw(varName);
            } catch(ClassCastException e) {
                throw new IllegalArgumentException("duration, pointed to by variable " + varName + ", is not a Duration.");
            }
        } else {
            Duration dur = Duration.ZERO;
            int value = 0;
            for(int i = 0; i < duration.length(); i++) {
                char c = duration.charAt(i);
                if(Character.isDigit(c)) {
                    value *= 10;
                    value += c - '0';
                } else {
                    dur = switch(c) {
                        case 'd' -> dur.plusDays(value);
                        case 'h' -> dur.plusHours(value);
                        case 'm' -> dur.plusMinutes(value);
                        case 's' -> dur.plusSeconds(value);
                        default -> throw new IllegalArgumentException("Unexpected non-digi character in duration: " + c);
                    };
                    value = 0;
                }
            }
            return dur;
        }
    }

    public int getUses(InstantiationContext ctx) {
        int usesInt;
        if(uses.charAt(0) == '$') {
            String varName = uses.substring(2, uses.length() - 1);
            try {
                usesInt = (int) ctx.getVarRaw(varName);
            } catch(ClassCastException e) {
                throw new IllegalArgumentException("uses, pointed to by variable " + varName + ", is not a integer.");
            }
        } else usesInt = Integer.parseInt(uses);

        return usesInt < 0 ? -1 : usesInt;
    }
}
