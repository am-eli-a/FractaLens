/*
 * FractaLens - A Fractal Generator Library Developed in Lens.
 * Copyright (c) 2022-2026 Romain Wallon - Université d'Artois.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 */


package fr.univartois.butinfo.fractalens.complex.sequence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.IComplex;

/**
 * The ComplexSequencesTest class tests the factory methods of
 * {@link ComplexSequences}.
 *
 * The values are chosen so that the expected results can be computed by hand.
 *
 *
 * @version 0.1.0
 */
class ComplexSequencesTest {

  /**
   * The first term used for the sequences, which is not used by computeNext.
   */
  private static final IComplex Z0 = new Complex(0, 0);

  /**
   * The maximum number of iterations used for the sequences.
   */
  private static final int MAX_ITERATIONS = 50;

  /**
   * Test method for {@link ComplexSequences#multiJulia(IComplex, int, IComplex, int)}
   * on a real number: 2^3 + 1 = 9.
   */
  @Test
  void testMultiJuliaOnRealNumber() {
    IComplexSequence sequence = ComplexSequences.multiJulia(Z0, MAX_ITERATIONS, new Complex(1, 0), 3);

    assertEquals(new Complex(9, 0), sequence.computeNext(new Complex(2, 0)));
  }

  /**
   * Test method for {@link ComplexSequences#multiJulia(IComplex, int, IComplex, int)}
   * on an imaginary number: i^3 + 1 = 1 - i.
   */
  @Test
  void testMultiJuliaOnImaginaryNumber() {
    IComplexSequence sequence = ComplexSequences.multiJulia(Z0, MAX_ITERATIONS, new Complex(1, 0), 3);

    assertEquals(new Complex(1, -1), sequence.computeNext(new Complex(0, 1)));
  }

  /**
   * Test method for {@link ComplexSequences#multiJulia(IComplex, int, IComplex, int)}
   * with a power of 2: the result must be the same as the classical Julia sequence.
   */
  @Test
  void testMultiJuliaWithPowerTwoIsJulia() {
    IComplex c = new Complex(0.5, 0.5);
    IComplex previous = new Complex(1, 1);
    IComplexSequence multiJulia = ComplexSequences.multiJulia(Z0, MAX_ITERATIONS, c, 2);
    IComplexSequence julia = new JuliaSequence(Z0, MAX_ITERATIONS, c);

    assertEquals(julia.computeNext(previous), multiJulia.computeNext(previous));
  }

  /**
   * Test method for {@link ComplexSequences#burningShip(IComplex, int, IComplex)}:
   * (|-1| + i|2|)^2 + 0 = (1 + 2i)^2 = -3 + 4i.
   * With Julia's sequence, the result would be -3 - 4i, which shows that the
   * absolute values are taken.
   */
  @Test
  void testBurningShip() {
    IComplexSequence sequence = ComplexSequences.burningShip(Z0, MAX_ITERATIONS, new Complex(0, 0));

    assertEquals(new Complex(-3, 4), sequence.computeNext(new Complex(-1, 2)));
  }

  /**
   * Test method for
   * {@link ComplexSequences#generalizedBurningShip(IComplex, int, IComplex, int)}:
   * (|-1| + i|-1|)^3 + 1 = (1 + i)^3 + 1 = -1 + 2i.
   */
  @Test
  void testGeneralizedBurningShip() {
    IComplexSequence sequence = ComplexSequences.generalizedBurningShip(Z0, MAX_ITERATIONS, new Complex(1, 0), 3);

    assertEquals(new Complex(-1, 2), sequence.computeNext(new Complex(-1, -1)));
  }

  /**
   * Test method for the iterator of a sequence created by
   * {@link ComplexSequences#multiJulia(IComplex, int, IComplex, int)} when the
   * sequence converges: all the terms must be computed.
   */
  @Test
  void testIteratorOnConvergence() {
    IComplexSequence sequence = ComplexSequences.multiJulia(new Complex(0.5, 0), MAX_ITERATIONS, new Complex(0, 0), 3);
    int nbIterations = 0;

    for (var _ : sequence) {
      nbIterations++;
    }

    assertEquals(MAX_ITERATIONS, nbIterations);
  }

