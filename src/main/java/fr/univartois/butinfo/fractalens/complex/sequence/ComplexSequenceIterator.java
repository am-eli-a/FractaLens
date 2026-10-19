package fr.univartois.butinfo.fractalens.complex.sequence;
import java.util.Iterator;
import java.util.NoSuchElementException;

import fr.univartois.butinfo.fractalens.complex.IComplex;

public class ComplexSequenceIterator implements Iterator<IComplex> {
	private final IComplexSequence sequence;
	private IComplex current;
	private final int maxIterations;
	private int nbIterations;
	public ComplexSequenceIterator(IComplexSequence sequence, IComplex first, int maxIterations){
		this.current = first;
		this.sequence = sequence;
		this.maxIterations = maxIterations;
		this.nbIterations = 0;
	}

	@Override
	public boolean hasNext() {
		return nbIterations < maxIterations && current.abs() <= 2;
	}

	@Override
	public IComplex next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		current = sequence.computeNext(current);
		nbIterations ++;
		return current;
	}
}
