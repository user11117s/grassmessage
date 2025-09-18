package com.array64.grassmessage.components.hover;

import com.array64.grassmessage.components.InstantiationContext;
import net.md_5.bungee.api.chat.ItemTag;
import net.md_5.bungee.api.chat.hover.content.Content;
import net.md_5.bungee.api.chat.hover.content.Item;

public class ItemHoveredContent implements HoveredContent {
    private final String id;
    private final int count;
    private final String tag;

    public ItemHoveredContent(String id, int count, String tag) {
        this.id = id;
        this.count = count;
        this.tag = tag;
    }

    @Override
    public Content instantiate(InstantiationContext ctx) {
        return new Item(ctx.substituteVars(id), count, ItemTag.ofNbt(ctx.substituteVars(tag)));
    }
}
