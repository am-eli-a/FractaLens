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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Color;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;

import fr.univartois.butinfo.fractalens.image.palette.IPalette;
import org.junit.jupiter.api.Test;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.IComplex;
import fr.univartois.butinfo.fractalens.complex.IComplexPlane;
import fr.univartois.butinfo.fractalens.complex.sequence.IComplexSequence;
import fr.univartois.butinfo.fractalens.image.IFractalImage;

/**
 * The FractalGeneratorTest tests the {@link FractalGenerator} class.
 * The generator is tested with fake implementations of the image, the sequence and
 * the palette, so that these tests do not depend on the other classes of the project.
 *
 * @author Matty Fourmestraux
 *
 * @version 0.1.0
 */
class FractalGeneratorTest {

	/**
	 * The maximum number of iterations used in the tests.
	 */
	private static final int MAX_ITERATIONS = 10;

	/**
	 * A palette that encodes its parameters in the returned color, so that the tests can
	 * check which values the generator gave to it.
	 */
	private static final IPalette PALETTE = (nbIterations, max) -> new Color(nbIterations, max, 0);

	/**
	 * A complex plane associating the complex number {@code column + i * row} with the
	 * pixel at {@code (row, column)}.
	 */
	private static final IComplexPlane PLANE = (row, column) -> new Complex(column, row);

	/**
	 * Test method for {@link FractalGenerator#computePixelColor(int, int)} when the
	 * sequence diverges before the maximum number of iterations.
	 */
	@Test
	void testComputePixelColorWhenSequenceDiverges() {
		FractalGenerator generator = createGenerator(new FakeImage(1, 1),
				(z0, max) -> new FakeSequence(3));

		assertEquals(new Color(3, MAX_ITERATIONS, 0), generator.computePixelColor(0, 0));
	}

	/**
	 * Test method for {@link FractalGenerator#computePixelColor(int, int)} when the
	 * sequence does not diverge.
	 */
	@Test
	void testComputePixelColorWhenSequenceConverges() {
		FractalGenerator generator = createGenerator(new FakeImage(1, 1),
				(z0, max) -> new FakeSequence(max));

		assertEquals(new Color(MAX_ITERATIONS, MAX_ITERATIONS, 0),
				generator.computePixelColor(0, 0));
	}

	/**
	 * Test method for {@link FractalGenerator#computePixelColor(int, int)}, checking that
	 * the sequence is created from the complex number given by the plane and from the
	 * maximum number of iterations.
	 */
	@Test
	void testComputePixelColorUsesPlaneAndMaxIterations() {
		List<IComplex> firstTerms = new ArrayList<>();
		List<Integer> maxValues = new ArrayList<>();
		FractalGenerator generator = createGenerator(new FakeImage(10, 10), (z0, max) -> {
			firstTerms.add(z0);
			maxValues.add(max);
			return new FakeSequence(0);
		});

		generator.computePixelColor(2, 5);

		assertEquals(List.of(new Complex(5, 2)), firstTerms);
		assertEquals(List.of(MAX_ITERATIONS), maxValues);
	}

	/**
	 * Test method for {@link FractalGenerator#generateImage()}.
	 */
	@Test
	void testGenerateImage() {
		FakeImage image = new FakeImage(2, 3);
		FractalGenerator generator = createGenerator(image,
				(z0, max) -> new FakeSequence((int) (z0.getRealPart() + z0.getImaginaryPart())));

		IFractalImage result = generator.generateImage();

		assertSame(image, result);
		for (int row = 0; row < image.getHeight(); row++) {
			for (int column = 0; column < image.getWidth(); column++) {
				assertEquals(new Color(row + column, MAX_ITERATIONS, 0),
						image.getColor(row, column));
			}
		}
	}

	/**
	 * Test method for {@link FractalGenerator#saveImage()}.
	 */
	@Test
	void testSaveImage() {
		FakeImage image = new FakeImage(1, 1);
		FractalGenerator generator = createGenerator(image, (z0, max) -> new FakeSequence(0));

		generator.saveImage();

		assertEquals("fractale.png", image.getSavedPath());
	}

