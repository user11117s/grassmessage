package com.array64.grassmessage.xml;

public class DepthTracker {
    private int depth = 0;

    public int getDepth() {
        return depth;
    }

    public void enter() {
        depth++;
    }

    public void exit() {
        depth--;
    }
}
