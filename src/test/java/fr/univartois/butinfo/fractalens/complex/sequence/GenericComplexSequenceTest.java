package fr.univartois.butinfo.fractalens.complex.sequence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.IComplex;

/**
 * The GenericComplexSequenceTest tests the {@link GenericComplexSequence} class.
 * The sequences are defined with simple formulas, so that the expected results can be
 * computed by hand.
 *
 * @author Amelia Dufour-Lazrek
 *
 * @version 0.1.0
 */
class GenericComplexSequenceTest {

    /**
     * Counts the number of terms given by the iterator of a sequence.
     *
     * @param sequence The sequence to iterate over.
     *
     * @return The number of iterations performed.
     */
    private static int countIterations(IComplexSequence sequence) {
        int counter = 0;
        for (var _ : sequence) {
            counter++;
        }
        return counter;
    }

    /**
     * Test method for {@link GenericComplexSequence#computeNext(IComplex)}, checking
     * that the function receives the previous term and the first term, in this order.
     */
    @Test
    void testComputeNextUsesPreviousAndFirstTerms() {
        GenericComplexSequence sequence = new GenericComplexSequence(
                (z, c) -> z.add(c), new Complex(1, 0), 10);

        assertEquals(new Complex(3, 0), sequence.computeNext(new Complex(2, 0)));
    }

    /**
     * Test method for {@link GenericComplexSequence#computeNext(IComplex)} with the
     * Mandelbrot formula.
     */
    @Test
    void testComputeNextWithMandelbrotFormula() {
        GenericComplexSequence sequence = new GenericComplexSequence(
                (z, c) -> z.multiply(z).add(c), new Complex(1, 0), 10);

        assertEquals(new Complex(5, 0), sequence.computeNext(new Complex(2, 0)));
    }

    /**
     * Test method for {@link GenericComplexSequence#iterator()} when the sequence does
     * not diverge.
     */
    @Test
    void testIteratorWhenSequenceDoesNotDiverge() {
        GenericComplexSequence sequence = new GenericComplexSequence(
                (z, c) -> z, new Complex(0, 0), 5);

        assertEquals(5, countIterations(sequence));
    }

    /**
     * Test method for {@link GenericComplexSequence#iterator()} when the sequence
     * diverges after one iteration.
     */
    @Test
    void testIteratorWhenSequenceDiverges() {
        GenericComplexSequence sequence = new GenericComplexSequence(
                (z, c) -> z.multiply(z).add(c), new Complex(2, 0), 10);

        assertEquals(1, countIterations(sequence));
    }

    /**
     * Test method for {@link GenericComplexSequence#iterator()} when the first term is
     * already too far from the origin.
     */
    @Test
    void testIteratorWhenFirstTermIsTooFar() {
        GenericComplexSequence sequence = new GenericComplexSequence(
                (z, c) -> z, new Complex(3, 0), 10);

        assertEquals(0, countIterations(sequence));
    }

    /**
     * Test method for {@link GenericComplexSequence#iterator()} when no iteration is
     * allowed.
     */
    @Test
    void testIteratorWithNoIterationAllowed() {
        GenericComplexSequence sequence = new GenericComplexSequence(
                (z, c) -> z, new Complex(0, 0), 0);

        assertEquals(0, countIterations(sequence));
    }

}