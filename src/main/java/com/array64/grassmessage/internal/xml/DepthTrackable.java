package com.array64.grassmessage.internal.xml;

public interface DepthTrackable {
    DepthTracker getDepthTracker();
    int getRootDepth();

    default boolean atRootDepth() {
        return getDepthTracker().getDepth() == getRootDepth();
    }
}
