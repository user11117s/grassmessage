package com.array64.grassmessage.components.impl.concrete;

import com.array64.grassmessage.components.GComponent;
import com.array64.grassmessage.components.GComponentRegistry;
import com.array64.grassmessage.components.InstantiationContext;
import com.array64.grassmessage.components.impl.GAbstractComponent;
import net.kyori.adventure.text.Component;
import org.xml.sax.Attributes;

public class GStyleComponent extends GAbstractComponent {
    private GComponent content;
    private final String ref;
    private final GComponentRegistry componentRegistry;

    public GStyleComponent(String ref, GComponentRegistry componentRegistry) {
        this.ref = ref;
        this.componentRegistry = componentRegistry;
    }

    @Override
    public void onStart() {
        content = componentRegistry.createCompositeComponent();
    }

    @Override
    protected void enterTag(String qName, Attributes attrs) {
        content.startTag(qName, attrs);
    }

    @Override
    protected void exitTag(String qName) {
        content.endTag(qName);
    }

    @Override
    public void parseText(String text) {
        content.parseText(text);
    }

    @Override
    protected Component instantiate(InstantiationContext ctx) {
        Component parent = Component.empty();
        parent = content.instantiateInParent(parent, ctx);
        return parent.style(ctx.getStyle(ref));
    }
}
