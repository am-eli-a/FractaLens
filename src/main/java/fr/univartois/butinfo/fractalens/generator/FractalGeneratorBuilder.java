package fr.univartois.butinfo.fractalens.generator;

import java.util.function.BiFunction;

import fr.univartois.butinfo.fractalens.complex.IComplex;
import fr.univartois.butinfo.fractalens.complex.IComplexPlane;
import fr.univartois.butinfo.fractalens.complex.sequence.IComplexSequence;
import fr.univartois.butinfo.fractalens.image.IFractalImage;
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

  public FractalGeneratorBuilder() {

  }

  public FractalGeneratorBuilder usingSequence(BiFunction<IComplex, Integer, IComplexSequence> sequenceFactory) {
    // TODO
    return this;
  }

  public FractalGeneratorBuilder withSize(int width, int height) {
    // TODO
    return this;
  }

  public FractalGeneratorBuilder zoomedBy(double zoom) {
    // TODO
    return this;
  }

  public FractalGeneratorBuilder centeredAt(IComplex center) {
    // TODO
    return this;
  }

  public FractalGeneratorBuilder withMaxIterations(int maxIterations) {
    // TODO
    return this;
  }

  public FractalGeneratorBuilder coloredWith(IPalette palette) {
    // TODO
    return this;
  }

  public FractalGeneratorBuilder savedAs(String outputFile) {
    // TODO
    return this;
  }

  public IFractalImage getImage() {
    // TODO construire l'image à partir de width et height
    return null;
  }

  public IComplexPlane getPlane() {
    // TODO construire le plan (zoom puis translation)
    return null;
  }

  public int getMaxIterations() {
    // TODO
    return 0;
  }

  public IPalette getPalette() {
    // TODO
    return null;
  }

  public String getOutputFile() {
    // TODO
    return null;
  }

  public BiFunction<IComplex, Integer, IComplexSequence> getSequenceFactory() {
    // TODO
    return null;
  }

  public FractalGenerator build() {
    // TODO return new FractalGenerator(this); quand le constructeur de Racer existera
    return null;
  }

}