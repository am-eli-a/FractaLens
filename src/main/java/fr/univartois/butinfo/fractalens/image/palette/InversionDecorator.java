package fr.univartois.butinfo.fractalens.image.palette;

import java.awt.Color;

/**
 * The InversionDecorator class is a decorator of palette that inverts the colors given
 * by the decorated palette.
 * Each component (red, green and blue) of a color is replaced by 255 minus its value:
 * for instance, white becomes black.
 *
 *
 * @version 0.1.0
 */
public class InversionDecorator extends PaletteDecorator {

    /**
     * Creates a new InversionDecorator.
     *
     * @param palette The palette to decorate.
     */
    public InversionDecorator(IPalette palette) {
        super(palette);
    }

    /**
     * Computes the color given by the decorated palette, and inverts it: each
     * component is replaced by 255 minus its value.
     *
     * @param nbIteration The number of iterations performed on the sequence.
     * @param maxIteration The maximum number of iterations that can be performed.
     *
     * @return The inverted color.
     *
     * @see IPalette#getColor(int, int)
     */
    @Override
    public Color getColor(int nbIteration, int maxIteration) {
        Color couleur = palette.getColor(nbIteration, maxIteration);
        return new Color(255 - couleur.getRed(), 255 - couleur.getGreen(),255 - couleur.getBlue());
    }

}