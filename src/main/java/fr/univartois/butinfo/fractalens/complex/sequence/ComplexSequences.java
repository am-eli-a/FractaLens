/*
 * FractaLens - A Fractal Generator Library Developed in Lens.
 * Copyright (c) 2022-2026 Romain Wallon - Université d'Artois.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
    private ComplexSequences() {}

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
     *
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
     *
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

    public static BiFunction<IComplex, Integer, IComplexSequence> mandelbrot() {
        return (z0, max) -> new GenericComplexSequence((z, c) -> z.multiply(z).add(c), z0, max);
    }

    public static BiFunction<IComplex, Integer, IComplexSequence> multibrot(int p) {
        return (z0, max) -> new GenericComplexSequence((z, c) -> z.power(p).add(c), z0, max);
    }
}
