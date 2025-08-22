package com.array64.grassmessage;

import org.xml.sax.Attributes;

import java.util.function.Function;

class FileParser extends XMLParser {
    private final FileData fileData;
    private final Function<GradientData, GradientParser> gradientParsers = GradientParser::new;
    private final Function<ComponentHolder, MessageParser> messageParsers = MessageParser::new;
    private XMLParser currentSubParser = null;

    FileParser(FileData fileData) {
        this.fileData = fileData;
    }

    @Override
    protected void startTag(String qName, Attributes attributes) {
        if(currentSubParser == null) {
            // Use respective parsers when they come.
            // XSD does the heavy lifting of validation for us.
            if(qName.equals("gradient")) {

                GradientData gradientData = new GradientData();
                fileData.addGradient(attributes.getValue("name"), gradientData);
                currentSubParser = gradientParsers.apply(gradientData);
            }
            else if(qName.equals("message")) {

                ComponentHolder message = new ComponentHolder(ComponentModifiers.NONE);
                fileData.addMessage(attributes.getValue("name"), message);
                currentSubParser = messageParsers.apply(message);
            }

        } else currentSubParser.startTag(qName, attributes);
    }

    @Override
    protected void endTag(String qName) {
        if(currentSubParser == null) {

        } else {
            currentSubParser.endTag(qName);
            if(currentSubParser.isDoneParsing())
                currentSubParser = null;
        }
    }

    @Override
    protected void parseText(String text) {
        if(currentSubParser == null) {

        } else currentSubParser.parseText(text);
    }
}