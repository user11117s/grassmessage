package com.array64.grassmessage;

import org.xml.sax.Attributes;

import java.util.function.Function;

class FileParser extends AbstractXMLParser {
    private final FileData fileData;
    private final ComponentRegistry componentRegistry;
    private XMLParser currentSubParser = null;

    FileParser(FileData fileData, ComponentRegistry componentRegistry) {
        this.fileData = fileData;
        this.componentRegistry = componentRegistry;
    }

    @Override
    public void startTag(String qName, Attributes attrs) {
        if(currentSubParser == null) {
            // Use respective parsers when they come.
            // XSD does the heavy lifting of validation for us.
            if(qName.equals("gradient")) {
                GradientData gradientData = new GradientData();
                fileData.addGradient(attrs.getValue("name"), gradientData);
                currentSubParser = new GradientParser(gradientData);
            }
            else if(qName.equals("message")) {
                ComponentHolder message = new ComponentHolder(ComponentModifiers.NONE, componentRegistry);
                fileData.addMessage(attrs.getValue("name"), message);
                currentSubParser = message;
            }

        } else currentSubParser.startTag(qName, attrs);
    }

    @Override
    public void endTag(String qName) {
        if(currentSubParser == null) {

        } else {
            currentSubParser.endTag(qName);
            if(currentSubParser.isDoneParsing())
                currentSubParser = null;
        }
    }

    @Override
    public void parseText(String text) {
        if(currentSubParser == null) {

        } else currentSubParser.parseText(text);
    }
}