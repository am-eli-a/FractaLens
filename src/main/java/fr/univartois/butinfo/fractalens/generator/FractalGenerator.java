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

package fr.univartois.butinfo.fractalens.generator;

import java.awt.Color;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.function.BiFunction;

import fr.univartois.butinfo.fractalens.complex.IComplex;
import fr.univartois.butinfo.fractalens.complex.IComplexPlane;
import fr.univartois.butinfo.fractalens.complex.sequence.IComplexSequence;
import fr.univartois.butinfo.fractalens.image.IFractalImage;

/**
 * The FractalGenerator class generates the image of a fractal based on a sequence of
 * complex numbers.
 * For each pixel of the image, it evaluates the sequence starting at the complex number
 * corresponding to this pixel, counts the number of iterations performed before the
 * sequence diverges (or the maximum number of iterations is reached), and uses a
 * palette to determine the color of the pixel.
 * This class only relies on interfaces, so that it is independent of the complex
 * plane, the sequence, the palette and the image that are actually used.
 *
 * @author Matty Fourmestraux
 *
 * @version 0.1.0
 */
public class FractalGenerator implements IFractalGenerator {

	/**
	 * The image on which the fractal is drawn.
	 */
	private final IFractalImage image;

	/**
	 * The complex plane used to associate a complex number with each pixel of the image.
	 */
	private final IComplexPlane plane;

	/**
	 * The maximum number of iterations to perform on the sequence for each pixel.
	 */
	private final int nbMaxIteration;

	/**
	 * The palette used to compute the color of a pixel from a number of iterations.
	 */
	private final IPalette palette;

	/**
	 * The path of the file in which the generated image is saved.
	 */
	private final String filePath;

	/**
	 * The function used to create the sequence to evaluate, from its first term and the
	 * maximum number of terms to compute.
	 */
	private final BiFunction<IComplex, Integer, IComplexSequence> sequenceBiFunction;

	/**
	 * Creates a new FractalGenerator.
	 *
	 * @param image The image on which the fractal is drawn.
	 * @param plane The complex plane used to associate a complex number with each pixel
	 *        of the image.
	 * @param nbMaxIteration The maximum number of iterations to perform on the sequence
	 *        for each pixel.
	 * @param palette The palette used to compute the color of a pixel from a number of
	 *        iterations.
	 * @param filePath The path of the file in which the generated image is saved.
	 * @param sequenceBiFunction The function used to create the sequence to evaluate,
	 *        from its first term and the maximum number of terms to compute.
	 */
	public FractalGenerator(IFractalImage image, IComplexPlane plane, int nbMaxIteration,
							IPalette palette, String filePath,
							BiFunction<IComplex, Integer, IComplexSequence> sequenceBiFunction) {
		this.image = image;
		this.plane = plane;
		this.nbMaxIteration = nbMaxIteration;
		this.palette = palette;
		this.filePath = filePath;
		this.sequenceBiFunction = sequenceBiFunction;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see fr.univartois.butinfo.fractalens.generator.IFractalGenerator#generateImage()
	 */
	@Override
	public IFractalImage generateImage() {
		int height = image.getHeight();
		int width = image.getWidth();
		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				Color color = computePixelColor(row, column);
				image.setColor(row, column, color);
			}
		}
		return image;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * fr.univartois.butinfo.fractalens.generator.IFractalGenerator#computePixelColor(int,
	 * int)
	 */
	@Override
	public Color computePixelColor(int row, int column) {
		IComplex z0 = plane.asComplex(row, column);
		IComplexSequence suite = sequenceBiFunction.apply(z0, nbMaxIteration);
		int counter = 0;
		for (var _ : suite) {
			counter++;
		}
		return palette.getCouleur(counter, nbMaxIteration);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see fr.univartois.butinfo.fractalens.generator.IFractalGenerator#saveImage()
	 */
	@Override
	public void saveImage() {
		try {
			image.saveAs(filePath);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

}
