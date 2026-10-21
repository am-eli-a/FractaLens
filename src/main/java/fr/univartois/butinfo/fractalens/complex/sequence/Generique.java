package fr.univartois.butinfo.fractalens.complex.sequence;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.IComplex;

import java.util.Iterator;

public class Generique implements IComplexSequence {
    Complex z0;
    int maxIterations;

    public Generique(Complex z0, int maxIterations) {
        this.z0 = z0;
        this.maxIterations = maxIterations;
    }

    @Override
    public IComplex computeNext(IComplex previous) {
        return null;
    }

    @Override
    public Iterator<IComplex> iterator() {
        return null;
    }
}
