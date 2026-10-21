package fr.univartois.butinfo.fractalens.complex.sequence;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.IComplex;

public final class ComplexSequences {
    private ComplexSequences() {
    }

    public static IComplexSequence multiJulia(IComplex z0, int maxIterations, IComplex c, int p) {
        return new GenericComplexSequence(
                (previous, first) -> previous.power(p).add(c),
                z0,
                maxIterations);
    }

    public static IComplexSequence burningShip(IComplex z0, int maxIterations, IComplex c) {
        return generalizedBurningShip(z0, maxIterations, c, 2);
    }

    public static IComplexSequence generalizedBurningShip(IComplex z0, int maxIterations, IComplex c, int p) {
        return new GenericComplexSequence(
                (previous, first) -> {
                    IComplex absolute = new Complex(Math.abs(previous.getRealPart()), Math.abs(previous.getImaginaryPart()));
                    return absolute.power(p).add(c);
                },
                z0,
                maxIterations);
    }

    public static IComplexSequence cubicJulia(IComplex z0, int maxIterations, IComplex c) {
        return new GenericComplexSequence(
                (previous, first) -> previous.power(3).add(previous.power(2)).add(c),
                z0,
                maxIterations);
    }
}