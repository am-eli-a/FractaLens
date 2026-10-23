package fr.univartois.butinfo.fractalens.image.palette;

import java.awt.Color;

/**
 * The IPalette interface defines a strategy for choosing the color of a pixel, from
 * the number of iterations performed on the sequence starting at this pixel.
 *
 *
 * @version 0.1.0
 */
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
    Color getColor(int nbIteration, int maxIteration);
}