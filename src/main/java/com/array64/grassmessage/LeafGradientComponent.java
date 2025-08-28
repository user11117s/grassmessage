package com.array64.grassmessage;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.xml.sax.Attributes;

public class LeafGradientComponent extends AbstractComponent {
    private String text = "";
    private final MutableReference<GradientData> gradientReference;

    public LeafGradientComponent(MutableReference<GradientData> gradientReference) {
        this.gradientReference = gradientReference;
    }

    @Override
    protected void enterTag(String qName, Attributes attrs) {

    }

    @Override
    protected void exitTag(String qName) {

    }

    @Override
    public void modifyParent(BaseComponent parent) {
        int length = text.length();
        GradientData gradient = gradientReference.get();

        for(int i = 0; i < length; i++) {
            TextComponent component = new TextComponent(Character.toString(text.charAt(i)));
            Color color = gradient.evaluate((float) i / Math.max(1f, length - 1));
            component.setColor(ChatColor.of(color.toString()));

            parent.addExtra(component);
        }
    }

    @Override
    public void parseText(String text) {
        this.text += text;
    }
}
