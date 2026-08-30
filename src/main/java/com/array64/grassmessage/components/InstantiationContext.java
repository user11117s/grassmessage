package com.array64.grassmessage.components;

import com.array64.grassmessage.data.FileData;
import com.array64.grassmessage.data.GradientData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;

import java.util.Map;
import java.util.Objects;

public class InstantiationContext {
    private final FileData fileData;
    private final Map<String, Object> vars;
    private final VariableSubstitutor variableSubstitutor;

    public InstantiationContext(
            FileData fileData, Map<String, Object> vars) {
        this.fileData = fileData;
        this.vars = vars;
        this.variableSubstitutor = new VariableSubstitutor(this);
    }

    public GradientData getGradient(String ref) {
        return fileData.getGradient(ref);
    }

    public GComponent getMessage(String ref) {
        return fileData.getMessageComponent(ref);
    }

    public Style getStyle(String ref) {
        return fileData.getStyle(ref);
    }

    public String getVar(String name) {
        return getVarRaw(name).toString();
    }

    public Object getVarRaw(String name) {
        return Objects.requireNonNull(vars.get(name), "Variable of name " + name + " was not provided.");
    }

    // Convert a string like "Hello, $(name)!" into "Hello, Tom!" during component instantiation
    // by using the vars map. In this example, it might look like Map.of("name", "Tom").
    public String substituteVars(String expr) {
        return variableSubstitutor.substituteVars(expr);
    }
}
