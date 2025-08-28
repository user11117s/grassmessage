package com.array64.grassmessage;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.xml.sax.Attributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ComponentHolder extends AbstractComponent {
    private final List<Component> heldComponents;
    private final ComponentModifier modifier;
    private final ComponentRegistry registry;
    private Glue glue = Glue.TRUE;

    public ComponentHolder(ComponentModifier modifier, ComponentRegistry registry) {
        this.heldComponents = new ArrayList<>();
        this.modifier = modifier;
        this.registry = registry;
    }

    public void addComponent(Component component) {
        heldComponents.add(component);
    }

    @Override
    public void modifyParent(BaseComponent parent) {
        // Add components to the parent based on children
        if(heldComponents.isEmpty()) return;

        if(heldComponents.size() == 1) {
            modifier.modify(parent);
            heldComponents.get(0).modifyParent(parent);
        }
        else {
            BaseComponent thisComponent = new TextComponent();
            modifier.modify(thisComponent);

            heldComponents.forEach(child -> child.modifyParent(thisComponent));
            parent.addExtra(thisComponent);
        }
    }

    @Override
    public void enterTag(String qName, Attributes attrs) {
        if(parsingChild())
            getLast().startTag(qName, attrs);
        else {
            Optional<String> whitespace = registry.getWhitespace(qName, attrs);

            if(whitespace.isPresent()) {
                glue = Glue.TRUE;
                appendText(whitespace.get());
            }
            else {
                if(glue == Glue.FALSE) {
                    appendText(" ");
                    glue = Glue.DEFAULT;
                }
                heldComponents.add(registry.get(qName, attrs));
            }
        }
    }

    @Override
    public void exitTag(String qName) {
        if(parsingChild())
            getLast().endTag(qName);
    }

    @Override
    public void parseText(String text) {
        if(glue != Glue.TRUE && parsingChild())
            getLast().parseText(text);
        else {
            String frontStrippedText = text.stripLeading();
            String strippedText = frontStrippedText.stripTrailing();

            // Turn leading whitespace into a single space.
            if((frontStrippedText.length() < text.length() && glue == Glue.DEFAULT)
                || glue == Glue.FALSE) {

                appendText(" ");
            }

            appendText(strippedText);

            // Check if trailing whitespace exists.
            glue = strippedText.length() == frontStrippedText.length() ? Glue.DEFAULT : Glue.FALSE;
        }
    }

    private void appendText(String text) {
        if(heldComponents.isEmpty() || !(getLast() instanceof LeafTextComponent))
            addComponent(new LeafTextComponent());

        getLast().parseText(text);
    }

    private Component getLast() {
        return heldComponents.get(heldComponents.size() - 1);
    }

    private boolean parsingChild() {
        if(heldComponents.isEmpty()) return true;
        return !getLast().isDoneParsing();
    }
}
