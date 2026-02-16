package com.array64.grassmessage.components.impl.concrete;

import com.array64.grassmessage.components.*;
import com.array64.grassmessage.components.impl.AbstractComponent;
import com.array64.grassmessage.misc.Glue;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.xml.sax.Attributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompositeComponent extends AbstractComponent {
    private final List<Component> heldComponents;
    private final ComponentModifier modifier;
    private final ComponentRegistry registry;
    private Glue glue = Glue.TRUE;

    public CompositeComponent(ComponentRegistry registry) {
        this(ComponentModifiers.NONE, registry);
    }

    public CompositeComponent(ComponentModifier modifier, ComponentRegistry registry) {
        this.heldComponents = new ArrayList<>();
        this.modifier = modifier;
        this.registry = registry;
    }

    public void addComponent(Component component) {
        heldComponents.add(component);
    }

    @Override
    public void instantiateInParent(BaseComponent parent, InstantiationContext ctx) {
        // Add components to the parent based on children
        if(heldComponents.isEmpty()) return;

        if(heldComponents.size() == 1) {
            modifier.modify(parent);
            heldComponents.get(0).instantiateInParent(parent, ctx);
        }
        else {
            if(modifier == ComponentModifiers.NONE)
                instantiateChildrenIn(parent, ctx);
            else {
                BaseComponent thisComponent = new TextComponent();
                modifier.modify(thisComponent);
                instantiateChildrenIn(thisComponent, ctx);
                parent.addExtra(thisComponent);
            }
        }
    }

    private void instantiateChildrenIn(BaseComponent component, InstantiationContext ctx) {
        heldComponents.forEach(child -> child.instantiateInParent(component, ctx));
    }

    @Override
    protected BaseComponent instantiate(InstantiationContext ctx) {
        throwOnInstantiate();
        return null; // Just a formality for the compiler; throwOnInstantiate() will throw before this statement.
    }

    @Override
    protected void enterTag(String qName, Attributes attrs) {
        if(atRootDepth()) {
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
        else
            getLast().startTag(qName, attrs);
    }

    @Override
    protected void exitTag(String qName) {
        if(atRootDepth())
            getLast().onEnd();
        else
            getLast().endTag(qName);
    }

    @Override
    public void parseText(String text) {
        if(atRootDepth()) {
            String frontStrippedText = text.stripLeading();
            String strippedText = frontStrippedText.stripTrailing();

            // Turn leading whitespace into a single space.
            if((frontStrippedText.length() < text.length() && glue == Glue.DEFAULT)
                || glue == Glue.FALSE) {

                appendText(" ");
            }

            appendText(strippedText.replaceAll("\\s+", " "));

            // Check if trailing whitespace exists.
            glue = strippedText.length() == frontStrippedText.length() ? Glue.DEFAULT : Glue.FALSE;
        }
        else getLast().parseText(text);
    }

    private void appendText(String text) {
        if(heldComponents.isEmpty() || !(getLast() instanceof LeafTextComponent))
            addComponent(new LeafTextComponent());

        getLast().parseText(text);
    }

    private Component getLast() {
        return heldComponents.get(heldComponents.size() - 1);
    }
}
