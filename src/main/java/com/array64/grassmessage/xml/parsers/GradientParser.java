package com.array64.grassmessage.xml.parsers;

import com.array64.grassmessage.misc.Color;
import com.array64.grassmessage.data.GradientData;
import com.array64.grassmessage.xml.XmlParser;
import org.xml.sax.Attributes;

public class GradientParser implements XmlParser {
    private final GradientData data;
    private Float stopPosition = null;

    public GradientParser(GradientData data) {
        this.data = data;
    }

    @Override
    public void startTag(String qName, Attributes attrs) {
        stopPosition = switch(qName) {
            case "start" -> 0f;
            case "end" -> 1f;
            case "middle" -> Float.parseFloat(attrs.getValue("position"));
            default -> throw new IllegalStateException("[THIS SHOULD NEVER HAPPEN] Unexpected tag: " + qName);
        };
    }

    @Override
    public void endTag(String qName) {}

    @Override
    public void parseText(String text) {
        if(stopPosition != null) {
            data.addStop(stopPosition, new Color(text));
            stopPosition = null;
        }
    }
}
