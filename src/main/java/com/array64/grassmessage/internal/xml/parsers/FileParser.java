package com.array64.grassmessage.internal.xml.parsers;

import com.array64.grassmessage.internal.components.concrete.GCompositeComponent;
import com.array64.grassmessage.internal.components.GComponentRegistry;
import com.array64.grassmessage.internal.data.FileData;
import com.array64.grassmessage.internal.data.GradientData;
import com.array64.grassmessage.internal.misc.ConstantNames;
import com.array64.grassmessage.internal.xml.XmlParser;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.format.ShadowColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.xml.sax.Attributes;

public class FileParser implements XmlParser {
    private final FileData fileData;
    private final GComponentRegistry componentRegistry;
    private XmlParser currentSubParser = null;
    private boolean rootFound = false;

    public FileParser(FileData fileData, GComponentRegistry componentRegistry) {
        this.fileData = fileData;
        this.componentRegistry = componentRegistry;
    }

    public void startTag(String qName, Attributes attrs) {
        if(!rootFound) {
            if(!"grass".equals(qName)) throw new IllegalStateException("Root element is not grass.");
            rootFound = true;
        }
        if(currentSubParser == null) {
            // Use respective parsers when they come.
            // XSD does the heavy lifting of validation for us.
            switch(qName) {
                case "gradient" -> {
                    GradientData gradientData = new GradientData();
                    fileData.addGradient(attrs.getValue("name"), gradientData);
                    currentSubParser = new GradientParser(gradientData);
                    componentRegistry.depthTracker.enter();
                }
                case "style" -> {
                    Style style = createStyle(attrs);
                    fileData.addStyle(attrs.getValue("name"), style);
                }
                case "message" -> {
                    GCompositeComponent message = componentRegistry.createCompositeComponent();
                    fileData.addMessage(attrs.getValue("name"), message);
                    currentSubParser = message;
                    componentRegistry.depthTracker.enter();
                }
            }
        } else {
            currentSubParser.startTag(qName, attrs);
            componentRegistry.depthTracker.enter();
        }
    }

    private Style createStyle(Attributes attrs) {
        Style.Builder styleBuilder = Style.style();

        textDec(attrs, "bold", TextDecoration.BOLD, styleBuilder);
        textDec(attrs, "italic", TextDecoration.ITALIC, styleBuilder);
        textDec(attrs, "underlined", TextDecoration.UNDERLINED, styleBuilder);
        textDec(attrs, "strikethrough", TextDecoration.STRIKETHROUGH, styleBuilder);
        textDec(attrs, "obfuscated", TextDecoration.OBFUSCATED, styleBuilder);

        String color = attrs.getValue("color");
        if(color != null) {
            if(color.charAt(0) == '#') {
                styleBuilder.color(TextColor.fromHexString(color));
            }
            else for(ConstantNames.ColorMapping mapping : ConstantNames.CHAT_COLORS) {
                if(mapping.name().equals(color)) {
                    styleBuilder.color(mapping.color());
                    break;
                }
            }
        }
        String shadowColor = attrs.getValue("shadowColor");
        if(shadowColor != null)
            styleBuilder.shadowColor(ShadowColor.fromHexString(shadowColor));

        String font = attrs.getValue("font");
        if(font != null)
            styleBuilder.font(Key.key(font));

        return styleBuilder.build();
    }

    private void textDec(Attributes attrs, String attr, TextDecoration decoration, Style.Builder styleBuilder) {
        String attrValue = attrs.getValue(attr);
        if("true".equals(attrValue) || "1".equals(attrValue)) // We're using the equals method of the literal to handle null cases
            styleBuilder.decorate(decoration);
    }

    @Override
    public void endTag(String qName) {
        if(qName.equals("gradient") || qName.equals("message")) {
            if(qName.equals("gradient"))
                ((GradientParser) currentSubParser).getData().onEnd();

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