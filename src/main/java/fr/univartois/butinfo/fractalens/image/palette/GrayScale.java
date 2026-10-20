package fr.univartois.butinfo.fractalens.image.palette;

import java.awt.*;

public class GrayScale implements IPalette {

    private final int maxIteration;

    public GrayScale(int maxIteration) {
        this.maxIteration = maxIteration;
    }

    public int getMaxIteration() {
        return maxIteration;
    }

    @Override
    public Color getCouleur(int nbIteration) {
        if  (nbIteration > maxIteration) {
            throw new IllegalArgumentException("Max iteration can't be greater than " + maxIteration);
        }
        float ratio = (float) nbIteration / maxIteration;
        float teinte = 1 - ratio;
        return new Color(teinte, teinte, teinte);
    }

}
