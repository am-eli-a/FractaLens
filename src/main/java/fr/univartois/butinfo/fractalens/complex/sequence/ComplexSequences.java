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

    public static IComplexSequence mandelbrot(IComplex z0, int maxIterations) {
        return new GenericComplexSequence(
                (z, first) -> z.multiply(z).add(first),
                z0,
                maxIterations);
    }

    public static IComplexSequence multibrot(IComplex z0, int maxIterations, int p) {
        if (p<0) {
            throw new IllegalArgumentException("p must be >= 0");
        }
        return new GenericComplexSequence(
                (z, first) -> z.power(p).add(first),
                z0,
                maxIterations);
    }

    public static IComplexSequence tricorn(IComplex z0, int maxIterations, IComplex c) {
        return new GenericComplexSequence((z, first) -> z.conjugate().power(2).add(c), z0, maxIterations);
    }

    public static IComplexSequence tricornGeneral(IComplex z0, int maxIterations, IComplex c, int p) {
        return new GenericComplexSequence((z, first) -> z.conjugate().power(p).add(c), z0, maxIterations);
    }

    public static IComplexSequence mixedJuliaMandelbrot(IComplex z0, int maxIterations, IComplex c){
        return new GenericComplexSequence(
                (previous, first) -> previous.power(2).add(c.multiply(first)), z0, maxIterations);
    }

    public static IComplexSequence rotatedMultiJulia(IComplex z0, int maxIterations, IComplex c, double theta, int p) {
        IComplex rotated = new Complex(Math.cos(theta), Math.sin(theta));
        IComplex cRotated = c.multiply(rotated);
        return multiJulia(z0, maxIterations, cRotated, p);
    }
}