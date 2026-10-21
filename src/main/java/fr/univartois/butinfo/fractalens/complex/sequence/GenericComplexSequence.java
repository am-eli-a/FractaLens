package fr.univartois.butinfo.fractalens.complex.sequence;

import fr.univartois.butinfo.fractalens.complex.IComplex;
import java.util.function.BinaryOperator;
import java.util.Iterator;

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

    @Override
    public IComplex computeNext(IComplex previous) {
        return operator.apply(previous, z0);
    }

    @Override
    public Iterator<IComplex> iterator() {
        return new ComplexSequenceIterator(this, z0, maxIterations);
    }
}
