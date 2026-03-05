package com.array64.grassmessage.xml.parsers;

import com.array64.grassmessage.components.impl.concrete.CompositeComponent;
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

    public void startTag(String qName, Attributes attrs) {
        if(currentSubParser == null) {
            // Use respective parsers when they come.
            // XSD does the heavy lifting of validation for us.
            if(qName.equals("gradient")) {
                GradientData gradientData = new GradientData();
                fileData.addGradient(attrs.getValue("name"), gradientData);
                currentSubParser = new GradientParser(gradientData);
                componentRegistry.depthTracker.enter();
            }
            else if(qName.equals("message")) {
                CompositeComponent message = componentRegistry.createCompositeComponent();
                fileData.addMessage(attrs.getValue("name"), message);
                currentSubParser = message;
                componentRegistry.depthTracker.enter();
            }
        } else {
            currentSubParser.startTag(qName, attrs);
            componentRegistry.depthTracker.enter();
        }
    }

    @Override
    public void endTag(String qName) {
        if(qName.equals("gradient") || qName.equals("message")) {
            currentSubParser = null;
            componentRegistry.depthTracker.exit();
        }

        if(currentSubParser != null) {
            componentRegistry.depthTracker.exit();
            currentSubParser.endTag(qName);
        }
    }

    @Override
    public void parseText(String text) {
        if(currentSubParser != null)
            currentSubParser.parseText(text);
    }
}