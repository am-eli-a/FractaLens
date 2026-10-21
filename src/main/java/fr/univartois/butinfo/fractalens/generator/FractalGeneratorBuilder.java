package fr.univartois.butinfo.fractalens.generator;

import java.awt.image.BufferedImage;
import java.util.function.BiFunction;
import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.IComplexPlane;
import fr.univartois.butinfo.fractalens.complex.ZoomDecorator;
import fr.univartois.butinfo.fractalens.complex.IComplex;
import fr.univartois.butinfo.fractalens.complex.ComplexPlane;
import fr.univartois.butinfo.fractalens.complex.TranslationDecorator;
import fr.univartois.butinfo.fractalens.complex.sequence.ComplexSequences;
import fr.univartois.butinfo.fractalens.complex.sequence.IComplexSequence;
import fr.univartois.butinfo.fractalens.image.BufferedImageAdapter;
import fr.univartois.butinfo.fractalens.image.IFractalImage;
import fr.univartois.butinfo.fractalens.image.palette.GrayScale;
import fr.univartois.butinfo.fractalens.image.palette.IPalette;

public class FractalGeneratorBuilder {

  private BiFunction<IComplex, Integer, IComplexSequence> sequenceFactory;
  private int width;
  private int height;
  private double zoom;
  private IComplex center;
  private int maxIterations;
  private IPalette palette;
  private String outputFile;
  private static final int DEFAULT_WIDTH = 800;
  private static final int DEFAULT_HEIGHT = 400;
  private static final double DEFAULT_ZOOM = 0.004;
  private static final String DEFAULT_OUTPUT_FILE = "fractal.png";
  private static final int DEFAULT_MAX_ITERATIONS = 64;
  private static final IComplex DEFAULT_CENTER = new Complex(0, 0);
  private static final IPalette DEFAULT_PALETTE = new GrayScale();
  private static final BiFunction<IComplex, Integer, IComplexSequence> DEFAULT_SEQUENCE = (z0, nbTerms) -> ComplexSequences.mandelbrot(z0, nbTerms);


  public FractalGeneratorBuilder() {
    this.width = DEFAULT_WIDTH;
    this.height = DEFAULT_HEIGHT;
    this.zoom = DEFAULT_ZOOM;
    this.maxIterations = DEFAULT_MAX_ITERATIONS;
    this.outputFile = DEFAULT_OUTPUT_FILE;
    this.center = DEFAULT_CENTER;
    this.palette = DEFAULT_PALETTE;
    this.sequenceFactory = DEFAULT_SEQUENCE;
  }

  public FractalGeneratorBuilder usingSequence(BiFunction<IComplex, Integer, IComplexSequence> sequenceFactory) {
    this.sequenceFactory = sequenceFactory;
    return this;
  }

  public FractalGeneratorBuilder withSize(int width, int height) {
    this.width = width;
    this.height = height;
    return this;
  }

  public FractalGeneratorBuilder zoomedBy(double zoom) {
    this.zoom = zoom;
    return this;
  }

  public FractalGeneratorBuilder centeredAt(IComplex center) {
    this.center = center;
    return this;
  }

  public FractalGeneratorBuilder withMaxIterations(int maxIterations) {
    this.maxIterations = maxIterations;
    return this;
  }

  public FractalGeneratorBuilder coloredWith(IPalette palette) {
    this.palette = palette;
    return this;
  }

  public FractalGeneratorBuilder savedAs(String outputFile) {
    this.outputFile = outputFile;
    return this;
  }

  public IFractalImage getImage() {
    return new BufferedImageAdapter(new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB));
  }

  public IComplexPlane getPlane() {
    return new TranslationDecorator(center, new ZoomDecorator(zoom ,new ComplexPlane(height, width)));
  }

  public int getMaxIterations() {
    return maxIterations;
  }

  public IPalette getPalette() {
    return palette;
  }

  public String getOutputFile() {
    return outputFile;
  }

  public BiFunction<IComplex, Integer, IComplexSequence> getSequenceFactory() {
    return sequenceFactory;
  }

  public FractalGenerator build() {
    // TODO return new FractalGenerator(this); quand le constructeur de Racer existera
    return null;
  }

}