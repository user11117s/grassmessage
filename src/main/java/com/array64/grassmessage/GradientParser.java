package com.array64.grassmessage;

import org.xml.sax.Attributes;

import java.util.Optional;

class GradientParser extends AbstractXMLParser {
    private final GradientData data;
    private Optional<Float> stopPosition = Optional.empty();

    GradientParser(GradientData data) {
        this.data = data;
    }

    @Override
    public void startTag(String qName, Attributes attrs) {
        stopPosition = Optional.of(switch(qName) {
            case "start" -> 0f;
            case "end" -> 1f;
            case "middle" -> Float.parseFloat(attrs.getValue("position"));
            default -> throw new IllegalStateException("Unexpected tag: " + qName);
        });
    }

    @Override
    public void endTag(String qName) {
        if(qName.equals("gradient"))
            setDoneParsing();
    }

    @Override
    public void parseText(String text) {
        if(stopPosition.isPresent()) {
            data.addStop(stopPosition.get(), new Color(text));
            stopPosition = Optional.empty();
        }
    }
}
