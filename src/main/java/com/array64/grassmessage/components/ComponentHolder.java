package com.array64.grassmessage.components;

import java.util.List;

public interface ComponentHolder {
    List<Component> getComponents();

    default Component getLast() {
        var components = getComponents();
        return components.get(components.size() - 1);
    }

    default boolean parsingChild() {
        if(getComponents().isEmpty()) return false;
        else return !getLast().isDoneParsing();
    }
}
