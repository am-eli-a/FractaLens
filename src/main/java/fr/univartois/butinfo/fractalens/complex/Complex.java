package fr.univartois.butinfo.fractalens.complex;

public class Complex implements IComplex {
  private double RealPart;
  private double ImaginaryPart;

  public Complex(double ImaginaryPart, double nbImaginary){
    this.RealPart = RealPart;
    this.ImaginaryPart = ImaginaryPart;
  }

  @Override
  public double getRealPart() {
    return RealPart;
  }

  @Override
  public double getImaginaryPart() {
    return ImaginaryPart;
  }

  @Override
  public double abs() {
    return 0;
  }

  @Override
  public IComplex negate() {
    return null;
  }

  @Override
  public IComplex conjugate() {
    return null;
  }

  @Override
  public IComplex add(IComplex other) {
    return null;
  }

  @Override
  public IComplex subtract(IComplex other) {
    return null;
  }

  @Override
  public IComplex multiply(double value) {
    return null;
  }

  @Override
  public IComplex multiply(IComplex other) {
    return null;
  }

  @Override
  public IComplex divide(IComplex other) {
    return null;
  }
}