  /**
   * Test method for the iterator of a sequence created by
   * {@link ComplexSequences#burningShip(IComplex, int, IComplex)} when the
   * sequence diverges: the iteration must stop before the maximum.
   */
  @Test
  void testIteratorOnDivergence() {
    IComplexSequence sequence = ComplexSequences.burningShip(new Complex(2, 2), MAX_ITERATIONS, new Complex(1, 1));
    int nbIterations = 0;

    for (var _ : sequence) {
      nbIterations++;
    }

    assertTrue(nbIterations < MAX_ITERATIONS);
  }

  /**
   * Test method for {@link ComplexSequences#cubicJulia(IComplex, int, IComplex)},
   * starting from 0: 0^3 + 0^2 + c = c.
   */
  @Test
  void testCubicJuliaComputeNextFromZero(){
    IComplexSequence sequence = ComplexSequences.cubicJulia(new Complex(0,1), MAX_ITERATIONS, new Complex(1, 1));
    IComplex result = sequence.computeNext(new Complex(0,0));
    assertEquals(new Complex(1,1), result);
  }

  /**
   * Test method for {@link ComplexSequences#cubicJulia(IComplex, int, IComplex)},
   * starting from 1: 1^3 + 1^2 + 0 = 2.
   */
  @Test
  void testCubicJuliaComputeNextFromOne() {
    IComplexSequence sequence = ComplexSequences.cubicJulia(new Complex(0, 0), MAX_ITERATIONS, new Complex(0, 0));
    IComplex result = sequence.computeNext(new Complex(1, 0));
    assertEquals(new Complex(2, 0), result);
  }
  /**
   * Test method for {@link ComplexSequences#tricorn(IComplex, int, IComplex)}:
   * conj(1 + i)^2 + 0 = (1 - i)^2 = -2i.
   * With Julia's sequence, the result would be 2i, which shows that the
   * conjugate is taken.
   */
  @Test
  void testTricorn() {
    IComplex c = new Complex(0, 0);
    IComplexSequence sequence = ComplexSequences.tricorn(Z0, MAX_ITERATIONS, c);

    assertEquals(new Complex(0, -2), sequence.computeNext(new Complex(1, 1)));
  }

  /**
   * Test method for {@link ComplexSequences#cubicJulia(IComplex, int, IComplex)},
   * starting from i: i^3 + i^2 + 0 = -i - 1.
   */
  @Test
  void testCubicJuliaComputeNextFromI() {
    IComplexSequence sequence = ComplexSequences.cubicJulia(new Complex(0, 0), MAX_ITERATIONS, new Complex(0, 0));
    IComplex result = sequence.computeNext(new Complex(0, 1));
    assertEquals(new Complex(-1, -1), result);
  }

  /**
   * Test method for {@link ComplexSequences#cubicJulia(IComplex, int, IComplex)},
   * when the sequence diverges: the iteration stops before the maximum.
   */
  @Test
  void testCubicJuliaOnDivergence() {
    IComplexSequence sequence = ComplexSequences.cubicJulia(new Complex(2, 0), 50, new Complex(0, 0));
    int nbIterations = 0;
    for (var _ : sequence) {
      nbIterations++;
    }
    assertTrue(nbIterations < 50);
  }

  /**
   * Test method for {@link ComplexSequences#cubicJulia(IComplex, int, IComplex)},
   * when the sequence converges (it stays at 0): all the terms are computed.
   */
  @Test
  void testCubicJuliaOnConvergence() {
    IComplexSequence sequence = ComplexSequences.cubicJulia(new Complex(0, 0), 50, new Complex(0, 0));
    int nbIterations = 0;
    for (var _ : sequence) {
      nbIterations++;
    }
    assertEquals(50, nbIterations);
  }

