package com.array64.grassmessage.internal.components.concrete;

import com.array64.grassmessage.internal.components.GComponent;
import com.array64.grassmessage.internal.components.GComponentRegistry;
import com.array64.grassmessage.internal.components.InstantiationContext;
import com.array64.grassmessage.internal.components.GAbstractComponent;
import com.array64.grassmessage.internal.components.Preformat;
import net.kyori.adventure.text.Component;
import org.xml.sax.Attributes;

public class GPreformattedComponent extends GAbstractComponent {
    private final Preformat preformat;
    private final GComponent component;
    private final GComponentRegistry registry;
    private final boolean inline;

    public GPreformattedComponent(String dedent, String trimBounds, String inlineStr, GComponentRegistry registry) {
        this.preformat = new Preformat();

        if("0".equals(dedent) || "false".equals(dedent))
            preformat.setDedent(false);
        if("0".equals(trimBounds) || "false".equals(trimBounds))
            preformat.setTrimBounds(false);

        this.inline = "1".equals(inlineStr) || "true".equals(inlineStr);
        this.component = registry.createCompositeComponent();
        this.registry = registry;
        registry.addPreformat(this.preformat);
    }

    @Override
    protected void enterTag(String qName, Attributes attrs) {
        component.startTag(qName, attrs);
    }

    @Override
    protected void exitTag(String qName) {
        component.endTag(qName);
    }

    @Override
    public void onEnd() {
        component.onEnd();
        preformat.format();
        registry.removePreformat();
    }

    @Override
    protected Component instantiate(InstantiationContext ctx) {
        Component parent = Component.empty();
        parent = component.instantiateInParent(parent, ctx);
        return parent;
    }

    @Override
    public void parseText(String text) {
        component.parseText(text);
    }

    public boolean isInline() {
        return inline;
    }
}
