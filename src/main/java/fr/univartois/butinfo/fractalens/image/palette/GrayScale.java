package fr.univartois.butinfo.fractalens.image.palette;

import java.awt.*;

public class GrayScale implements IPalette {

    @Override
    public Color getCouleur(int nbIteration, int maxIteration) {
        if  (nbIteration > maxIteration ||  nbIteration < 0) {
            throw new IllegalArgumentException("The number of iteration can't be greater than " + maxIteration + " or be negative");
        }
        float ratio = (float) nbIteration / maxIteration;
        float teinte = 1 - ratio;
        return new Color(teinte, teinte, teinte);
    }

}