  /**
   * Test method for
   * {@link ComplexSequences#mixedJuliaMandelbrot(IComplex, int, IComplex)}, when the
   * first term is 0 (c × z0 = 0, so the next term is only previous²).
   */
  @Test
  void testMixedJuliaMandelbrotWithZeroFirstTerm() {
    IComplexSequence sequence = ComplexSequences.mixedJuliaMandelbrot(new Complex(0, 0), MAX_ITERATIONS, new Complex(1, 1));
    IComplex result = sequence.computeNext(new Complex(1, 0));
    assertEquals(new Complex(1, 0), result);
  }

  /**
   * Test method for
   * {@link ComplexSequences#mixedJuliaMandelbrot(IComplex, int, IComplex)}, when the
   * previous term is 0 (the next term is then c × z0).
   */
  @Test
  void testMixedJuliaMandelbrotComputeNextFromZero() {
    IComplexSequence sequence = ComplexSequences.mixedJuliaMandelbrot(new Complex(1, 0), MAX_ITERATIONS, new Complex(1, 1));
    IComplex result = sequence.computeNext(new Complex(0, 0));
    assertEquals(new Complex(1, 1), result);
  }

  /**
   * Test method for
   * {@link ComplexSequences#mixedJuliaMandelbrot(IComplex, int, IComplex)}, checking the
   * order of the operations: 1² + 1 × 2 = 3, whereas (1² + 1) × 2 would give 4.
   */
  @Test
  void testMixedJuliaMandelbrotOrderOfOperations() {
    IComplexSequence sequence = ComplexSequences.mixedJuliaMandelbrot(new Complex(2, 0), MAX_ITERATIONS, new Complex(1, 0));
    IComplex result = sequence.computeNext(new Complex(1, 0));
    assertEquals(new Complex(3, 0), result);
  }

  /**
   * Test method for
   * {@link ComplexSequences#mixedJuliaMandelbrot(IComplex, int, IComplex)}, with
   * imaginary numbers: 0² + i × i = -1.
   */
  @Test
  void testMixedJuliaMandelbrotWithImaginaryNumbers() {
    IComplexSequence sequence = ComplexSequences.mixedJuliaMandelbrot(new Complex(0, 1), MAX_ITERATIONS, new Complex(0, 1));
    IComplex result = sequence.computeNext(new Complex(0, 0));
    assertEquals(new Complex(-1, 0), result);
  }

  /**
   * Test method for
   * {@link ComplexSequences#mixedJuliaMandelbrot(IComplex, int, IComplex)}, when the
   * sequence diverges (2² + 1 × 2 = 6, whose modulus is greater than 2).
   */
  @Test
  void testMixedJuliaMandelbrotOnDivergence() {
    IComplexSequence sequence = ComplexSequences.mixedJuliaMandelbrot(new Complex(2, 0), 50, new Complex(1, 0));
    int nbIterations = 0;
    for (var _ : sequence) {
      nbIterations++;
    }
    assertTrue(nbIterations < 50);
  }

  /**
   * Test method for
   * {@link ComplexSequences#mixedJuliaMandelbrot(IComplex, int, IComplex)}, when the
   * sequence converges (z0 = 0, so c × z0 = 0 and the sequence stays at 0).
   */
  @Test
  void testMixedJuliaMandelbrotOnConvergence() {
    IComplexSequence sequence = ComplexSequences.mixedJuliaMandelbrot(new Complex(0, 0), 50, new Complex(1, 1));
    int nbIterations = 0;
    for (var _ : sequence) {
      nbIterations++;
    }
    assertEquals(50, nbIterations);
  }




  /**
   * Test method for {@link ComplexSequences#tricorn(IComplex, int, IComplex)}, with a
   * constant: conj(1 + i)^2 + 1 = -2i + 1.
   */
  @Test
  void testTricornWithConstant() {
    IComplexSequence sequence = ComplexSequences.tricorn(Z0, MAX_ITERATIONS, new Complex(1, 0));

    assertEquals(new Complex(1, -2), sequence.computeNext(new Complex(1, 1)));
  }

