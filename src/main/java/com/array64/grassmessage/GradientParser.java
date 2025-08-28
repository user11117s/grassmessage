package com.array64.grassmessage;

import org.xml.sax.Attributes;

class GradientParser implements XMLParser {
    private final GradientData data;
    private Float stopPosition = null;
    private boolean doneParsing;

    GradientParser(GradientData data) {
        this.data = data;
    }

    @Override
    public void startTag(String qName, Attributes attrs) {
        stopPosition = switch(qName) {
            case "start" -> 0f;
            case "end" -> 1f;
            case "middle" -> Float.parseFloat(attrs.getValue("position"));
            default -> throw new IllegalStateException("Unexpected tag: " + qName);
        };
    }

    @Override
    public void endTag(String qName) {
        if(qName.equals("gradient"))
            this.doneParsing = true;
    }

    @Override
    public void parseText(String text) {
        if(stopPosition != null) {
            data.addStop(stopPosition, new Color(text));
            stopPosition = null;
        }
    }

    @Override
    public boolean isDoneParsing() {
        return this.doneParsing;
    }
}
