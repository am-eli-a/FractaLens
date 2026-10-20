package fr.univartois.butinfo.fractalens.image.palette;

import java.awt.*;

public class InversionDecorator extends PaletteDecorator {
    public InversionDecorator(IPalette palette) {
        super(palette);
    }

    @Override
    public Color getColor(int nbIteration, int maxIteration) {
        Color couleur = palette.getColor(nbIteration, maxIteration);
        return new Color(255-couleur.getRed(), 255-couleur.getGreen(), 255-couleur.getBlue());
    }
}
