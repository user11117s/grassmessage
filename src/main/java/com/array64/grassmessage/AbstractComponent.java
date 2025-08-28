package com.array64.grassmessage;

import org.xml.sax.Attributes;

abstract class AbstractComponent implements Component {
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
}
