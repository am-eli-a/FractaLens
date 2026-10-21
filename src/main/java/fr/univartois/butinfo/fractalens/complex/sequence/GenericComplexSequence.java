package fr.univartois.butinfo.fractalens.complex.sequence;

import fr.univartois.butinfo.fractalens.complex.IComplex;
import java.util.function.BinaryOperator;
import java.util.Iterator;

public class GenericComplexSequence implements IComplexSequence {
    private final IComplex z0;
    private final int maxIterations;
    private final BinaryOperator<IComplex> operator;

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
