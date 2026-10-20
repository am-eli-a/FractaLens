package fr.univartois.butinfo.fractalens.application;

import fr.univartois.butinfo.fractalens.complex.*;
import fr.univartois.butinfo.fractalens.complex.sequence.IComplexSequence;
import fr.univartois.butinfo.fractalens.complex.sequence.JuliaSequence;
import fr.univartois.butinfo.fractalens.generator.FractalGenerator;
import fr.univartois.butinfo.fractalens.generator.IFractalGenerator;
import fr.univartois.butinfo.fractalens.image.BufferedImageAdapter;
import fr.univartois.butinfo.fractalens.image.IFractalImage;
import fr.univartois.butinfo.fractalens.image.palette.*;

import java.awt.Color;
import java.util.List;
import java.awt.image.BufferedImage;
import java.util.function.BiFunction;

/**
 * The Main class generates the demonstration images of the Julia fractal.
 * Two images are generated, each one with its own constant for the Julia sequence,
 * its own center and its own palette, while sharing the same size, zoom and maximum
 * number of iterations.
 *
 * @author Nathan Salomé
 * @author Matty Fourmestraux
 *
 * @version 0.2.0
 */
public class Main {
  /**
   * The height of the image (in pixels).
   */
  private static final int HEIGHT = 800;
  /**
   * The width of the image (in pixels).
   */
  private static final int WIDTH = 800;
  /**
   * The maximum number of iterations.
   */
  private static final int MAX_ITERATIONS = 250;
  /**
   * The zoom factor applied on the complex plane.
   */
  private static final double ZOOM = 0.001;


  /**
   * Generates an image of a Julia fractal and saves it in a file.
   *
   * @param constant   The constant c of the Julia sequence.
   * @param center     The complex number at the center of the image.
   * @param palette    The palette used to color the image.
   * @param outputFile The name of the file in which to save the image.
   */
  private static void generateFractal(IComplex constant, IComplex center,
                                      IPalette palette, String outputFile) {
    IComplexPlane planeZoom = new ZoomDecorator(ZOOM, new ComplexPlane(HEIGHT, WIDTH));
    IComplexPlane planeTranslate = new TranslationDecorator(center, planeZoom);

    BiFunction<IComplex, Integer, IComplexSequence> sequence =
            (z0, nbTerms) -> new JuliaSequence(z0, nbTerms, constant);

    IFractalImage image = new BufferedImageAdapter(
            new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB));

    IFractalGenerator generator = new FractalGenerator(image, planeTranslate,
            MAX_ITERATIONS, palette, outputFile, sequence);

    generator.generateImage();
    generator.saveImage();
  }

  /**
   * Generates the two demonstration images and saves them.
   *
   * @param args The names of the two output files (optional). If they are not given,
   *        the images are saved in {@code fractal1.png} and {@code fractal2.png}.
   */
  public static void main(String[] args) {
    String firstOutput = (args.length > 0) ? args[0] : "fractal1.png";
    String secondOutput = (args.length > 1) ? args[1] : "fractal2.png";

    // Image 1 : c = -0.4 + 0.6i, centrée en 0.1 + 0.6i, palette fixe
    IPalette fixedPalette = new FixedPalette(List.of(
            new Color(255, 255, 220), Color.YELLOW, Color.ORANGE, Color.RED, Color.BLACK));
    generateFractal(new Complex(-0.4, 0.6), new Complex(0.1, 0.6),
            fixedPalette, firstOutput);

    // Image 2 : c = 0.285 + 0.01i, centrée en -0.47 - 0.21i, cyan inversé
    IPalette invertedCyan = new InversionDecorator(
            new MaskDecorator(new GrayScale(), false, true, true));
    generateFractal(new Complex(0.285, 0.01), new Complex(-0.47, -0.21),
            invertedCyan, secondOutput);
  }
}