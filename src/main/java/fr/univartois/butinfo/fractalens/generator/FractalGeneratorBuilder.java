package fr.univartois.butinfo.fractalens.generator;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.ComplexPlane;
import fr.univartois.butinfo.fractalens.complex.IComplex;
import fr.univartois.butinfo.fractalens.complex.IComplexPlane;
import fr.univartois.butinfo.fractalens.complex.TranslationDecorator;
import fr.univartois.butinfo.fractalens.complex.ZoomDecorator;
import fr.univartois.butinfo.fractalens.complex.sequence.ComplexSequenceType;
import fr.univartois.butinfo.fractalens.complex.sequence.ComplexSequences;
import fr.univartois.butinfo.fractalens.complex.sequence.IComplexSequence;
import fr.univartois.butinfo.fractalens.image.BufferedImageAdapter;
import fr.univartois.butinfo.fractalens.image.IFractalImage;
import fr.univartois.butinfo.fractalens.image.palette.GrayScale;
import fr.univartois.butinfo.fractalens.image.palette.IPalette;
import fr.univartois.butinfo.fractalens.image.palette.PaletteType;

/**
 * The FractalGeneratorBuilder class is a builder allowing to configure a
 * {@link FractalGenerator} step by step, by chaining calls that read like a
 * sentence describing the fractal to generate.
 *
 * The builder only stores the raw settings (size, zoom, center...). The objects
 * depending on several settings, such as the image and the complex plane, are
 * created when they are requested, so that they are always consistent with the
 * last values that have been set.
 *
 * Every setting has a default value, used when it is not configured.
 *
 * @author Baptiste Wagee
 *
 * @version 0.1.0
 */
public class FractalGeneratorBuilder {

  /**
   * The default width of the image (in pixels).
   */
  private static final int DEFAULT_WIDTH = 800;

  /**
   * The default height of the image (in pixels).
   */
  private static final int DEFAULT_HEIGHT = 400;

  /**
   * The default zoom factor applied on the complex plane.
   */
  private static final double DEFAULT_ZOOM = 0.004;

  /**
   * The default name of the file in which the image is saved.
   */
  private static final String DEFAULT_OUTPUT_FILE = "fractal.png";

  /**
   * The default maximum number of iterations computed for each pixel.
   */
  private static final int DEFAULT_MAX_ITERATIONS = 64;

  /**
   * The default complex number at the center of the image.
   */
  private static final IComplex DEFAULT_CENTER = new Complex(0, 0);

  /**
   * The default palette used to color the image.
   */
  private static final IPalette DEFAULT_PALETTE = new GrayScale();

  private static final int DEFAULT_FRAME_COUNT = 1;

  private static final double DEFAULT_ZOOM_FACTOR = 1.0;

  private static final int DEFAULT_FRAME_DELAY = 100;

  /**
   * The default function creating the sequence to evaluate (Mandelbrot's sequence).
   */
  private static final BiFunction<IComplex, Integer, IComplexSequence> DEFAULT_SEQUENCE =
          (z0, nbTerms) -> ComplexSequences.mandelbrot(z0, nbTerms);

  /**
   * The function creating the sequence to evaluate, from its first term and its
   * maximum number of terms.
   */
  private BiFunction<IComplex, Integer, IComplexSequence> sequenceFactory;

  /**
   * The width of the image (in pixels).
   */
  private int width;

  /**
   * The height of the image (in pixels).
   */
  private int height;

  /**
   * The zoom factor applied on the complex plane.
   */
  private double zoom;

  /**
   * The complex number at the center of the image.
   */
  private IComplex center;

  /**
   * The maximum number of iterations computed for each pixel.
   */
  private int maxIterations;

  /**
   * The palette used to color the image.
   */
  private IPalette palette;

  /**
   * The name of the file in which the image is saved.
   */
  private String outputFile;

  private int frameCount;

  private double zoomFactor;

  private int frameDelay;


  /**
   * Creates a new FractalGeneratorBuilder, initialized with the default values.
   */
  public FractalGeneratorBuilder() {
    this.width = DEFAULT_WIDTH;
    this.height = DEFAULT_HEIGHT;
    this.zoom = DEFAULT_ZOOM;
    this.maxIterations = DEFAULT_MAX_ITERATIONS;
    this.outputFile = DEFAULT_OUTPUT_FILE;
    this.center = DEFAULT_CENTER;
    this.palette = DEFAULT_PALETTE;
    this.sequenceFactory = DEFAULT_SEQUENCE;
    this.frameDelay = DEFAULT_FRAME_DELAY;
    this.zoomFactor = DEFAULT_ZOOM_FACTOR;
    this.frameCount = DEFAULT_FRAME_COUNT;
  }

  public FractalGeneratorBuilder animatedWithZoom(double factor, int frames){
    this.zoomFactor = factor;
    this.frameCount = frames;
    return this;
  }

