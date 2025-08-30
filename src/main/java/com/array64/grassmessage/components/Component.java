package com.array64.grassmessage.components;

import com.array64.grassmessage.xml.XMLParser;
import net.md_5.bungee.api.chat.BaseComponent;

public interface Component extends XMLParser {
    void instantiateInParent(BaseComponent parent, InstantiationContext ctx);
}
