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

import fr.univartois.butinfo.fractalens.complex.IComplex;
import java.util.Iterator;

/**
 * The JuliaSequence class represents a Julia sequence of complex numbers,
 * defined by z(n+1) = z(n)^2 + c, where c is a constant complex number.
 *
 * @author Baptiste Wagee
 *
 * @version 0.1.0
 */
public class JuliaSequence implements IComplexSequence {

  /**
   * The first term of the sequence.
   */
  private final IComplex first ;
  /**
   * The max number of terms to compute.
   */
  private final int max ;
  /**
   * The constant complex number used in the definition of the sequence.
   */
  private final IComplex c ;

  /**
   * Creates a new JuliaSequence.
   *
   * @param first The first term of the sequence.
   * @param max The maximum number of terms to compute.
   * @param c The constant complex number used in the definition of the sequence.
   */
  public JuliaSequence(IComplex first, int max, IComplex c) {
    this.first = first;
    this.max = max;
    this.c = c;
  }

  /**
   * Computes the next term of the sequence, using the formula
   * z(n+1) = z(n)^2 + c.
   *
   * @param previous The previous term of the sequence.
   *
   * @return The next term of the sequence.
   *
   * @see IComplexSequence#computeNext(IComplex)
   */
  @Override
  public IComplex computeNext(IComplex previous){
    return previous.multiply(previous).add(c);
  }

  /**
   * Gives an iterator over the terms of this sequence.
   *
   * @return An iterator over the terms of this sequence, starting from the
   *         first term.
   *
   * @see Iterable#iterator()
   */
  @Override
  public Iterator<IComplex> iterator() {
    return new ComplexSequenceIterator(this, first, max);
  }

}