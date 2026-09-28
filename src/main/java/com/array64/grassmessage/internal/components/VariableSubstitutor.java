package com.array64.grassmessage.internal.components;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Converts a string "expression" like "Hello, $(name)!" and converts it into a string like "Hello, Tom!"
// based on variables passed into a message
public class VariableSubstitutor {
    private static final Pattern VAR_PATTERN = Pattern.compile("\\$\\(([^)]+)\\)");
    private final InstantiationContext ctx;

    public VariableSubstitutor(InstantiationContext ctx) {
        this.ctx = ctx;
    }

    public String substituteVars(String expr) {
        Matcher matcher = VAR_PATTERN.matcher(expr);
        StringBuilder sb = new StringBuilder();
        int lastVarEnd = 0;

        while(matcher.find()) {
            sb.append(expr, lastVarEnd, matcher.start());
            String varName = matcher.group(1);
            String varValue = ctx.getVar(varName);
            sb.append(varValue == null ? "undefined" : varValue);
            lastVarEnd = matcher.end();
        }
        sb.append(expr.substring(lastVarEnd));
        return sb.toString();
    }
}
