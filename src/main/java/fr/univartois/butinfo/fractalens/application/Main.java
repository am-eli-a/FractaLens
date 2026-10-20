package fr.univartois.butinfo.fractalens.application;

import java.awt.image.BufferedImage;
import java.util.function.BiFunction;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.ComplexPlane;
import fr.univartois.butinfo.fractalens.complex.IComplex;
import fr.univartois.butinfo.fractalens.complex.IComplexPlane;
import fr.univartois.butinfo.fractalens.complex.ZoomDecorator;
import fr.univartois.butinfo.fractalens.complex.sequence.IComplexSequence;
import fr.univartois.butinfo.fractalens.complex.sequence.JuliaSequence;
import fr.univartois.butinfo.fractalens.generator.FractalGenerator;
import fr.univartois.butinfo.fractalens.generator.IFractalGenerator;
import fr.univartois.butinfo.fractalens.image.IFractalImage;

/**
 * Main class generating a Julia fractal image in grayscale.
 *
 * @author Nathan Salomé
 *
 * @version 0.1.0
 */
public class Main {
  /** The height of the image (in pixels). */
  private static final int HEIGHT = 600;
  /** The width of the image (in pixels). */
  private static final int WIDTH = 800;
  /** The maximum number of iterations. */
  private static final int MAX_ITERATIONS = 100;
  /** The zoom factor applied on the complex plane. */
  private static final double ZOOM = 0.004;
  /** The real part of the constant c of the Julia sequence. */
  private static final double C_REAL = -0.4;
  /** The imaginary part of the constant c of the Julia sequence. */
  private static final double C_IMAGINARY = 0.6;
  /** The default name of the output file. */
  private static final String DEFAULT_OUTPUT = "fractal.png";

  /**
   * Generates the fractal image and saves it.
   *
   * @param args The name of the output file (optional).
   */
  public static void main(String[] args) {
    String outputFile;
    if (args.length > 0) {
      outputFile = args[0];
    } else {
      outputFile = DEFAULT_OUTPUT;
    }

    IComplexPlane plane = new ZoomDecorator(ZOOM, new ComplexPlane(HEIGHT, WIDTH));

    IComplex c = new Complex(C_REAL, C_IMAGINARY);
    BiFunction<IComplex, Integer, IComplexSequence> sequence =
            (z0, nbTerms) -> new JuliaSequence(z0, c, nbTerms);

    IPalette palette = new GrayScalePalette(); // À ADAPTER

    IFractalImage image = new BufferedImageAdapter( // À ADAPTER
            new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB));

    IFractalGenerator generator =
            new FractalGenerator(image, plane, MAX_ITERATIONS, palette, outputFile, sequence);

    generator.generateImage();
    generator.saveImage();
  }

}