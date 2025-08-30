package com.array64.grassmessage.components;

import org.xml.sax.Attributes;

public abstract class AbstractComponent implements Component {
    private int depthLevel = 0;

    public boolean isDoneParsing() {
        return depthLevel < 0;
    }

    @Override
    public void startTag(String qName, Attributes attrs) {
        depthLevel++;
        enterTag(qName, attrs);
    }

    protected abstract void enterTag(String qName, Attributes attrs);

    @Override
    public void endTag(String qName) {
        if(depthLevel > 0) exitTag(qName);
        depthLevel--;
    }

    protected abstract void exitTag(String qName);

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
}
