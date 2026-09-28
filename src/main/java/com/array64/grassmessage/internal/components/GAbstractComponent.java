package com.array64.grassmessage.internal.components;

import com.array64.grassmessage.internal.xml.DepthTrackable;
import com.array64.grassmessage.internal.xml.DepthTracker;
import net.kyori.adventure.text.Component;
import org.xml.sax.Attributes;

public abstract class GAbstractComponent implements GComponent, DepthTrackable {
    private DepthTracker depthTracker;
    private int rootDepth;

    @Override
    public final void startTag(String qName, Attributes attrs) {
        enterTag(qName, attrs);
        // depthTracker.enter();
    }

    protected abstract void enterTag(String qName, Attributes attrs);

    @Override
    public final void endTag(String qName) {
        // depthTracker.exit();
        if(depthTracker.getDepth() >= rootDepth) exitTag(qName);
    }

    protected abstract void exitTag(String qName);

    @Override
    public Component instantiateInParent(Component parent, InstantiationContext ctx) {
        return parent.append(this.instantiate(ctx));
    }

    protected abstract Component instantiate(InstantiationContext ctx);

    // Helper methods for subclasses

    protected void throwOnEnterTag() {
        throw new IllegalStateException("[THIS SHOULD NEVER HAPPEN]" + this.getClass() + " does not support enterTag.");
    }

    protected void throwOnExitTag() {
        throw new IllegalStateException("[THIS SHOULD NEVER HAPPEN]" + this.getClass() + " does not support exitTag.");
    }

    protected void throwOnParseText() {
        throw new IllegalStateException("[THIS SHOULD NEVER HAPPEN]" + this.getClass() + " does not support parseText.");
    }

    protected void throwOnInstantiate() {
        throw new IllegalStateException("[THIS SHOULD NEVER HAPPEN]" + this.getClass() + " does not support instantiate.");
    }

    @Override
    public int getRootDepth() {
        return rootDepth;
    }

    @Override
    public DepthTracker getDepthTracker() {
        return depthTracker;
    }

    public GAbstractComponent initialize(DepthTracker depthTracker) {
        this.depthTracker = depthTracker;
        this.rootDepth = depthTracker.getDepth() + 1;
        return this;
    }
}
