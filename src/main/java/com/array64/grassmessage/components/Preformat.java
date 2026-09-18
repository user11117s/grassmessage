package com.array64.grassmessage.components;

import com.array64.grassmessage.components.impl.concrete.GTextComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Preformat {
    private boolean dedent = true;
    private boolean trimBounds = true;

    private int minWhitespace = Integer.MAX_VALUE;

    // State to be tracked throughout all addTextComponent calls
    private int whitespace = 0;
    private boolean nonWhitespaceFound = false;

    private final List<GTextComponent> textComponents = new ArrayList<>();

    public boolean getDedent() {
        return dedent;
    }

    public void setDedent(boolean dedent) {
        this.dedent = dedent;
    }

    public boolean getTrimBounds() {
        return trimBounds;
    }

    public void setTrimBounds(boolean trimBounds) {
        this.trimBounds = trimBounds;
    }

    public void addTextComponent(GTextComponent textComponent) {
        textComponents.add(textComponent);
        String text = textComponent.getText();
        if(dedent && !text.isEmpty()) {
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
    }

    // Ironic, considering this is for a preformatted element.
    public void format() {
        if(dedent) {
            for(GTextComponent textComponent : textComponents) {
                String[] lines = textComponent.getText().split("\n");
                for(int i = 0; i < lines.length; i++) {
                    if(!lines[i].isBlank())
                        lines[i] = lines[i].substring(minWhitespace);
                }
                textComponent.setText(String.join("\n", lines));
            }
        }
        boolean trimmedLeading = false;
        for(GTextComponent textComponent : textComponents) {
            if(trimmedLeading) break;

            String[] lines = textComponent.getText().split("\n");
            int j = 0;
            for(; j < lines.length; j++) {
                if(!trimBounds && j > 0) break; // Allow for only one line if trim_bounds is false
                if(!lines[j].isBlank()) {
                    trimmedLeading = true;
                    break;
                }
            }
            textComponent.setText(String.join("\n", Arrays.copyOfRange(lines, j, lines.length)));
            if(!trimBounds) break; // Allow for only one line if trim_bounds is false
        }

        boolean trimmedTrailing = false;
        for(int i = textComponents.size() - 1; i > -1; i--) {
            if(trimmedTrailing) break;

            String[] lines = textComponents.get(i).getText().split("\n");
            int j = lines.length;
            for(; j > 0; j--) {
                if(!trimBounds && j < lines.length) break; // Allow for only one line if trim_bounds is false
                if(!lines[j - 1].isBlank()) {
                    trimmedTrailing = true;
                    break;
                }
            }
            textComponents.get(i).setText(String.join("\n", Arrays.copyOfRange(lines, 0, j)));
            if(!trimBounds) break; // Allow for only one line if trim_bounds is false
        }
    }
}
