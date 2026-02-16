package com.array64.grassmessage.components.hover;

import com.array64.grassmessage.components.InstantiationContext;
import net.md_5.bungee.api.chat.hover.content.Content;

public interface HoveredContent {
    Content instantiate(InstantiationContext ctx);
}
