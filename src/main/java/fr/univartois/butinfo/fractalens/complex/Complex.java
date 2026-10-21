package fr.univartois.butinfo.fractalens.complex;

import java.util.Objects;

/**
 * Immutable implementation of {@link IComplex}.
 *
 * @author Nathan Salomé
 *
 * @version 0.1.0
 */
public class Complex implements IComplex {

  /** The real part. */
  private final double realPart;

  /** The imaginary part. */
  private final double imaginaryPart;

  /**
   * Creates a new complex number.
   *
   * @param realPart The real part.
   * @param imaginaryPart The imaginary part.
   */
  public Complex(double realPart, double imaginaryPart) {
    this.realPart = realPart;
    this.imaginaryPart = imaginaryPart;
  }

  /**
   * @return The real part.
   */
  @Override
  public double getRealPart() {
    return realPart;
  }

  /**
   * @return The imaginary part.
   */
  @Override
  public double getImaginaryPart() {
    return imaginaryPart;
  }

  /**
   * @return The modulus of this complex number.
   */
  @Override
  public double abs() {
    return Math.sqrt(Math.pow(getRealPart(), 2) + Math.pow(getImaginaryPart(), 2));
  }

  /**
   * @return The opposite of this complex number.
   */
  @Override
  public IComplex negate() {
    return new Complex(realPart * -1, imaginaryPart * -1);
  }

  /**
   * @return The conjugate of this complex number.
   */
  @Override
  public IComplex conjugate() {
    return new Complex(getRealPart(), -getImaginaryPart());
  }

  /**
   * @param other The complex number to add.
   * @return The result of {@code this + other}.
   */
  @Override
  public IComplex add(IComplex other) {
    return new Complex(realPart + other.getRealPart(), imaginaryPart + other.getImaginaryPart());
  }

  /**
   * @param other The complex number to subtract.
   * @return The result of {@code this - other}.
   */
  @Override
  public IComplex subtract(IComplex other) {
    return add(other.negate());
  }

  /**
   * @param value The real value to multiply by.
   * @return The result of {@code this * value}.
   */
  @Override
  public IComplex multiply(double value) {
    return new Complex(realPart * value, imaginaryPart * value);
  }

  /**
   * @param other The complex number to multiply by.
   * @return The result of {@code this * other}.
   */
  @Override
  public IComplex multiply(IComplex other) {
    return new Complex(realPart * other.getRealPart() - imaginaryPart * other.getImaginaryPart(), realPart * other.getImaginaryPart() + other.getRealPart() * imaginaryPart);
  }

  /**
   * @param other The complex number to divide by.
   * @return The result of {@code this / other}.
   */
  @Override
  public IComplex divide(IComplex other) {
    return new Complex((realPart * other.getRealPart() + imaginaryPart * other.getImaginaryPart()) / (other.getRealPart() * other.getRealPart() + other.getImaginaryPart() * other.getImaginaryPart()), (other.getRealPart() * imaginaryPart - realPart * other.getImaginaryPart()) / (other.getRealPart() * other.getRealPart() + other.getImaginaryPart() * other.getImaginaryPart()));
  }

  /**
   * @return This complex number as {@code "a + bi"}.
   */
  @Override
  public String toString() {
    return realPart + " + " + imaginaryPart + "i";
  }

  /**
   * @param obj The object to compare with.
   * @return Whether {@code obj} is an equal complex number.
   */
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

  /**
   * @return The hash code of this complex number.
   */
  @Override
  public int hashCode() {
    return Objects.hash(realPart + 0.0, imaginaryPart + 0.0);
  }


  @Override
  public IComplex power(int p){
    IComplex result = new Complex(1,0);
    for (int i = 0; i < p; i++) {
      result = result.multiply(this); // Complex Z
    }
    return result;
  }
}