  /**
   * Test method for
   * {@link ComplexSequences#tricornGeneral(IComplex, int, IComplex, int)} with a power
   * of 2: the result must be the same as the classical Tricorn sequence.
   */
  @Test
  void testTricornGeneralWithPowerTwoIsTricorn() {
    IComplex c = new Complex(0.5, 0.5);
    IComplex previous = new Complex(1, 1);
    IComplexSequence tricornGeneral = ComplexSequences.tricornGeneral(Z0, MAX_ITERATIONS, c, 2);
    IComplexSequence tricorn = ComplexSequences.tricorn(Z0, MAX_ITERATIONS, c);

    assertEquals(tricorn.computeNext(previous), tricornGeneral.computeNext(previous));
  }

  /**
   * Test method for
   * {@link ComplexSequences#tricornGeneral(IComplex, int, IComplex, int)} with a power
   * of 3: conj(1 + i)^3 + 0 = (1 - i)^3 = -2 - 2i.
   */
  @Test
  void testTricornGeneralWithPowerThree() {
    IComplexSequence sequence = ComplexSequences.tricornGeneral(Z0, MAX_ITERATIONS, new Complex(0, 0), 3);

    assertEquals(new Complex(-2, -2), sequence.computeNext(new Complex(1, 1)));
  }

  /**
   * Test method for {@link ComplexSequences#mandelbrot(IComplex, int)} on a real
   * number: 2^2 + 1 = 5.
   */
  @Test
  void testMandelbrotOnRealNumber() {
    IComplexSequence sequence = ComplexSequences.mandelbrot(new Complex(1, 0), MAX_ITERATIONS);

    assertEquals(new Complex(5, 0), sequence.computeNext(new Complex(2, 0)));
  }

  /**
   * Test method for {@link ComplexSequences#mandelbrot(IComplex, int)} on a complex
   * number: (1 + i)^2 + 1 = 2i + 1 = 1 + 2i.
   */
  @Test
  void testMandelbrotOnComplexNumber() {
    IComplexSequence sequence = ComplexSequences.mandelbrot(new Complex(1, 0), MAX_ITERATIONS);

    assertEquals(new Complex(1, 2), sequence.computeNext(new Complex(1, 1)));
  }

  /**
   * Test method for {@link ComplexSequences#mandelbrot(IComplex, int)} when the
   * previous term is 0: the next term is then the first term z0 (and not a constant
   * given separately, as in Julia's sequence).
   */
  @Test
  void testMandelbrotComputeNextFromZero() {
    IComplexSequence sequence = ComplexSequences.mandelbrot(new Complex(1, 1), MAX_ITERATIONS);

    assertEquals(new Complex(1, 1), sequence.computeNext(new Complex(0, 0)));
  }

  /**
   * Test method for the iterator of a sequence created by
   * {@link ComplexSequences#mandelbrot(IComplex, int)} when the sequence converges
   * (z0 = 0 stays at 0): all the terms must be computed.
   */
  @Test
  void testMandelbrotIteratorOnConvergence() {
    IComplexSequence sequence = ComplexSequences.mandelbrot(new Complex(0, 0), MAX_ITERATIONS);
    int nbIterations = 0;

    for (var _ : sequence) {
      nbIterations++;
    }

    assertEquals(MAX_ITERATIONS, nbIterations);
  }

  /**
   * Test method for the iterator of a sequence created by
   * {@link ComplexSequences#mandelbrot(IComplex, int)} with z0 = -1, which alternates
   * between -1 and 0 and never diverges.
   */
  @Test
  void testMandelbrotIteratorOnPeriodicPoint() {
    IComplexSequence sequence = ComplexSequences.mandelbrot(new Complex(-1, 0), MAX_ITERATIONS);
    int nbIterations = 0;

    for (var _ : sequence) {
      nbIterations++;
    }

    assertEquals(MAX_ITERATIONS, nbIterations);
  }

