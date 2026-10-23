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

package fr.univartois.butinfo.fractalens.application;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.IComplex;
import fr.univartois.butinfo.fractalens.complex.sequence.ComplexSequenceType;
import fr.univartois.butinfo.fractalens.complex.sequence.ComplexSequences;
import fr.univartois.butinfo.fractalens.generator.CompositeFractalGenerator;
import fr.univartois.butinfo.fractalens.generator.FractalGeneratorBuilder;
import fr.univartois.butinfo.fractalens.generator.IFractalGenerator;
import fr.univartois.butinfo.fractalens.image.palette.FixedPalette;
import fr.univartois.butinfo.fractalens.image.palette.IPalette;

/**
 * The ContestAnimations class generates the animated GIF images proposed for the
 * image contest of the SAÉ:
 * a zoom in a valley of the Multibrot set of power 4, and a rotation of the
 * constant of the generalized Burning Ship sequence of power 3.
 *
 * @author Matty Fourmestraux
 *
 * @version 0.1.0
 */
public final class ContestAnimations {

	/**
	 * The size of the images of the zoom animation (in pixels).
	 */
	private static final int ZOOM_SIZE = 400;

	/**
	 * The size of the images of the rotation animation (in pixels).
	 */
	private static final int ROTATION_SIZE = 300;

	/**
	 * The number of images of the rotation animation.
	 */
	private static final int ROTATION_FRAMES = 72;

	/**
	 * Disables instantiation.
	 */
	private ContestAnimations() {
		throw new AssertionError("No ContestAnimations instance for you!");
	}

	/**
	 * Generates the animations of the contest.
	 *
	 * @param args The command line arguments (not used).
	 */
	public static void main(String[] args) {
		infiniteZoom("contest-infinite.gif");
		generalizedBurningShipRotation("contest-burning-ship-3.gif");
	}
	/**
	 * Generates a rotation of the constant of the generalized Burning Ship sequence,
	 * with a power of 3 and |c| = 0.6.
	 *
	 * @param output The name of the output file.
	 */
	private static void generalizedBurningShipRotation(String output) {
		List<IFractalGenerator> generators = new ArrayList<>();
		for (int k = 0; k < ROTATION_FRAMES; k++) {
			IComplex c = rotatedConstant(0.6, k);
			generators.add(new FractalGeneratorBuilder()
					.usingSequence((z0, max) -> ComplexSequences.generalizedBurningShip(z0, max, c, 3))
					.withSize(ROTATION_SIZE, ROTATION_SIZE)
					.zoomedBy(0.0075)
					.centeredAt(new Complex(0, 0))
					.withMaxIterations(20)
					.coloredWith(duskPalette())
					.build());
		}
		IFractalGenerator animation = new CompositeFractalGenerator(generators, 60, output);
		animation.generateImage();
		animation.saveImage();
	}

	/**
	 * Computes the constant used for an image of the rotation animation.
	 *
	 * @param modulus The modulus of the constant.
	 * @param frame The index of the image in the animation.
	 *
	 * @return The constant of the image, rotated by {@code 2π × frame / ROTATION_FRAMES}.
	 */
	private static IComplex rotatedConstant(double modulus, int frame) {
		double theta = 2 * Math.PI * frame / ROTATION_FRAMES;
		return new Complex(modulus * Math.cos(theta), modulus * Math.sin(theta));
	}

	/**
	 * Creates the palette used for the rotation animation.
	 *
	 * @return A palette going from dark blue to light yellow, with a purple interior.
	 */
	private static IPalette duskPalette() {
		return new FixedPalette(List.of(
				new Color(2, 4, 20), new Color(30, 20, 90), new Color(120, 40, 160),
				new Color(240, 90, 140), new Color(255, 200, 120), new Color(255, 255, 230),
				new Color(40, 10, 60)));
	}
	/**
	 * Generates an infinite zoom on the Feigenbaum point of the Mandelbrot set.
	 * Around this point, the Mandelbrot set is self-similar: zooming by the
	 * Feigenbaum constant gives the same image, provided that the maximum number of
	 * iterations is doubled. The last image of the animation thus joins the first
	 * one, so that the animation loops seamlessly.
	 *
	 * @param output The name of the output file.
	 */
	private static void infiniteZoom(String output) {
		IPalette aurora = new FixedPalette(List.of(
				new Color(3, 5, 25), new Color(20, 40, 110), new Color(30, 150, 180),
				new Color(120, 230, 160), new Color(250, 250, 200), new Color(250, 160, 60),
				new Color(180, 40, 80), Color.BLACK));
		double feigenbaumConstant = 4.669201609;
		int frames = 60;

		List<IFractalGenerator> generators = new ArrayList<>();
		for (int k = 0; k < frames; k++) {
			double progress = (double) k / frames;
			generators.add(new FractalGeneratorBuilder()
					.usingSequence(ComplexSequenceType.MANDELBROT)
					.withSize(300, 300)
					.zoomedBy(0.0000571 / Math.pow(feigenbaumConstant, progress))
					.centeredAt(new Complex(-1.401155189092051, 0))
					.withMaxIterations((int) Math.round(150 * Math.pow(2, progress)))
					.coloredWith(aurora)
					.build());
		}
		IFractalGenerator animation = new CompositeFractalGenerator(generators, 60, output);
		animation.generateImage();
		animation.saveImage();
	}

}