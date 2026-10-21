package fr.univartois.butinfo.fractalens.application;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.function.BiFunction;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.ComplexPlane;
import fr.univartois.butinfo.fractalens.complex.IComplex;
import fr.univartois.butinfo.fractalens.complex.IComplexPlane;
import fr.univartois.butinfo.fractalens.complex.TranslationDecorator;
import fr.univartois.butinfo.fractalens.complex.ZoomDecorator;
import fr.univartois.butinfo.fractalens.complex.sequence.ComplexSequences;
import fr.univartois.butinfo.fractalens.complex.sequence.IComplexSequence;
import fr.univartois.butinfo.fractalens.generator.FractalGenerator;
import fr.univartois.butinfo.fractalens.generator.IFractalGenerator;
import fr.univartois.butinfo.fractalens.image.BufferedImageAdapter;
import fr.univartois.butinfo.fractalens.image.IFractalImage;
import fr.univartois.butinfo.fractalens.image.palette.FixedPalette;
import fr.univartois.butinfo.fractalens.image.palette.IPalette;

/**
 * The Main class generates the demonstration images of Sprint 4: a Mandelbrot
 * fractal, a Burning Ship fractal and a fractal defined by a free formula.
 * Each image has its own size, zoom, maximum number of iterations, sequence,
 * center and palette.
 *
 * @author Nathan Salomé
 * @author Matty Fourmestraux
 * @author Baptiste Wagee
 *
 * @version 0.3.0
 */
public class Main {

  /**
   * Generates an image of a fractal and saves it in a file.
   *
   * @param width         The width of the image (in pixels).
   * @param height        The height of the image (in pixels).
   * @param zoom          The zoom factor applied on the complex plane.
   * @param maxIterations The maximum number of iterations.
   * @param sequence      The function creating the sequence to evaluate, from its
   *                      first term and its maximum number of terms.
   * @param center        The complex number at the center of the image.
   * @param palette       The palette used to color the image.
   * @param outputFile    The name of the file in which to save the image.
   */
  private static void generateFractal(int width, int height, double zoom, int maxIterations,
                                      BiFunction<IComplex, Integer, IComplexSequence> sequence,
                                      IComplex center, IPalette palette, String outputFile) {
    IComplexPlane planeZoom = new ZoomDecorator(zoom, new ComplexPlane(height, width));
    IComplexPlane planeTranslate = new TranslationDecorator(center, planeZoom);

    IFractalImage image = new BufferedImageAdapter(
            new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB));

    IFractalGenerator generator = new FractalGenerator(image, planeTranslate,
            maxIterations, palette, outputFile, sequence);

    generator.generateImage();
    generator.saveImage();
  }

  /**
   * Generates the three demonstration images and saves them.
   *
   * @param args The names of the three output files (optional). If they are not
   *        given, the images are saved in {@code fractal1.png}, {@code fractal2.png}
   *        and {@code fractal3.png}.
   */
  public static void main(String[] args) {
    String firstOutput = (args.length > 0) ? args[0] : "fractal1.png";
    String secondOutput = (args.length > 1) ? args[1] : "fractal2.png";
    String thirdOutput = (args.length > 2) ? args[2] : "fractal3.png";

    IPalette fixedPalette = new FixedPalette(List.of(
            new Color(255, 255, 220), Color.YELLOW, Color.ORANGE, Color.RED, Color.BLACK));

    // Image 1 : Mandelbrot
    generateFractal(800, 600, 0.001, 250,
            (z0, nbTerms) -> ComplexSequences.mandelbrot(z0, nbTerms),
            new Complex(-0.75, 0), fixedPalette, firstOutput);

    // Image 2 : Burning Ship
    generateFractal(800, 800, 0.0001, 64,
            (z0, nbTerms) -> ComplexSequences.burningShip(z0, nbTerms, new Complex(-0.4, 0.6)),
            new Complex(-1.755, -0.03), fixedPalette, secondOutput);

    // Image 3 : suite libre (z³ + z² + c)
    generateFractal(800, 800, 0.004, 100,
            (z0, nbTerms) -> ComplexSequences.cubicJulia(z0, nbTerms, new Complex(-0.4, 0.6)),
            new Complex(0, 0), fixedPalette, thirdOutput);
  }

}