  /**
   * Test method for the iterator of a sequence created by
   * {@link ComplexSequences#mandelbrot(IComplex, int)} when the sequence diverges:
   * z0 = 2 gives 2^2 + 2 = 6, whose modulus is greater than 2, after one iteration.
   */
  @Test
  void testMandelbrotIteratorOnDivergence() {
    IComplexSequence sequence = ComplexSequences.mandelbrot(new Complex(2, 0), MAX_ITERATIONS);
    int nbIterations = 0;

    for (var _ : sequence) {
      nbIterations++;
    }

    assertEquals(1, nbIterations);
  }

  /**
   * Test method for {@link ComplexSequences#multibrot(IComplex, int, int)} on a real
   * number: 2^3 + 1 = 9.
   */
  @Test
  void testMultibrotOnRealNumber() {
    IComplexSequence sequence = ComplexSequences.multibrot(new Complex(1, 0), MAX_ITERATIONS, 3);

    assertEquals(new Complex(9, 0), sequence.computeNext(new Complex(2, 0)));
  }

  /**
   * Test method for {@link ComplexSequences#multibrot(IComplex, int, int)} on an
   * imaginary number: i^3 + 1 = -i + 1 = 1 - i.
   */
  @Test
  void testMultibrotOnImaginaryNumber() {
    IComplexSequence sequence = ComplexSequences.multibrot(new Complex(1, 0), MAX_ITERATIONS, 3);

    assertEquals(new Complex(1, -1), sequence.computeNext(new Complex(0, 1)));
  }

  /**
   * Test method for {@link ComplexSequences#multibrot(IComplex, int, int)} with a
   * power of 2: the result must be the same as the Mandelbrot sequence.
   */
  @Test
  void testMultibrotWithPowerTwoIsMandelbrot() {
    IComplex z0 = new Complex(0.5, 0.5);
    IComplex previous = new Complex(1, 1);
    IComplexSequence multibrot = ComplexSequences.multibrot(z0, MAX_ITERATIONS, 2);
    IComplexSequence mandelbrot = ComplexSequences.mandelbrot(z0, MAX_ITERATIONS);

    assertEquals(mandelbrot.computeNext(previous), multibrot.computeNext(previous));
  }

  /**
   * Test method for the iterator of a sequence created by
   * {@link ComplexSequences#multibrot(IComplex, int, int)} when the sequence converges.
   */
  @Test
  void testMultibrotIteratorOnConvergence() {
    IComplexSequence sequence = ComplexSequences.multibrot(new Complex(0, 0), MAX_ITERATIONS, 3);
    int nbIterations = 0;

    for (var _ : sequence) {
      nbIterations++;
    }

    assertEquals(MAX_ITERATIONS, nbIterations);
  }

  /**
   * Test method for the iterator of a sequence created by
   * {@link ComplexSequences#multibrot(IComplex, int, int)} when the sequence diverges:
   * z0 = 2 gives 2^3 + 2 = 10, whose modulus is greater than 2, after one iteration.
   */
  @Test
  void testMultibrotIteratorOnDivergence() {
    IComplexSequence sequence = ComplexSequences.multibrot(new Complex(2, 0), MAX_ITERATIONS, 3);
    int nbIterations = 0;

    for (var _ : sequence) {
      nbIterations++;
    }

    assertEquals(1, nbIterations);
  }

  /**
   * Checks that a complex number has the expected real and imaginary parts, with a
   * tolerance because the rotations are computed with cosines and sines.
   *
   * @param expectedReal The expected real part.
   * @param expectedImaginary The expected imaginary part.
   * @param actual The complex number to check.
   */
  private static void assertComplexEquals(double expectedReal, double expectedImaginary,
                                          IComplex actual) {
    assertEquals(expectedReal, actual.getRealPart(), 0.001);
    assertEquals(expectedImaginary, actual.getImaginaryPart(), 0.001);
  }

  /**
   * Test method for
   * {@link ComplexSequences#rotatedMultiJulia(IComplex, int, IComplex, double, int)}
   * with an angle of 0: the constant is unchanged.
   */
  @Test
  void testRotatedMultiJuliaWithAngleZero() {
    IComplexSequence sequence = ComplexSequences.rotatedMultiJulia(Z0, MAX_ITERATIONS, new Complex(1, 1), 0, 2);

    assertComplexEquals(1, 1, sequence.computeNext(new Complex(0, 0)));
  }

