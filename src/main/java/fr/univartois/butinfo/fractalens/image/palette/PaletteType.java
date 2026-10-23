package fr.univartois.butinfo.fractalens.image.palette;

import java.awt.Color;
import java.util.List;

/**
 * The PaletteType enumeration lists the palettes of colors that can be used to
 * generate a fractal.
 * Each constant is able to create a ready-to-use palette, with default colors.
 * To use other colors, a palette can be created directly from its class instead.
 *
 * @author Amelia Dufour-Lazrek
 *
 * @version 0.1.0
 */
public enum PaletteType {

    /**
     * A {@link FixedPalette} going from white to black, through three shades of blue.
     */
    FIXED,

    /**
     * A {@link GradientPalette} going from dark green to black.
     */
    GRADIENT,

    /**
     * A {@link GrayScale} palette, going from white to black.
     */
    GRAYSCALE;

    /**
     * Creates the palette associated with this constant.
     * A new palette is created at each call.
     *
     * @return The palette associated with this constant.
     */
    public IPalette getPalette() {
        return switch (this) {
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

    IPalette firePalette = new FixedPalette(List.of(
            new Color(255, 255, 220), Color.YELLOW, Color.ORANGE, Color.RED, Color.BLACK));

    IPalette nightPalette = new FixedPalette(List.of(
            new Color(10, 10, 40), new Color(60, 30, 120), new Color(220, 80, 160),
            new Color(255, 220, 120), Color.BLACK));

    IPalette forestPalette = new FixedPalette(List.of(
            new Color(5, 20, 10), new Color(20, 90, 40), new Color(120, 200, 80),
            new Color(230, 255, 200), Color.BLACK));
}