	/**
	 * Test method for {@link FractalGenerator#saveImage()} when the image cannot be
	 * saved.
	 */
	@Test
	void testSaveImageWhenAnErrorOccurs() {
		FakeImage image = new FakeImage(1, 1);
		image.failOnSave();
		FractalGenerator generator = createGenerator(image, (z0, max) -> new FakeSequence(0));

		assertThrows(UncheckedIOException.class, generator::saveImage);
	}

	/**
	 * Creates a generator using the plane and the palette of these tests.
	 *
	 * @param image The image on which to draw the fractal.
	 * @param sequenceFactory The function creating the sequences to evaluate.
	 *
	 * @return The created generator.
	 */
	private static FractalGenerator createGenerator(IFractalImage image,
													BiFunction<IComplex, Integer, IComplexSequence> sequenceFactory) {
		return new FractalGenerator(image, PLANE, MAX_ITERATIONS, PALETTE, "fractale.png",
				sequenceFactory);
	}

	/**
	 * A fake sequence that always gives a fixed number of terms.
	 */
	private static class FakeSequence implements IComplexSequence {

		/**
		 * The number of terms given by this sequence.
		 */
		private final int nbTerms;

		/**
		 * Creates a new FakeSequence.
		 *
		 * @param nbTerms The number of terms given by this sequence.
		 */
		FakeSequence(int nbTerms) {
			this.nbTerms = nbTerms;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see fr.univartois.butinfo.fractalens.complex.sequence.IComplexSequence#
		 * computeNext(fr.univartois.butinfo.fractalens.complex.IComplex)
		 */
		@Override
		public IComplex computeNext(IComplex previous) {
			return previous;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see java.lang.Iterable#iterator()
		 */
		@Override
		public Iterator<IComplex> iterator() {
			return Collections.<IComplex> nCopies(nbTerms, new Complex(0, 0)).iterator();
		}

	}

	/**
	 * A fake image that stores the colors of its pixels in an array.
	 */
	private static class FakeImage implements IFractalImage {

		/**
		 * The colors of the pixels of this image.
		 */
		private final Color[][] colors;

		/**
		 * The path in which this image has been saved.
		 */
		private String savedPath;

		/**
		 * Whether saving this image must fail.
		 */
		private boolean failing;

		/**
		 * Creates a new FakeImage.
		 *
		 * @param height The height of the image.
		 * @param width The width of the image.
		 */
		FakeImage(int height, int width) {
			this.colors = new Color[height][width];
		}

		/**
		 * Makes the next saves of this image fail.
		 */
		void failOnSave() {
			this.failing = true;
		}

		/**
		 * Gives the color of a pixel of this image.
		 *
		 * @param row The row of the pixel.
		 * @param column The column of the pixel.
		 *
		 * @return The color of the pixel.
		 */
		Color getColor(int row, int column) {
			return colors[row][column];
		}

		/**
		 * Gives the path in which this image has been saved.
		 *
		 * @return The path, or {@code null} if the image has not been saved.
		 */
		String getSavedPath() {
			return savedPath;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see fr.univartois.butinfo.fractalens.image.IFractalImage#getHeight()
		 */
		@Override
		public int getHeight() {
			return colors.length;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see fr.univartois.butinfo.fractalens.image.IFractalImage#getWidth()
		 */
		@Override
		public int getWidth() {
			return colors[0].length;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see fr.univartois.butinfo.fractalens.image.IFractalImage#setColor(int, int,
		 * java.awt.Color)
		 */
		@Override
		public void setColor(int row, int column, Color color) {
			colors[row][column] = color;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see fr.univartois.butinfo.fractalens.image.IFractalImage#saveAs(java.lang.String)
		 */
		@Override
		public void saveAs(String path) throws IOException {
			if (failing) {
				throw new IOException("Cannot save the image");
			}
			savedPath = path;
		}

	}

}
