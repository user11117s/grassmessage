package com.array64.grassmessage.components.impl;

import com.array64.grassmessage.components.Component;
import com.array64.grassmessage.components.InstantiationContext;
import com.array64.grassmessage.xml.DepthTrackable;
import com.array64.grassmessage.xml.DepthTracker;
import net.md_5.bungee.api.chat.BaseComponent;
import org.xml.sax.Attributes;

public abstract class AbstractComponent implements Component, DepthTrackable {
    private DepthTracker depthTracker;
    private int rootDepth;

    @Override
    public final void startTag(String qName, Attributes attrs) {
        enterTag(qName, attrs);
        depthTracker.enter();
    }

    protected abstract void enterTag(String qName, Attributes attrs);

    @Override
    public final void endTag(String qName) {
        depthTracker.exit();
        if(depthTracker.getDepth() >= rootDepth) exitTag(qName);
    }

    protected abstract void exitTag(String qName);

    @Override
    public void instantiateInParent(BaseComponent parent, InstantiationContext ctx) {
        parent.addExtra(this.instantiate(ctx));
    }

    protected abstract BaseComponent instantiate(InstantiationContext ctx);

    // Helper methods for subclasses

    protected void throwOnEnterTag() {
        throw new UnsupportedOperationException(this.getClass() + " does not support enterTag.");
    }

    protected void throwOnExitTag() {
        throw new UnsupportedOperationException(this.getClass() + " does not support exitTag.");
    }

    protected void throwOnParseText() {
        throw new UnsupportedOperationException(this.getClass() + " does not support parseText.");
    }

    protected void throwOnInstantiate() {
        throw new UnsupportedOperationException(this.getClass() + " does not support instantiate.");
    }

    @Override
    public int getRootDepth() {
        return rootDepth;
    }

    @Override
    public DepthTracker getDepthTracker() {
        return depthTracker;
    }

    public AbstractComponent initialize(DepthTracker depthTracker) {
        this.depthTracker = depthTracker;
        this.rootDepth = depthTracker.getDepth();
        return this;
    }
}
