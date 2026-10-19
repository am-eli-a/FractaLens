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

import java.util.Iterator;
import java.util.NoSuchElementException;

import fr.univartois.butinfo.fractalens.complex.IComplex;

/**
 * The ComplexSequenceIterator class allows to iterate over the terms of a sequence
 * of complex numbers.
 * The terms are computed lazily, one at a time, using only the
 * {@link IComplexSequence} interface, so that this iterator can be reused for any
 * complex sequence.
 *
 * @author Matty Fourmestraux
 *
 * @version 0.1.0
 */
public class ComplexSequenceIterator implements Iterator<IComplex> {

	/**
	 * The sequence used to compute the terms.
	 */
	private final IComplexSequence sequence;

	/**
	 * The last term that has been computed.
	 */
	private IComplex current;

	/**
	 * The maximum number of iterations to perform.
	 */
	private final int maxIterations;

	/**
	 * The number of iterations performed so far.
	 */
	private int nbIterations;

	/**
	 * Creates a new ComplexSequenceIterator.
	 *
	 * @param sequence The sequence used to compute the terms.
	 * @param first The first term of the sequence.
	 * @param maxIterations The maximum number of iterations to perform.
	 */
	public ComplexSequenceIterator(IComplexSequence sequence, IComplex first, int maxIterations) {
		this.sequence = sequence;
		this.current = first;
		this.maxIterations = maxIterations;
		this.nbIterations = 0;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		return nbIterations < maxIterations && current.abs() <= 2;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Iterator#next()
	 */
	@Override
	public IComplex next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		IComplex initial = sequence.computeNext(current);
		current = sequence.computeNext(current);
		nbIterations++;
		return initial;
	}

}