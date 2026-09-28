package com.array64.grassmessage.internal.impl;

import com.array64.grassmessage.api.MessageInstance;
import com.array64.grassmessage.internal.data.Message;
import net.kyori.adventure.text.Component;

import java.util.HashMap;
import java.util.Map;

public class MessageInstanceImpl implements MessageInstance {
    private final Map<String, Object> vars;
    private final Message message;

    public MessageInstanceImpl(Message message) {
        this.message = message;
        this.vars = new HashMap<>();
    }

    @Override
    public MessageInstance addVar(String name, Object value) {
        vars.put(name, value);
        return this;
    }

    @Override
    public Component build() {
        return message.get(vars);
    }
}
