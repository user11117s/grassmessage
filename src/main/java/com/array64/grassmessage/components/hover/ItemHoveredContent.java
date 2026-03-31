package com.array64.grassmessage.components.hover;

import com.array64.grassmessage.components.InstantiationContext;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.api.BinaryTagHolder;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.event.HoverEventSource;

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
    public HoverEventSource<?> instantiate(InstantiationContext ctx) {
        if(tag == null)
            return HoverEvent.showItem(Key.key(ctx.substituteVars(id)), count);
        else
            return HoverEvent.showItem(
                    Key.key(ctx.substituteVars(id)),
                    count,
                    BinaryTagHolder.binaryTagHolder(ctx.substituteVars(tag))
            );
    }
}
