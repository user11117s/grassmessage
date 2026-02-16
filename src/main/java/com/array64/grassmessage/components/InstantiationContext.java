package com.array64.grassmessage.components;

import com.array64.grassmessage.components.impl.concrete.CompositeComponent;
import com.array64.grassmessage.data.FileData;
import com.array64.grassmessage.data.GradientData;
import net.md_5.bungee.api.chat.BaseComponent;

import java.util.Map;
import java.util.Objects;

public class InstantiationContext {
    private final FileData fileData;
    private final Map<String, String> vars;
    private final Map<String, BaseComponent> bungeeComponents;
    private final VariableSubstitutor variableSubstitutor;

    public InstantiationContext(FileData fileData, Map<String, String> vars, Map<String, BaseComponent> bungeeComponents) {
        this.fileData = fileData;
        this.vars = vars;
        this.bungeeComponents = bungeeComponents;
        this.variableSubstitutor = new VariableSubstitutor(this);
    }

    public GradientData getGradient(String ref) {
        return fileData.getGradient(ref);
    }

    public Component getMessage(String ref) {
        return fileData.getMessageComponent(ref);
    }

    public String getVar(String name) {
        return Objects.requireNonNull(vars.get(name), "Variable of name " + name + " was not provided.");
    }

    public BaseComponent getBungeeComponent(String name) {
        return Objects.requireNonNull(bungeeComponents.get(name), "Bungee component of name " + name + " was not provided.");
    }

    // Convert a string like "Hello, $(name)!" into "Hello, Tom!" during component instantiation
    // by using the vars map. In this example, it might look like Map.of("name", "Tom").
    public String substituteVars(String expr) {
        return variableSubstitutor.substituteVars(expr);
    }
}
