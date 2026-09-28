package com.array64.grassmessage.api;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.dialog.DialogLike;
import net.kyori.adventure.nbt.api.BinaryTagHolder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickCallback;
import net.kyori.adventure.text.event.DataComponentValue;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.util.ARGBLike;

import java.time.temporal.TemporalAmount;
import java.util.UUID;

public interface MessageInstance {
    MessageInstance addVar(String name, Object value);

    default MessageInstance addString(String name, String value) {
        return addVar(name, value);
    }

    default MessageInstance addInt(String name, int value) {
        return addVar(name, value);
    }

    default MessageInstance addUUID(String name, UUID value) {
        return addVar(name, value);
    }

    default MessageInstance addData(String name, DataComponentValue value) {
        return addVar(name, value);
    }

    default MessageInstance addNBT(String name, BinaryTagHolder value) {
        return addVar(name, value);
    }

    default MessageInstance addDialog(String name, DialogLike value) {
        return addVar(name, value);
    }

    default MessageInstance addAdventureComponent(String name, Component value) {
        return addVar(name, value);
    }

    default MessageInstance addClickCallback(String name, ClickCallback<Audience> value) {
        return addVar(name, value);
    }

    default MessageInstance addDuration(String name, TemporalAmount value) {
        return addVar(name, value);
    }

    default MessageInstance addTextColor(String name, TextColor value) {
        return addVar(name, value);
    }

    default MessageInstance addShadowColor(String name, ARGBLike value) {
        return addVar(name, value);
    }

    Component build();
}
