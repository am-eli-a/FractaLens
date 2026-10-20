package fr.univartois.butinfo.fractalens.image.palette;

import java.awt.*;

/**
 * The GrayScale class is a palette that associates a shade of gray with a number of
 * iterations.
 * The more iterations were performed, the darker the color is: a sequence that
 * diverges immediately gives white, while a sequence that reaches the maximum number
 * of iterations (and thus does not diverge) gives black.
 *
 * @author Amelia Dufour-Lazrek
 *
 * @version 0.1.0
 */
public class GrayScale implements IPalette {

    @Override
    public Color getColor(int nbIteration, int maxIteration) {
        if  (nbIteration > maxIteration ||  nbIteration < 0) {
            throw new IllegalArgumentException("The number of iteration can't be greater than " + maxIteration + " or be negative");
        }
        float ratio = (float) nbIteration / maxIteration;
        float color = 1 - ratio;
        return new Color(color, color, color);
    }

}
