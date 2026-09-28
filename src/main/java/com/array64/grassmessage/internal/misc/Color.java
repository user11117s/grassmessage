package com.array64.grassmessage.internal.misc;

import net.kyori.adventure.text.format.TextColor;

public record Color(float red, float green, float blue) {
    public Color(TextColor color) {
        this(color.red(), color.green(), color.blue());
    }

    public Color add(Color other) {
        return new Color(this.red + other.red, this.green + other.green, this.blue + other.blue);
    }

    public Color sub(Color other) {
        return new Color(this.red - other.red, this.green - other.green, this.blue - other.blue);
    }

    public Color mul(float scale) {
        return new Color(this.red * scale, this.green * scale, this.blue * scale);
    }

    public Color div(float divisor) {
        return new Color(this.red / divisor, this.green / divisor, this.blue / divisor);
    }

    public TextColor toAdventureColor() {
        return TextColor.color((int) red, (int) green, (int) blue);
    }
}
