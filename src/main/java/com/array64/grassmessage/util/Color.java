package com.array64.grassmessage.util;

public record Color(float red, float green, float blue) {
    public Color(String hexCode) {
        this(
            Integer.valueOf(hexCode.substring(1, 3), 16), // red
            Integer.valueOf(hexCode.substring(3, 5), 16), // green
            Integer.valueOf(hexCode.substring(5, 7), 16) // blue
        );
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

    @Override
    public String toString() {
        int red = (int) this.red,
            green = (int) this.green,
            blue = (int) this.blue;
        return String.format("#%02x%02x%02x", red, green, blue);
    }
}
