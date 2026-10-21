package fr.univartois.butinfo.fractalens.image.palette;

import java.awt.Color;
import java.util.List;

public enum PaletteType {
    FIXED, GRADIENT, GRAYSCALE;

    public IPalette getPalette() {
        return switch(this) {
            case FIXED -> new FixedPalette(List.of(
                    Color.WHITE,
                    new Color(150, 220, 255),
                    new Color(30, 120, 220),
                    new Color(10, 30, 90),
                    Color.BLACK));
            case GRADIENT -> new GradientPalette(new Color(10, 60, 30), Color.BLACK);
            case GRAYSCALE -> new GrayScale();
        };
    }

}
