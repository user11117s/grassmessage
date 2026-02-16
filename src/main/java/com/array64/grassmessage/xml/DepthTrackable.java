package com.array64.grassmessage.xml;

public interface DepthTrackable {
    DepthTracker getDepthTracker();
    int getRootDepth();

    default boolean atRootDepth() {
        return getDepthTracker().getDepth() == getRootDepth();
    }
}
