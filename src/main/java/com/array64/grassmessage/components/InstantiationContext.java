package com.array64.grassmessage.components;

import com.array64.grassmessage.data.FileData;
import com.array64.grassmessage.data.GradientData;

import java.util.Map;
import java.util.Objects;

public class InstantiationContext {
    private final FileData fileData;
    private final Map<String, String> vars;
    private final Map<String, net.kyori.adventure.text.Component> adventureComponents;
    private final VariableSubstitutor variableSubstitutor;

    public InstantiationContext(
            FileData fileData, Map<String, String> vars, Map<String, net.kyori.adventure.text.Component> adventureComponents) {
        this.fileData = fileData;
        this.vars = vars;
        this.adventureComponents = adventureComponents;
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

    public net.kyori.adventure.text.Component getAdventureComponent(String name) {
        return Objects.requireNonNull(adventureComponents.get(name), "Adventure component of name " + name + " was not provided.");
    }

    // Convert a string like "Hello, $(name)!" into "Hello, Tom!" during component instantiation
    // by using the vars map. In this example, it might look like Map.of("name", "Tom").
    public String substituteVars(String expr) {
        return variableSubstitutor.substituteVars(expr);
    }
}
