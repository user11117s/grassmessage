package com.array64.grassmessage.components.impl.concrete;

import com.array64.grassmessage.components.ComponentHolder;
import com.array64.grassmessage.components.ComponentRegistry;
import com.array64.grassmessage.components.impl.AbstractComponent;
import com.array64.grassmessage.components.Component;
import com.array64.grassmessage.components.InstantiationContext;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.TranslatableComponent;
import org.xml.sax.Attributes;

import java.util.ArrayList;
import java.util.List;

public class LeafTranslatableComponent extends AbstractComponent implements ComponentHolder {
    private String childElementName;
    private String key = "";
    private String fallback = null;
    private final List<Component> with;
    private final ComponentRegistry registry;

    public LeafTranslatableComponent(ComponentRegistry registry) {
        this.registry = registry;
        this.with = new ArrayList<>();
    }

    @Override
    protected void enterTag(String qName, Attributes attrs) {
        if(parsingChild())
            getLast().startTag(qName, attrs);
        else {
            childElementName = qName;
            if("with".equals(qName))
                with.add(new CompositeComponent(registry));
        }
    }

    @Override
    protected void exitTag(String qName) {
        if(parsingChild())
            getLast().endTag(qName);
    }

    @Override
    protected BaseComponent instantiate(InstantiationContext ctx) {
        var translatable = new TranslatableComponent(key);
        translatable.setWith(with.stream().map(component -> {
            BaseComponent parentComponent = new TextComponent();
            component.instantiateInParent(parentComponent, ctx);
            return parentComponent;
        }).toList());

        translatable.setFallback(fallback);
        return translatable;
    }

    @Override
    public void parseText(String text) {
        if(parsingChild())
            getLast().parseText(text);

        else if(with.isEmpty()) {
            if("key".equals(childElementName))
                this.key += text.strip();
            else if("fallback".equals(childElementName))
                this.fallback += text.strip();
            else
                throw new IllegalStateException("Unexpected tag: " + childElementName);
        }
        else throw new IllegalStateException("Unexpected text: " + text);
    }

    @Override
    public List<Component> getComponents() {
        return with;
    }
}
