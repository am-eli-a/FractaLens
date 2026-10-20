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
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * The FixedPaletteTest tests the {@link FixedPalette} class.
 *
 * @author Matty Fourmestraux
 *
 * @version 0.1.0
 */
class FixedPaletteTest {

	/**
	 * The maximum number of iterations used in the tests.
	 */
	private static final int MAX_ITERATIONS = 100;

	/**
	 * A palette going from red to green, then from green to blue.
	 */
	private final IPalette palette = new FixedPalette(List.of(Color.RED, Color.GREEN, Color.BLUE));

	/**
	 * Test method for {@link FixedPalette#FixedPalette(List)} when too few colors are
	 * given.
	 */
	@Test
	void testConstructorWithTooFewColors() {
		List<Color> colors = List.of(Color.RED);
		assertThrows(IllegalArgumentException.class, () -> new FixedPalette(colors));
	}

	/**
	 * Test method for {@link FixedPalette#getColor(int, int)} with a negative number of
	 * iterations.
	 */
	@Test
	void testGetColorWithNegativeIterations() {
		assertThrows(IllegalArgumentException.class, () -> palette.getColor(-1, MAX_ITERATIONS));
	}

	/**
	 * Test method for {@link FixedPalette#getColor(int, int)} with more iterations than
	 * the maximum.
	 */
	@Test
	void testGetColorWithTooManyIterations() {
		assertThrows(IllegalArgumentException.class,
				() -> palette.getColor(MAX_ITERATIONS + 1, MAX_ITERATIONS));
	}

	/**
	 * Test method for {@link FixedPalette#getColor(int, int)} when no iteration has been
	 * performed.
	 */
	@Test
	void testGetColorWithNoIteration() {
		assertEquals(Color.RED, palette.getColor(0, MAX_ITERATIONS));
	}

	/**
	 * Test method for {@link FixedPalette#getColor(int, int)} when the maximum number of
	 * iterations has been performed.
	 */
	@Test
	void testGetColorWithMaxIterations() {
		assertEquals(Color.BLUE, palette.getColor(MAX_ITERATIONS, MAX_ITERATIONS));
	}

	/**
	 * Test method for {@link FixedPalette#getColor(int, int)} when the position falls
	 * exactly on an intermediate color.
	 */
	@Test
	void testGetColorOnIntermediateColor() {
		assertEquals(Color.GREEN, palette.getColor(50, MAX_ITERATIONS));
	}

	/**
	 * Test method for {@link FixedPalette#getColor(int, int)} in the middle of a segment
	 * of the gradient.
	 */
	@Test
	void testGetColorInTheMiddleOfASegment() {
		IPalette blackToWhite = new FixedPalette(List.of(Color.BLACK, Color.WHITE));
		assertEquals(new Color(128, 128, 128), blackToWhite.getColor(50, MAX_ITERATIONS));
	}

	/**
	 * Test method for {@link FixedPalette#getColor(int, int)} in the second segment of
	 * the gradient.
	 */
	@Test
	void testGetColorInSecondSegment() {
		// position = 0.75 * 2 = 1.5, so halfway between green and blue.
		assertEquals(new Color(0, 128, 128), palette.getColor(75, MAX_ITERATIONS));
	}

	/**
	 * Test method for {@link FixedPalette#FixedPalette(List)}, checking that modifying the
	 * given list does not modify the palette.
	 */
	@Test
	void testPaletteIsNotModifiedWithTheGivenList() {
		List<Color> colors = new ArrayList<>(List.of(Color.BLACK, Color.WHITE));
		IPalette blackToWhite = new FixedPalette(colors);

		colors.set(0, Color.RED);

		assertEquals(Color.BLACK, blackToWhite.getColor(0, MAX_ITERATIONS));
	}

}
