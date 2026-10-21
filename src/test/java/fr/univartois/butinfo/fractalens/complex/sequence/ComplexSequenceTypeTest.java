package fr.univartois.butinfo.fractalens.complex.sequence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.IComplex;

/**
 * Tests for {@link ComplexSequenceType}.
 * Each constant must create a sequence computing the right next term.
 * All the sequences are created with z0 = 1 + i, and the next term is
 * computed from 1 - i (with c = -0.4 + 0.6i and p = 3 by default).
 *
 * @version 0.1.0
 */
class ComplexSequenceTypeTest {

  /** The first term of the sequences. */
  private static final IComplex Z0 = new Complex(1, 1);

  /** The previous term used to compute the next one. */
  private static final IComplex PREVIOUS = new Complex(1, -1);

  /** The maximum number of iterations. */
  private static final int MAX_ITERATIONS = 50;

  /**
   * Checks that the given type creates a sequence whose next term,
   * computed from {@link #PREVIOUS}, is the expected one.
   *
   * @param type The type of sequence to test.
   * @param expectedReal The expected real part.
   * @param expectedImaginary The expected imaginary part.
   */
  private static void assertNextTerm(ComplexSequenceType type,
                                     double expectedReal, double expectedImaginary) {
    IComplexSequence sequence = type.getSequenceFactory().apply(Z0, MAX_ITERATIONS);
    IComplex next = sequence.computeNext(PREVIOUS);
    assertEquals(expectedReal, next.getRealPart(), 0.001);
    assertEquals(expectedImaginary, next.getImaginaryPart(), 0.001);
  }

  /** Test for {@link ComplexSequenceType#JULIA}: (1 - i)^2 + c. */
  @Test
  void testJulia() {
    assertNextTerm(ComplexSequenceType.JULIA, -0.4, -1.4);
  }

  /** Test for {@link ComplexSequenceType#MANDELBROT}: (1 - i)^2 + z0. */
  @Test
  void testMandelbrot() {
    assertNextTerm(ComplexSequenceType.MANDELBROT, 1, -1);
  }

  /** Test for {@link ComplexSequenceType#BURNING_SHIP}: (1 + i)^2 + c. */
  @Test
  void testBurningShip() {
    assertNextTerm(ComplexSequenceType.BURNING_SHIP, -0.4, 2.6);
  }

  /** Test for {@link ComplexSequenceType#GENERALIZED_BURNING_SHIP}: (1 + i)^3 + c. */
  @Test
  void testGeneralizedBurningShip() {
    assertNextTerm(ComplexSequenceType.GENERALIZED_BURNING_SHIP, -2.4, 2.6);
  }

  /** Test for {@link ComplexSequenceType#TRICORN}: conj(1 - i)^2 + c. */
  @Test
  void testTricorn() {
    assertNextTerm(ComplexSequenceType.TRICORN, -0.4, 2.6);
  }

  /** Test for {@link ComplexSequenceType#GENERALIZED_TRICORN}: conj(1 - i)^3 + c. */
  @Test
  void testGeneralizedTricorn() {
    assertNextTerm(ComplexSequenceType.GENERALIZED_TRICORN, -2.4, 2.6);
  }

  /** Test for {@link ComplexSequenceType#MULTI_JULIA}: (1 - i)^3 + c. */
  @Test
  void testMultiJulia() {
    assertNextTerm(ComplexSequenceType.MULTI_JULIA, -2.4, -1.4);
  }

  /** Test for {@link ComplexSequenceType#MULTIBROT}: (1 - i)^3 + z0. */
  @Test
  void testMultibrot() {
    assertNextTerm(ComplexSequenceType.MULTIBROT, -1, -1);
  }

  /** Test for {@link ComplexSequenceType#CUBIC_JULIA}: (1 - i)^3 + (1 - i)^2 + c. */
  @Test
  void testCubicJulia() {
    assertNextTerm(ComplexSequenceType.CUBIC_JULIA, -2.4, -3.4);
  }

  /** Test for {@link ComplexSequenceType#MIXED_JULIA_MANDELBROT}: (1 - i)^2 + c * z0. */
  @Test
  void testMixedJuliaMandelbrot() {
    assertNextTerm(ComplexSequenceType.MIXED_JULIA_MANDELBROT, -1, -1.8);
  }

}