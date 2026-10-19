package fr.univartois.butinfo.fractalens.complex;
import java.util.Objects;

public class Complex implements IComplex {
  private final double realPart;
  private final double imaginaryPart;

  public Complex(double realPart, double imaginaryPart){
    this.realPart = realPart;
    this.imaginaryPart = imaginaryPart;
  }

  @Override
  public double getRealPart() {
    return realPart;
  }

  @Override
  public double getImaginaryPart() {
    return imaginaryPart;
  }

  @Override
  public double abs() {
    return Math.sqrt(Math.pow(getRealPart(), 2) + Math.pow(getImaginaryPart(), 2));
  }

  @Override
  public IComplex negate() {
    return new Complex(realPart * -1, imaginaryPart * -1);
  }

  @Override
  public IComplex conjugate() {
    return new Complex(getRealPart(), -getImaginaryPart());
  }

  @Override
  public IComplex add(IComplex other) {
    return new Complex(realPart + other.getRealPart(), imaginaryPart + other.getImaginaryPart());
  }

  @Override
  public IComplex subtract(IComplex other) {
    return add(other.negate());
  }

  @Override
  public IComplex multiply(double value) {
    return new Complex(realPart * value, imaginaryPart * value);
  }

  @Override
  public IComplex multiply(IComplex other) {
    return new Complex(realPart * other.getRealPart() - imaginaryPart * other.getImaginaryPart(), realPart * other.getImaginaryPart() + other.getRealPart() * imaginaryPart);
  }

  @Override
  public IComplex divide(IComplex other) {
    return new Complex((realPart * other.getRealPart() + imaginaryPart * other.getImaginaryPart()) / (other.getRealPart() * other.getRealPart() + other.getImaginaryPart() * other.getImaginaryPart()), (other.getRealPart() * imaginaryPart - realPart * other.getImaginaryPart()) / (other.getRealPart() * other.getRealPart() + other.getImaginaryPart() * other.getImaginaryPart()));
  }

  @Override
  public String toString() {
    return realPart + " + " + imaginaryPart + "i";
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Complex other = (Complex) obj;
    return realPart == other.realPart && imaginaryPart == other.imaginaryPart;
  }

  @Override
  public int hashCode() {
    return Objects.hash(realPart + 0.0, imaginaryPart + 0.0);
  }

}
