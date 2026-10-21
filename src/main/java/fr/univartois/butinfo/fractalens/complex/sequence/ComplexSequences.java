package fr.univartois.butinfo.fractalens.complex.sequence;
import fr.univartois.butinfo.fractalens.complex.IComplex;

import java.util.function.BiFunction;


import fr.univartois.butinfo.fractalens.complex.Complex;

/**
 * The ComplexSequences class provides static factory methods for creating
 * complex sequences.
 *
 * Each method creates a {@link GenericComplexSequence} with a lambda expression
 * representing the formula of the sequence, so that no new class is needed for
 * each new sequence.
 *
 * @author Baptiste Wagee
 *
 * @version 0.1.0
 */
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

    public static BiFunction<IComplex, Integer, IComplexSequence> mandelbrot() {
        return (z0, max) -> new GenericComplexSequence((z, c) -> z.multiply(z).add(c), z0, max);
    }

    public static BiFunction<IComplex, Integer, IComplexSequence> multibrot(int p) {
        return (z0, max) -> new GenericComplexSequence((z, c) -> z.power(p).add(c), z0, max);
    }

    public static IComplexSequence tricorn(IComplex z0, int maxIterations, IComplex c) {
        return new GenericComplexSequence((z, first) -> z.conjugate().power(2).add(c), z0, maxIterations);
    }

    public static IComplexSequence tricornGeneral(IComplex z0, int maxIterations, IComplex c, int p) {
        return new GenericComplexSequence((z, first) -> z.conjugate().power(p).add(c), z0, maxIterations);
    }
}