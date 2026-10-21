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
 * @author Baptiste Wagee
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

  @Test
  void testTricornWithConstant() {
    IComplexSequence sequence = ComplexSequences.tricorn(Z0, MAX_ITERATIONS, new Complex(1, 0));

    assertEquals(new Complex(1, -2), sequence.computeNext(new Complex(1, 1)));
  }

  @Test
  void testTricornGeneralWithPowerTwoIsTricorn() {
    IComplex c = new Complex(0.5, 0.5);
    IComplex previous = new Complex(1, 1);
    IComplexSequence tricornGeneral = ComplexSequences.tricornGeneral(Z0, MAX_ITERATIONS, c, 2);
    IComplexSequence tricorn = ComplexSequences.tricorn(Z0, MAX_ITERATIONS, c);

    assertEquals(tricorn.computeNext(previous), tricornGeneral.computeNext(previous));
  }

  @Test
  void testTricornGeneralWithPowerThree() {
    IComplexSequence sequence = ComplexSequences.tricornGeneral(Z0, MAX_ITERATIONS, new Complex(0, 0), 3);

    assertEquals(new Complex(-2, -2), sequence.computeNext(new Complex(1, 1)));
  }
}