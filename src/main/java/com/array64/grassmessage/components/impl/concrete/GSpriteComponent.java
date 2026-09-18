package com.array64.grassmessage.components.impl.concrete;

import com.array64.grassmessage.components.InstantiationContext;
import com.array64.grassmessage.components.impl.GAbstractComponent;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.object.ObjectContents;
import org.xml.sax.Attributes;

public class GSpriteComponent extends GAbstractComponent {
    private final String atlas;
    private final String path;

    public GSpriteComponent(String atlas, String path) {
        this.atlas = atlas;
        this.path = path;
    }

    @Override
    protected void enterTag(String qName, Attributes attrs) {
        throwOnEnterTag();
    }

    @Override
    protected void exitTag(String qName) {
        throwOnExitTag();
    }

    @Override
    protected Component instantiate(InstantiationContext ctx) {
        Key pathKey = Key.key(ctx.substituteVars(path));
        Key atlasKey = atlas == null ? null : Key.key(ctx.substituteVars(atlas));

        return Component.object(atlasKey == null ? ObjectContents.sprite(pathKey) : ObjectContents.sprite(atlasKey, pathKey));
    }

    @Override
    public void parseText(String text) {
        throwOnParseText();
    }
}
