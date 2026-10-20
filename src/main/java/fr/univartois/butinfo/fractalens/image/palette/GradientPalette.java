/*
 * FractaLens - A Fractal Generator Library Developed in Lens.
 * Copyright (c) 2022-2026 Romain Wallon - Université d'Artois.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package fr.univartois.butinfo.fractalens.image.palette;

import java.awt.Color;

/**
 * The GradientPalette class is a palette computing a gradient between two
 * colors, depending on the number of iterations performed on the sequence.
 *
 * The color is computed with the ratio p between the number of iterations and
 * the maximum number of iterations: each component is equal to
 * {@code c1 + p * (c2 - c1)}, where c1 and c2 are the components of the start
 * and end colors.
 *
 * @author Baptiste Wagee
 *
 * @version 0.1.0
 */
public class GradientPalette implements IPalette{

  /**
   * The color used when no iteration has been performed.
   */
  private final Color start;
  /**
   * The color used when the maximum number of iterations has been reached.
   */
  private final Color end;

  /**
   * Creates a new GradientPalette.
   *
   * @param start The color used when no iteration has been performed.
   * @param end The color used when the maximum number of iterations has been
   *        reached.
   */
  public GradientPalette(Color start, Color end) {
    this.start = start;
    this.end = end;
  }

  /**
   * Computes the color associated with a number of iterations, as a gradient
   * between the start and end colors.
   *
   * @param nbIteration The number of iterations performed on the sequence.
   * @param maxIteration The maximum number of iterations that can be
   *        performed.
   *
   * @return The color of the gradient for the given number of iterations.
   *
   * @throws IllegalArgumentException If the number of iterations is negative
   *         or greater than the maximum number of iterations.
   *
   * @see IPalette#getColor(int, int)
   */
  @Override
  public Color getColor(int nbIteration, int maxIteration) {
    if (nbIteration > maxIteration || nbIteration < 0) {
      throw new IllegalArgumentException("The number of iterations must be between 0 and the maximum OR the number of iterations is too big");
    }
    float ratio = (float) nbIteration / maxIteration;
    int red = (int) (start.getRed() + ratio * (end.getRed() - start.getRed()));
    int blue = (int) (start.getBlue() + ratio * (end.getBlue() - start.getBlue()));
    int green = (int) (start.getGreen() + ratio * (end.getGreen() - start.getGreen()));
    return new Color(red, green, blue);
  }

}
