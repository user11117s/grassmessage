package com.array64.grassmessage.internal.xml.properties;

import com.array64.grassmessage.internal.xml.DepthTrackable;
import com.array64.grassmessage.internal.xml.DepthTracker;
import org.xml.sax.Attributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class PropertyHolder implements XmlProperty<List<XmlPropertyMeta>>, DepthTrackable {

    protected final Map<String, Supplier<XmlProperty<?>>> propertyParsers;
    private final List<XmlPropertyMeta> properties;
    private final DepthTracker depthTracker;
    private final int rootDepth;

    private XmlPropertyMeta currentProperty;

    public PropertyHolder(Map<String, Supplier<XmlProperty<?>>> propertyParsers, DepthTracker depthTracker) {
        this.propertyParsers = propertyParsers;
        this.properties = new ArrayList<>();
        this.depthTracker = depthTracker;
        this.rootDepth = depthTracker.getDepth() + 1;
    }

    @Override
    public void startTag(String qName, Attributes attrs) {
        if(atRootDepth())
            this.currentProperty = new XmlPropertyMeta(qName, attrs, propertyParsers.get(qName).get());
        else
            this.currentProperty.parser().startTag(qName, attrs);

        // depthTracker.enter();
    }

    @Override
    public void endTag(String qName) {
        // depthTracker.exit();

        if(atRootDepth()) {
            properties.add(currentProperty);
            this.currentProperty = null;
        }
        else
            this.currentProperty.parser().endTag(qName);
    }

    @Override
    public void parseText(String text) {
        if(this.currentProperty != null)
            this.currentProperty.parser().parseText(text);
    }

    @Override
    public List<XmlPropertyMeta> get() {
        return properties;
    }

    @Override
    public DepthTracker getDepthTracker() {
        return depthTracker;
    }

    @Override
    public int getRootDepth() {
        return rootDepth;
    }
}
