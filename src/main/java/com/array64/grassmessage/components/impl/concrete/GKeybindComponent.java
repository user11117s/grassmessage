package com.array64.grassmessage.components.impl.concrete;

import com.array64.grassmessage.components.impl.GAbstractComponent;
import com.array64.grassmessage.components.InstantiationContext;
import net.kyori.adventure.text.Component;
import org.xml.sax.Attributes;

public class GKeybindComponent extends GAbstractComponent {
    private String keybind = "";

    @Override
    protected void enterTag(String qName, Attributes attrs) {
        throwOnEnterTag();
    }

    @Override
    protected void exitTag(String qName) {
        throwOnExitTag();
    }

    @Override
    public Component instantiate(InstantiationContext ctx) {
        return Component.keybind(ctx.substituteVars(keybind));
    }

    @Override
    public void parseText(String text) {
        keybind += text.strip();
    }
}
