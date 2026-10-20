package fr.univartois.butinfo.fractalens.image.palette;

import java.awt.*;

public interface IPalette {

    /**
     * Computes the color associated with a number of iterations.
     *
     * @param nbIteration The number of iterations performed on the sequence.
     * @param maxIteration The maximum number of iterations that can be performed.
     *
     * @return The color to use for the given number of iterations.
     *
     * @throws IllegalArgumentException If the number of iterations is negative or
     *         greater than the maximum number of iterations.
     */
    Color getCouleur(int nbIteration, int maxIteration);
}
