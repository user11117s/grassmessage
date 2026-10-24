package com.array64.grassmessage.internal.components.concrete;

import com.array64.grassmessage.internal.components.GComponent;
import com.array64.grassmessage.internal.components.GComponentRegistry;
import com.array64.grassmessage.internal.components.InstantiationContext;
import com.array64.grassmessage.internal.components.GAbstractComponent;
import com.array64.grassmessage.internal.xml.DepthTrackable;
import com.array64.grassmessage.internal.xml.DepthTracker;
import net.kyori.adventure.text.Component;
import org.xml.sax.Attributes;

import java.util.ArrayList;
import java.util.List;

public class GPreformattedComponent extends GAbstractComponent {
    private final boolean dedent, trimBounds, inline;
    private final List<Event> events = new ArrayList<>();

    private GComponent component;
    private final GComponentRegistry registry;

    private int minWhitespace = Integer.MAX_VALUE;
    private int whitespace = 0;
    private boolean nonWhitespaceFound = false;

    public GPreformattedComponent(String dedent, String trimBounds, String inlineStr, GComponentRegistry registry) {
        this.dedent = !("0".equals(dedent) || "false".equals(dedent));
        this.trimBounds = !("0".equals(trimBounds) || "false".equals(trimBounds));
        this.inline = "1".equals(inlineStr) || "true".equals(inlineStr);

        this.registry = registry;
        registry.countPreformat();
    }

    @Override
    protected void enterTag(String qName, Attributes attrs) {
        processTag();
        events.add(new Event(Event.Type.ENTER, qName, attrs));
    }

    @Override
    protected void exitTag(String qName) {
        if(dedent) processTag();
        events.add(new Event(Event.Type.EXIT, qName));
    }

    @Override
    public void parseText(String text) {
        if(dedent) processText(text);
        events.add(new Event(Event.Type.PARSE_TEXT, text));
    }

    @Override
    public void onEnd() {
        format();

        component = registry.createCompositeComponent();
        getDepthTracker().enter();
        events.forEach(event -> {
            switch(event.type) {
                case ENTER -> {
                    component.startTag(event.value, event.attrs);
                    getDepthTracker().enter();
                }
                case EXIT -> {
                    getDepthTracker().exit();
                    component.endTag(event.value);
                }
                case PARSE_TEXT -> component.parseText(event.value);
            }
        });
        getDepthTracker().exit();
        component.onEnd();

        registry.removePreformat();
    }

    @Override
    protected Component instantiate(InstantiationContext ctx) {
        Component parent = Component.empty();
        parent = component.instantiateInParent(parent, ctx);
        return parent;
    }

    // Preformatting functions

    private void processText(String text) {
        for(int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if(c == '\n') {
                whitespace = 0;
                nonWhitespaceFound = false;
                continue;
            }
            if(!nonWhitespaceFound && Character.isWhitespace(c)) whitespace++;
            else {
                nonWhitespaceFound = true;
                if(whitespace < minWhitespace) minWhitespace = whitespace;
            }
        }
    }

    private void processTag() {
        nonWhitespaceFound = true;
        if(whitespace < minWhitespace) minWhitespace = whitespace;
    }

    private void format() {
        if(dedent) {
            String firstTarget = "(?m)^\\h{" + minWhitespace + "}";
            String defaultTarget = "(\\R)\\h{" + minWhitespace + "}";

            for(Event event : events) {
                if(event.type == Event.Type.PARSE_TEXT) {
                    // Replace all newline sequences with spaces ahead of them with just the newline
                    // except for the text just after the <pre> tag, which also has to face dedent.
                    event.value = event == events.get(0)
                        ? event.value.replaceAll(firstTarget, "")
                        : event.value.replaceAll(defaultTarget, "$1");
                }
            }
        }

        Event firstTextEvent = null, lastTextEvent = null;

        for(Event event : events) {
            if(event.type == Event.Type.PARSE_TEXT) {
                if(firstTextEvent == null) firstTextEvent = event;
                lastTextEvent = event;
            }
        }

        if(firstTextEvent != null) { // Ensure there is at least one text event
            firstTextEvent.value = firstTextEvent.value.replaceAll(trimBounds ? "^\\s*\\R" : "^\\h*\\R", "");
            lastTextEvent.value = lastTextEvent.value.replaceAll(trimBounds ? "\\R\\s*$" : "\\R\\h*$", "");
        }
    }

    public boolean isBlock() {
        return !inline;
    }

    private static class Event {
        private enum Type {
            ENTER,
            EXIT,
            PARSE_TEXT
        }
        private final Type type;
        private String value;
        private final Attributes attrs;

        private Event(Type type, String value) {
            this(type, value, null);
        }

        private Event(Type type, String value, Attributes attrs) {
            this.type = type;
            this.value = value;
            this.attrs = attrs;
        }
    }
}
