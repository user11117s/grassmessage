package com.array64.grassmessage.xml.parsers;

import com.array64.grassmessage.components.impl.ComponentHolder;
import com.array64.grassmessage.components.ComponentModifiers;
import com.array64.grassmessage.components.ComponentRegistry;
import com.array64.grassmessage.data.FileData;
import com.array64.grassmessage.data.GradientData;
import com.array64.grassmessage.xml.XMLParser;
import org.xml.sax.Attributes;

public class FileParser implements XMLParser {
    private final FileData fileData;
    private final ComponentRegistry componentRegistry;
    private XMLParser currentSubParser = null;

    public FileParser(FileData fileData, ComponentRegistry componentRegistry) {
        this.fileData = fileData;
        this.componentRegistry = componentRegistry;
    }

    @Override
    public boolean isDoneParsing() {
        return false;
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