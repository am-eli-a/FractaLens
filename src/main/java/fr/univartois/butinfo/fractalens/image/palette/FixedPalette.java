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
import java.util.List;

/**
 * The FixedPalette class is a palette defined by a fixed list of colors.
 * The ratio between the number of iterations performed and the maximum number of
 * iterations gives a position along this list, and the returned color is a gradient
 * between the two colors surrounding this position.
 * The first color is used when no iteration is performed, and the last one when the
 * maximum number of iterations is reached (i.e., when the sequence does not diverge).
 *
 * @author Matty Fourmestraux
 *
 * @version 0.1.0
 */
public class FixedPalette implements IPalette {

	/**
	 * The colors of this palette, from the one used for 0 iterations to the one used
	 * for the maximum number of iterations.
	 */
	private final List<Color> colorList;

	/**
	 * Creates a new FixedPalette.
	 *
	 * @param colorList The colors of the palette, from the one used for 0 iterations to
	 *        the one used for the maximum number of iterations.
	 *
	 * @throws IllegalArgumentException If fewer than two colors are given.
	 */
	public FixedPalette(List<Color> colorList) {
		if (colorList.size() < 2) {
			throw new IllegalArgumentException("Trop peu de couleurs");
		}
		this.colorList = List.copyOf(colorList);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see fr.univartois.butinfo.fractalens.image.palette.IPalette#getColor(int, int)
	 */
	@Override
	public Color getColor(int nbIteration, int maxIteration) {
		if ((nbIteration < 0) || (nbIteration > maxIteration)) {
			throw new IllegalArgumentException("Erreur sur nbIteration");
		}
		if (nbIteration == maxIteration) {
			return colorList.getLast();
		}

		double ratio = (double) nbIteration / maxIteration;
		double position = ratio * (colorList.size() - 1);
		int index = (int) position;
		double localRatio = position - index;

		Color color1 = colorList.get(index);
		Color color2 = colorList.get(index + 1);
		double r = color1.getRed() + localRatio * (color2.getRed() - color1.getRed());
		double g = color1.getGreen() + localRatio * (color2.getGreen() - color1.getGreen());
		double b = color1.getBlue() + localRatio * (color2.getBlue() - color1.getBlue());
		return new Color((int) Math.round(r), (int) Math.round(g), (int) Math.round(b));
	}

}
