package fr.univartois.butinfo.fractalens.image.palette;

import java.awt.*;

public class MasqueDecorator extends PaletteDecorator {

    final boolean red;
    final boolean green;
    final boolean blue;


    public MasqueDecorator(IPalette palette, boolean red, boolean green, boolean blue) {
        super(palette);
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    @Override
    public Color getColor(int nbIteration, int maxIteration) {
        Color couleur = palette.getColor(nbIteration, maxIteration);
        int r = red ? couleur.getRed() : 0;
        int g = green ? couleur.getGreen() : 0;
        int b = blue? couleur.getBlue() : 0;
        return new Color(r, g, b);
    }
}