  public FractalGeneratorBuilder withFrameDelay(int delay){
    this.frameDelay=delay;
    return this;
  }
  /**
   * Sets the sequence to evaluate, given as a function.
   *
   * @param sequenceFactory The function creating the sequence, from its first term
   *        and its maximum number of terms.
   *
   * @return This builder.
   */
  public FractalGeneratorBuilder usingSequence(BiFunction<IComplex, Integer, IComplexSequence> sequenceFactory) {
    this.sequenceFactory = sequenceFactory;
    return this;
  }

  /**
   * Sets the sequence to evaluate, given as a constant of the enumeration.
   *
   * @param sequenceType The type of the sequence to evaluate.
   *
   * @return This builder.
   */
  public FractalGeneratorBuilder usingSequence(ComplexSequenceType sequenceType) {
    this.sequenceFactory = sequenceType.getSequenceFactory();
    return this;
  }

  /**
   * Sets the size of the image. It is used both for the image and for the
   * complex plane.
   *
   * @param width The width of the image (in pixels).
   * @param height The height of the image (in pixels).
   *
   * @return This builder.
   */
  public FractalGeneratorBuilder withSize(int width, int height) {
    this.width = width;
    this.height = height;
    return this;
  }

  /**
   * Sets the zoom factor applied on the complex plane.
   *
   * @param zoom The zoom factor.
   *
   * @return This builder.
   */
  public FractalGeneratorBuilder zoomedBy(double zoom) {
    this.zoom = zoom;
    return this;
  }

  /**
   * Sets the complex number at the center of the image.
   *
   * @param center The center of the image.
   *
   * @return This builder.
   */
  public FractalGeneratorBuilder centeredAt(IComplex center) {
    this.center = center;
    return this;
  }

  /**
   * Sets the maximum number of iterations computed for each pixel.
   *
   * @param maxIterations The maximum number of iterations.
   *
   * @return This builder.
   */
  public FractalGeneratorBuilder withMaxIterations(int maxIterations) {
    this.maxIterations = maxIterations;
    return this;
  }

  /**
   * Sets the palette used to color the image, given as an object.
   *
   * @param palette The palette to use.
   *
   * @return This builder.
   */
  public FractalGeneratorBuilder coloredWith(IPalette palette) {
    this.palette = palette;
    return this;
  }

  /**
   * Sets the palette used to color the image, given as a constant of the
   * enumeration.
   *
   * @param paletteType The type of the palette to use.
   *
   * @return This builder.
   */
  public FractalGeneratorBuilder coloredWith(PaletteType paletteType) {
    this.palette = paletteType.getPalette();
    return this;
  }

  /**
   * Sets the name of the file in which the image is saved.
   *
   * @param outputFile The name of the output file.
   *
   * @return This builder.
   */
  public FractalGeneratorBuilder savedAs(String outputFile) {
    this.outputFile = outputFile;
    return this;
  }

  /**
   * Creates the image on which the fractal is drawn, with the current size.
   *
   * @return A new image of the configured size.
   */
  public IFractalImage getImage() {
    return new BufferedImageAdapter(new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB));
  }

  /**
   * Creates the complex plane associated with the image, using the current size,
   * zoom and center. The zoom is applied first, then the translation.
   *
   * @return A new complex plane matching the current settings.
   */
  public IComplexPlane getPlane() {
    return new TranslationDecorator(center, new ZoomDecorator(zoom, new ComplexPlane(height, width)));
  }

  /**
   * Gives the maximum number of iterations computed for each pixel.
   *
   * @return The maximum number of iterations.
   */
  public int getMaxIterations() {
    return maxIterations;
  }

  /**
   * Gives the palette used to color the image.
   *
   * @return The palette to use.
   */
  public IPalette getPalette() {
    return palette;
  }

  /**
   * Gives the name of the file in which the image is saved.
   *
   * @return The name of the output file.
   */
  public String getOutputFile() {
    return outputFile;
  }

  /**
   * Gives the function creating the sequence to evaluate.
   *
   * @return The function creating the sequence, from its first term and its
   *         maximum number of terms.
   */
  public BiFunction<IComplex, Integer, IComplexSequence> getSequenceFactory() {
    return sequenceFactory;
  }

  /**
   * Creates the fractal generator configured by this builder.
   *
   * @return The created generator.
   */
  public IFractalGenerator build() {
    if (frameCount == 1) {
      return new FractalGenerator(this);
    }

    double initialZoom = zoom;
    List<IFractalGenerator> generators = new ArrayList<>();

    for (int k = 0; k < frameCount; k++) {
      zoom = initialZoom * Math.pow(zoomFactor, k);
      generators.add(new FractalGenerator(this));
    }

    zoom = initialZoom;
    return new CompositeFractalGenerator(generators, frameDelay, outputFile);
  }