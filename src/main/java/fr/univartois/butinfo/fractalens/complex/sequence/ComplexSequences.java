package fr.univartois.butinfo.fractalens.complex.sequence;
import fr.univartois.butinfo.fractalens.complex.IComplex;


public final class ComplexSequences {
    private ComplexSequences() {}

    public static IComplexSequence tricorn(IComplex z0, int maxIterations, IComplex c) {
        return new GenericComplexSequence((z, first) -> z.conjugate().power(2).add(c), z0, maxIterations);
    }

    public static IComplexSequence tricornGeneral(IComplex z0, int maxIterations, IComplex c, int p) {
        return new GenericComplexSequence((z, first) -> z.conjugate().power(p).add(c), z0, maxIterations);
    }

}