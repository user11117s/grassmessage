package com.array64.grassmessage.internal.components.concrete;

import com.array64.grassmessage.internal.components.GComponent;
import com.array64.grassmessage.internal.components.GComponentRegistry;
import com.array64.grassmessage.internal.components.InstantiationContext;
import com.array64.grassmessage.internal.components.click.*;
import com.array64.grassmessage.internal.components.GAbstractComponent;
import com.array64.grassmessage.internal.xml.DepthTracker;
import com.array64.grassmessage.internal.xml.properties.PropertyHolder;
import com.array64.grassmessage.internal.xml.properties.TextHolder;
import com.array64.grassmessage.internal.xml.properties.XmlPropertyMeta;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickCallback;
import net.kyori.adventure.text.event.ClickEvent;
import org.xml.sax.Attributes;

import java.util.List;
import java.util.Map;

public class GClickComponent extends GAbstractComponent {
    private PropertyHolder propertyHolder;
    private final GComponentRegistry componentRegistry;
    private GComponent mainContent;
    private ClickType type;
    private ClickPayload payload;

    public GClickComponent(GComponentRegistry componentRegistry) {
        this.componentRegistry = componentRegistry;
    }


    @Override
    public void onStart() {
        DepthTracker depthTracker = getDepthTracker();

        propertyHolder = new PropertyHolder(Map.of(
            "content", componentRegistry::createCompositeComponent,
            "run_command", TextHolder::new,
            "suggest_command", TextHolder::new,
            "open_url", TextHolder::new,
            "change_page", TextHolder::new,
            "copy_to_clipboard", TextHolder::new,
            "show_dialog", TextHolder::new,
            "callback", TextHolder::new,
            "custom", () -> new PropertyHolder(Map.of(
                "id", TextHolder::new,
                "payload", TextHolder::new
            ), depthTracker)
        ), depthTracker);
    }

    @Override
    protected void enterTag(String qName, Attributes attrs) {
        propertyHolder.startTag(qName, attrs);
    }

    @Override
    protected void exitTag(String qName) {
        propertyHolder.endTag(qName);
    }

    @Override
    public void parseText(String text) {
        propertyHolder.parseText(text);
    }

    @SuppressWarnings("unchecked")
    private void parseCustomProperties(List<?> properties) {
        String id = "";
        String customPayload = "{}";
        boolean isVar = false;

        for(var propertyMeta : (List<XmlPropertyMeta>) properties) {
            switch(propertyMeta.propertyName()) {
                case "id" -> id = propertyMeta.getValue(String.class);
                case "spayload" -> customPayload = propertyMeta.getValue(String.class);
                case "vpayload" -> {
                    isVar = true;
                    customPayload = propertyMeta.attrs().getValue("src");
                }
            }
        }

        payload = new CustomClickPayload(id, customPayload, isVar);
    }

    @Override
    public void onEnd() {
        List<XmlPropertyMeta> properties = propertyHolder.get();
        for(var propertyMeta : properties) {
            switch(propertyMeta.propertyName()) {
                case "content" -> mainContent = propertyMeta.getValue(GComponent.class);
                case "custom" -> {
                    type = ClickType.CUSTOM;
                    parseCustomProperties(propertyMeta.getValue(List.class));
                }
                case "show_dialog" -> {
                    type = ClickType.SHOW_DIALOG;
                    payload = new DialogClickPayload(propertyMeta.attrs().getValue("src"));
                }
                case "callback" -> {
                    type = ClickType.CALLBACK;
                    payload = new CallbackClickPayload(
                        propertyMeta.attrs().getValue("src"),
                        propertyMeta.attrs().getValue("duration"),
                        propertyMeta.attrs().getValue("uses")
                    );
                }
                case "change_page" -> {
                    type = ClickType.CHANGE_PAGE;
                    payload = new IntegerClickPayload(propertyMeta.getValue(String.class));
                }
                case "run_command", "suggest_command", "open_url", "open_file", "copy_to_clipboard" -> {
                    payload = new StringClickPayload(propertyMeta.getValue(String.class));
                    type = switch(propertyMeta.propertyName()) {
                        case "run_command" -> ClickType.RUN_COMMAND;
                        case "suggest_command" -> ClickType.SUGGEST_COMMAND;
                        case "open_url" -> ClickType.OPEN_URL;
                        case "open_file" -> ClickType.OPEN_FILE;
                        case "copy_to_clipboard" -> ClickType.COPY_TO_CLIPBOARD;
                        default -> throw new IllegalStateException("[THIS SHOULD NEVER HAPPEN] Unexpected click action: " + propertyMeta.propertyName());
                    };
                }
            }
        }
    }

    @Override
    protected Component instantiate(InstantiationContext ctx) {
        Component parent = Component.empty();
        parent = mainContent.instantiateInParent(parent, ctx);
        if(type == ClickType.CALLBACK) {
            CallbackClickPayload callbackPayload = (CallbackClickPayload) payload;
            return parent.clickEvent(
                ClickEvent.callback(
                    callbackPayload.getCallback(ctx),
                    ClickCallback.Options.builder()
                        .lifetime(callbackPayload.getDuration(ctx))
                        .uses(callbackPayload.getUses(ctx))
                        .build()
                )
            );
        }
        else {
            if(type == ClickType.CHANGE_PAGE)
                if(((ClickEvent.Payload.Int) payload.getPayload(ctx)).integer() < 1)
                    throw new IllegalArgumentException("Page number is less than 1.");

            return parent.clickEvent(ClickEvent.clickEvent(type.getAction(), payload.getPayload(ctx)));
        }
    }
}