  /**
   * Test method for
   * {@link ComplexSequences#rotatedMultiJulia(IComplex, int, IComplex, double, int)}
   * with an angle of pi/2: the constant is multiplied by i, so
   * i * (1 + i) = -1 + i.
   */
  @Test
  void testRotatedMultiJuliaWithQuarterTurn() {
    IComplexSequence sequence = ComplexSequences.rotatedMultiJulia(Z0, MAX_ITERATIONS, new Complex(1, 1), Math.PI / 2, 2);

    assertComplexEquals(-1, 1, sequence.computeNext(new Complex(0, 0)));
  }

  /**
   * Test method for
   * {@link ComplexSequences#rotatedMultiJulia(IComplex, int, IComplex, double, int)}
   * with an angle of pi: the constant becomes its opposite, -(1 + i).
   */
  @Test
  void testRotatedMultiJuliaWithHalfTurn() {
    IComplexSequence sequence = ComplexSequences.rotatedMultiJulia(Z0, MAX_ITERATIONS, new Complex(1, 1), Math.PI, 2);

    assertComplexEquals(-1, -1, sequence.computeNext(new Complex(0, 0)));
  }

  /**
   * Test method for
   * {@link ComplexSequences#rotatedMultiJulia(IComplex, int, IComplex, double, int)}
   * with an angle of 2 pi: a full turn gives back the original constant.
   */
  @Test
  void testRotatedMultiJuliaWithFullTurn() {
    IComplexSequence sequence = ComplexSequences.rotatedMultiJulia(Z0, MAX_ITERATIONS, new Complex(1, 1), 2 * Math.PI, 2);

    assertComplexEquals(1, 1, sequence.computeNext(new Complex(0, 0)));
  }

  /**
   * Test method for
   * {@link ComplexSequences#rotatedMultiJulia(IComplex, int, IComplex, double, int)}
   * on a real constant: 2 rotated by pi/2 gives 2i.
   */
  @Test
  void testRotatedMultiJuliaOnRealConstant() {
    IComplexSequence sequence = ComplexSequences.rotatedMultiJulia(Z0, MAX_ITERATIONS, new Complex(2, 0), Math.PI / 2, 2);

    assertComplexEquals(0, 2, sequence.computeNext(new Complex(0, 0)));
  }

  /**
   * Test method for
   * {@link ComplexSequences#rotatedMultiJulia(IComplex, int, IComplex, double, int)}
   * with a null constant: the rotation does not change it.
   */
  @Test
  void testRotatedMultiJuliaWithNullConstant() {
    IComplexSequence sequence = ComplexSequences.rotatedMultiJulia(Z0, MAX_ITERATIONS, new Complex(0, 0), 1.234, 2);

    assertComplexEquals(0, 0, sequence.computeNext(new Complex(0, 0)));
  }

  /**
   * Test method for
   * {@link ComplexSequences#rotatedMultiJulia(IComplex, int, IComplex, double, int)}
   * checking that a rotation keeps the modulus of the constant: |3 + 4i| = 5.
   */
  @Test
  void testRotatedMultiJuliaKeepsModulus() {
    IComplexSequence sequence = ComplexSequences.rotatedMultiJulia(Z0, MAX_ITERATIONS, new Complex(3, 4), 0.7, 2);

    assertEquals(5, sequence.computeNext(new Complex(0, 0)).abs(), 0.001);
  }

  /**
   * Test method for
   * {@link ComplexSequences#rotatedMultiJulia(IComplex, int, IComplex, double, int)}
   * checking that the power is used: 2^3 + (1 rotated by pi = -1) = 7.
   */
  @Test
  void testRotatedMultiJuliaUsesPower() {
    IComplexSequence sequence = ComplexSequences.rotatedMultiJulia(Z0, MAX_ITERATIONS, new Complex(1, 0), Math.PI, 3);

    assertComplexEquals(7, 0, sequence.computeNext(new Complex(2, 0)));
  }
}