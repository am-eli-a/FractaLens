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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Color;

import org.junit.jupiter.api.Test;

/**
 * The GradientPaletteTest class tests the {@link GradientPalette} class.
 *
 * All the tests use a gradient from black (0, 0, 0) to (200, 100, 50), so that
 * the color in the middle of the gradient can be computed without rounding.
 *
 * @author Baptiste Wagee
 *
 * @version 0.1.0
 */
class GradientPaletteTest {

  /**
   * Test method for {@link GradientPalette#getColor(int, int)} when no
   * iteration has been performed: the start color is expected.
   */
  @Test
  void testColorAtZeroIterations() {
    Color start = new Color(0, 0, 0);
    Color end = new Color(200, 100, 50);
    GradientPalette palette = new GradientPalette(start, end);

    assertEquals(start, palette.getColor(0, 100));
  }

  /**
   * Test method for {@link GradientPalette#getColor(int, int)} when the
   * maximum number of iterations has been reached: the end color is expected.
   */
  @Test
  void testColorAtMaxIterations() {
    Color start = new Color(0, 0, 0);
    Color end = new Color(200, 100, 50);
    GradientPalette palette = new GradientPalette(start, end);

    assertEquals(end, palette.getColor(100, 100));
  }

  /**
   * Test method for {@link GradientPalette#getColor(int, int)} when half of
   * the maximum number of iterations has been performed: the color in the
   * middle of the gradient is expected.
   */
  @Test
  void testColorAtHalfIterations() {
    Color start = new Color(0, 0, 0);
    Color end = new Color(200, 100, 50);
    GradientPalette palette = new GradientPalette(start, end);

    assertEquals(new Color(100, 50, 25), palette.getColor(50, 100));
  }


  /**
   * Test method for {@link GradientPalette#getColor(int, int)} when the
   * number of iterations is invalid: an exception is expected, both when it
   * is greater than the maximum and when it is negative.
   */
  @Test
  void testInvalidNumberOfIterations() {
    GradientPalette palette = new GradientPalette(Color.BLACK, Color.WHITE);

    assertThrows(IllegalArgumentException.class, () -> palette.getColor(101, 100));
    assertThrows(IllegalArgumentException.class, () -> palette.getColor(-1, 100));
  }
}