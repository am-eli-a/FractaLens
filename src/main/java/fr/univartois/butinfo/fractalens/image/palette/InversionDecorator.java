package fr.univartois.butinfo.fractalens.image.palette;

import java.awt.Color;

/**
 * The InversionDecorator class is a decorator of palette that inverts the colors given
 * by the decorated palette.
 * Each component (red, green and blue) of a color is replaced by 255 minus its value:
 * for instance, white becomes black.
 *
 * @author Amelia Dufour-Lazrek
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

    @Override
    public Color getColor(int nbIteration, int maxIteration) {
        Color couleur = palette.getColor(nbIteration, maxIteration);
        return new Color(255 - couleur.getRed(), 255 - couleur.getGreen(),255 - couleur.getBlue());
    }

}