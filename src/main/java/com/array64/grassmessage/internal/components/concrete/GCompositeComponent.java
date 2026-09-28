package com.array64.grassmessage.internal.components.concrete;

import com.array64.grassmessage.internal.components.*;
import com.array64.grassmessage.internal.misc.Glue;
import net.kyori.adventure.text.Component;
import org.xml.sax.Attributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GCompositeComponent extends GAbstractComponent {
    private final List<GComponent> heldComponents;
    private final GComponentModifier modifier;
    private final GComponentRegistry registry;
    private Glue glue = Glue.TRUE;

    public GCompositeComponent(GComponentRegistry registry) {
        this(GComponentModifiers.NONE, registry);
    }

    public GCompositeComponent(GComponentModifier modifier, GComponentRegistry registry) {
        this.heldComponents = new ArrayList<>();
        this.modifier = modifier;
        this.registry = registry;
    }

    public void addComponent(GComponent component) {
        heldComponents.add(component);
    }

    @Override
    public Component instantiateInParent(Component parent, InstantiationContext ctx) {
        // Add components to the parent based on children
        if(heldComponents.isEmpty()) return parent;

        if(heldComponents.size() == 1) {
            Component thisComponent = Component.empty();
            thisComponent = modifier.modify(thisComponent, ctx);
            thisComponent = heldComponents.get(0).instantiateInParent(thisComponent, ctx);
            return parent.append(thisComponent);
        }
        else {
            if(modifier == GComponentModifiers.NONE)
                return instantiateChildrenIn(parent, ctx);
            else {
                Component thisComponent = Component.empty();
                thisComponent = modifier.modify(thisComponent, ctx);
                thisComponent = instantiateChildrenIn(thisComponent, ctx);
                return parent.append(thisComponent);
            }
        }
    }

    private Component instantiateChildrenIn(Component component, InstantiationContext ctx) {
        Component c = component;
        for(GComponent child : heldComponents) {
            c = child.instantiateInParent(c, ctx);
        }
        return c;
    }

    @Override
    protected Component instantiate(InstantiationContext ctx) {
        throwOnInstantiate();
        return null; // Just a formality for the compiler; throwOnInstantiate() will throw before this statement.
    }

    @Override
    protected void enterTag(String qName, Attributes attrs) {
        if(atRootDepth()) {
            if(registry.topPreformat() == null) {
                Optional<String> whitespace = registry.getWhitespace(qName, attrs);

                if(whitespace.isPresent()) {
                    glue = Glue.TRUE;
                    appendText(whitespace.get());
                } else {
                    boolean addedNewline = addNewlineToPre();
                    if(glue == Glue.FALSE) {
                        if(!addedNewline) appendText(" ");
                        glue = Glue.DEFAULT;
                    }
                    heldComponents.add(registry.get(qName, attrs));
                }
            }
        }
        else
            getLast().startTag(qName, attrs);
    }

    @Override
    protected void exitTag(String qName) {
        if(atRootDepth()) {
            getLast().onEnd();
            if(getLast() instanceof GPreformattedComponent)
                glue = Glue.TRUE;
        }
        else
            getLast().endTag(qName);
    }

    @Override
    public void parseText(String text) {
        if(atRootDepth()) {
            if(registry.topPreformat() == null) {
                if(text.isBlank()) {
                    if(glue == Glue.DEFAULT) glue = Glue.FALSE;
                    return;
                }

                addNewlineToPre();

                String frontStrippedText = text.stripLeading();
                String strippedText = frontStrippedText.stripTrailing();

                // Turn leading whitespace into a single space.
                if ((frontStrippedText.length() < text.length() && glue == Glue.DEFAULT)
                        || glue == Glue.FALSE) {

                    appendText(" ");
                }

                appendText(strippedText.replaceAll("\\s+", " "));

                // Check if trailing whitespace exists.
                glue = strippedText.length() == frontStrippedText.length() ? Glue.DEFAULT : Glue.FALSE;
            }
            else {
                appendText(text);
                registry.topPreformat().addTextComponent((GTextComponent) getLast());
            }
        }
        else getLast().parseText(text);
    }

    private void appendText(String text) {
        if(heldComponents.isEmpty() || !(getLast() instanceof GTextComponent))
            addComponent(new GTextComponent());

        getLast().parseText(text);
    }

    private GComponent getLast() {
        return heldComponents.get(heldComponents.size() - 1);
    }

    private boolean addNewlineToPre() {
        if(!heldComponents.isEmpty() && getLast() instanceof GPreformattedComponent preformatted)
            if(!preformatted.isInline()) {
                appendText("\n");
                return true;
            }
        return false;
    }
}
