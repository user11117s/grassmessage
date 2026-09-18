package com.array64.grassmessage.components.click;

import net.kyori.adventure.text.event.ClickEvent.Action;

public enum ClickType {
    CUSTOM(Action.CUSTOM),
    SHOW_DIALOG(Action.SHOW_DIALOG),
    CALLBACK(null),
    CHANGE_PAGE(Action.CHANGE_PAGE),
    RUN_COMMAND(Action.RUN_COMMAND),
    SUGGEST_COMMAND(Action.SUGGEST_COMMAND),
    OPEN_URL(Action.OPEN_URL),
    OPEN_FILE(Action.OPEN_FILE),
    COPY_TO_CLIPBOARD(Action.COPY_TO_CLIPBOARD);

    private final Action action;

    private ClickType(Action action) {
        this.action = action;
    }

    public Action getAction() {
        return action;
    }
}
