package fr.univartois.butinfo.fractalens.complex.sequence;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.IComplex;

/**
 * The ComplexSequences class provides static factory methods creating the complex
 * sequences used to generate fractals.
 *
 * Each method creates a {@link GenericComplexSequence} with a lambda expression
 * representing the formula of the sequence, so that no new class is needed for each
 * new sequence. In these lambda expressions, the first parameter is the previous term
 * of the sequence, and the second one is its first term.
 *
 *
 * @version 0.1.0
 */
public final class ComplexSequences {

    /**
     * Disables instantiation, since this class only contains static methods.
     */
    private ComplexSequences() {
    }

    /**
     * Creates a generalized Julia sequence (Multijulia), defined by
     * {@code z(n+1) = z(n)^p + c}.
     *
     * @param z0 The first term of the sequence.
     * @param maxIterations The maximum number of terms to compute.
     * @param c The constant complex number of the sequence.
     * @param p The power to which each term is raised.
     *
     * @return The created generalized Julia sequence.
     */
    public static IComplexSequence multiJulia(IComplex z0, int maxIterations, IComplex c, int p) {
        return new GenericComplexSequence(
                (previous, first) -> previous.power(p).add(c),
                z0,
                maxIterations);
    }

    /**
     * Creates a Burning Ship sequence, defined by
     * {@code z(n+1) = (|Re(z(n))| + i|Im(z(n))|)^2 + c}.
     * This is a generalized Burning Ship sequence with a power of 2.
     *
     * @param z0 The first term of the sequence.
     * @param maxIterations The maximum number of terms to compute.
     * @param c The constant complex number of the sequence.
     *
     * @return The created Burning Ship sequence.
     *
     * @see #generalizedBurningShip(IComplex, int, IComplex, int)
     */
    public static IComplexSequence burningShip(IComplex z0, int maxIterations, IComplex c) {
        return generalizedBurningShip(z0, maxIterations, c, 2);
    }

    /**
     * Creates a generalized Burning Ship sequence, defined by
     * {@code z(n+1) = (|Re(z(n))| + i|Im(z(n))|)^p + c}.
     * The absolute values of the real and imaginary parts of the previous term are
     * taken before raising it to the power p.
     *
     * @param z0 The first term of the sequence.
     * @param maxIterations The maximum number of terms to compute.
     * @param c The constant complex number of the sequence.
     * @param p The power to which each term is raised.
     *
     * @return The created generalized Burning Ship sequence.
     */
    public static IComplexSequence generalizedBurningShip(IComplex z0, int maxIterations, IComplex c, int p) {
        return new GenericComplexSequence(
                (previous, first) -> {
                    IComplex absolute = new Complex(Math.abs(previous.getRealPart()), Math.abs(previous.getImaginaryPart()));
                    return absolute.power(p).add(c);
                },
                z0,
                maxIterations);
    }

    /**
     * Creates a sequence defined by the polynomial {@code z(n+1) = z(n)^3 + z(n)^2 + c}.
     * This is the sequence defined with a free formula for the demonstration.
     *
     * @param z0 The first term of the sequence.
     * @param maxIterations The maximum number of terms to compute.
     * @param c The constant complex number of the sequence.
     *
     * @return The created sequence.
     */
    public static IComplexSequence cubicJulia(IComplex z0, int maxIterations, IComplex c) {
        return new GenericComplexSequence(
                (previous, first) -> previous.power(3).add(previous.power(2)).add(c),
                z0,
                maxIterations);
    }

    /**
     * Creates a Mandelbrot sequence, defined by {@code z(n+1) = z(n)^2 + z0}.
     * Unlike Julia's sequence, the constant c is replaced by the first term of the
     * sequence.
     *
     * @param z0 The first term of the sequence.
     * @param maxIterations The maximum number of terms to compute.
     *
     * @return The created Mandelbrot sequence.
     */
    public static IComplexSequence mandelbrot(IComplex z0, int maxIterations) {
        return new GenericComplexSequence(
                (z, first) -> z.multiply(z).add(first),
                z0,
                maxIterations);
    }

    /**
     * Creates a generalized Mandelbrot sequence (Multibrot), defined by
     * {@code z(n+1) = z(n)^p + z0}.
     *
     * @param z0 The first term of the sequence.
     * @param maxIterations The maximum number of terms to compute.
     * @param p The power to which each term is raised.
     *
     * @return The created Multibrot sequence.
     *
     * @throws IllegalArgumentException If the power is negative.
     */
    public static IComplexSequence multibrot(IComplex z0, int maxIterations, int p) {
        if (p<0) {
            throw new IllegalArgumentException("p must be >= 0");
        }
        return new GenericComplexSequence(
                (z, first) -> z.power(p).add(first),
                z0,
                maxIterations);
    }

    /**
     * Creates a Tricorn sequence, defined by {@code z(n+1) = conj(z(n))^2 + c}, where
     * {@code conj} is the conjugate of a complex number.
     *
     * @param z0 The first term of the sequence.
     * @param maxIterations The maximum number of terms to compute.
     * @param c The constant complex number of the sequence.
     *
     * @return The created Tricorn sequence.
     */
    public static IComplexSequence tricorn(IComplex z0, int maxIterations, IComplex c) {
        return new GenericComplexSequence((z, first) -> z.conjugate().power(2).add(c), z0, maxIterations);
    }

    /**
     * Creates a generalized Tricorn sequence, defined by
     * {@code z(n+1) = conj(z(n))^p + c}, where {@code conj} is the conjugate of a
     * complex number.
     *
     * @param z0 The first term of the sequence.
     * @param maxIterations The maximum number of terms to compute.
     * @param c The constant complex number of the sequence.
     * @param p The power to which the conjugate of each term is raised.
     *
     * @return The created generalized Tricorn sequence.
     */
    public static IComplexSequence tricornGeneral(IComplex z0, int maxIterations, IComplex c, int p) {
        return new GenericComplexSequence((z, first) -> z.conjugate().power(p).add(c), z0, maxIterations);
    }

    /**
     * Creates a sequence mixing Julia's and Mandelbrot's sequences, defined by
     * {@code z(n+1) = z(n)^2 + c * z0}.
     *
     * @param z0 The first term of the sequence.
     * @param maxIterations The maximum number of terms to compute.
     * @param c The constant complex number, multiplied by the first term.
     *
     * @return The created sequence.
     */
    public static IComplexSequence mixedJuliaMandelbrot(IComplex z0, int maxIterations, IComplex c){
        return new GenericComplexSequence(
                (previous, first) -> previous.power(2).add(c.multiply(first)), z0, maxIterations);
    }

    /**
     * Creates a generalized Julia sequence whose constant is rotated by a given angle.
     * The constant c is multiplied by {@code cos(theta) + i sin(theta)}, then the
     * sequence {@code z(n+1) = z(n)^p + c'} is created with the rotated constant c'.
     * This is used to generate the rotation animations.
     *
     * @param z0 The first term of the sequence.
     * @param maxIterations The maximum number of terms to compute.
     * @param c The constant complex number, before the rotation.
     * @param theta The angle of the rotation (in radians).
     * @param p The power to which each term is raised.
     *
     * @return The created sequence, with the rotated constant.
     */
    public static IComplexSequence rotatedMultiJulia(IComplex z0, int maxIterations, IComplex c, double theta, int p) {
        IComplex rotated = new Complex(Math.cos(theta), Math.sin(theta));
        IComplex cRotated = c.multiply(rotated);
        return multiJulia(z0, maxIterations, cRotated, p);
    }
}