package fr.univartois.butinfo.fractalens.complex.sequence;

import fr.univartois.butinfo.fractalens.complex.IComplex;
import java.util.function.BinaryOperator;
import java.util.Iterator;

/**
 * The GenericComplexSequence class is a generic implementation of
 * {@link IComplexSequence}, whose formula is given as a function.
 *
 * This function receives the previous term and the first term of the sequence, and
 * computes the next term. It allows to define any sequence with a lambda expression,
 * without writing a new class for each sequence.
 *
 *
 * @version 0.1.0
 */
public class GenericComplexSequence implements IComplexSequence {

    /**
     * The first term of the sequence.
     */
    private final IComplex z0;

    /**
     * The maximum number of iterations to perform on this sequence.
     */
    private final int maxIterations;

    /**
     * The function computing the next term from the previous term and the first term.
     */
    private final BinaryOperator<IComplex> operator;

    /**
     * Creates a new GenericComplexSequence.
     *
     * @param operator The function computing the next term from the previous term and
     *        the first term.
     * @param z0 The first term of the sequence.
     * @param maxIterations The maximum number of iterations to perform.
     */
    public GenericComplexSequence(BinaryOperator<IComplex> operator, IComplex z0, int maxIterations) {
        this.operator = operator;
        this.z0 = z0;
        this.maxIterations = maxIterations;
    }

    /**
     * Computes the next term of the sequence, by applying the function of this
     * sequence to the previous term and to the first term.
     *
     * @param previous The previous term of the sequence.
     *
     * @return The next term of the sequence.
     *
     * @see IComplexSequence#computeNext(IComplex)
     */
    @Override
    public IComplex computeNext(IComplex previous) {
        return operator.apply(previous, z0);
    }

    /**
     * Gives an iterator over the terms of this sequence, starting from the first term.
     *
     * @return A new iterator over the terms of this sequence.
     *
     * @see Iterable#iterator()
     */
    @Override
    public Iterator<IComplex> iterator() {
        return new ComplexSequenceIterator(this, z0, maxIterations);
    }
}