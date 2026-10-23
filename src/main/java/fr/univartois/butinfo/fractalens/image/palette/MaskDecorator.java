package fr.univartois.butinfo.fractalens.image.palette;

import java.awt.Color;

/**
 * The MasqueDecorator class is a decorator of palette that only keeps some of the
 * components (red, green and blue) of the colors given by the decorated palette.
 * The components that are not kept are set to 0.
 *
 *
 * @version 0.1.0
 */
public class MaskDecorator extends PaletteDecorator {

    /**
     * Whether the red component is kept.
     */
    private final boolean red;

    /**
     * Whether the green component is kept.
     */
    private final boolean green;

    /**
     * Whether the blue component is kept.
     */
    private final boolean blue;

    /**
     * Creates a new MasqueDecorator.
     *
     * @param palette The palette to decorate.
     * @param red Whether the red component must be kept.
     * @param green Whether the green component must be kept.
     * @param blue Whether the blue component must be kept.
     */
    public MaskDecorator(IPalette palette, boolean red, boolean green, boolean blue) {
        super(palette);
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    /**
     * Computes the color given by the decorated palette, and only keeps the selected
     * components. The other components are set to 0.
     *
     * @param nbIteration The number of iterations performed on the sequence.
     * @param maxIteration The maximum number of iterations that can be performed.
     *
     * @return The color with only the selected components.
     *
     * @see IPalette#getColor(int, int)
     */
    @Override
    public Color getColor(int nbIteration, int maxIteration) {
        Color couleur = palette.getColor(nbIteration, maxIteration);
        int r = red ? couleur.getRed() : 0;
        int g = green ? couleur.getGreen() : 0;
        int b = blue ? couleur.getBlue() : 0;
        return new Color(r, g, b);
    }

}