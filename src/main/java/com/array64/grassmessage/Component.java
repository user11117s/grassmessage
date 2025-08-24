package com.array64.grassmessage;

interface Component extends ComponentModifier {
    default void addComponent(Component component) {
        throw new UnsupportedOperationException("This component does not support child components.");
    }
    void append(String text);
    Component getParent();
}
