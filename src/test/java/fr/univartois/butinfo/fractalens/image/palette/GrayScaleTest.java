package fr.univartois.butinfo.fractalens.image.palette;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Color;

import org.junit.jupiter.api.Test;

/**
 * The GrayScaleTest tests the {@link GrayScale} class.
 *
 * @author Amelia Dufour-Lazrek
 *
 * @version 0.1.0
 */
class GrayScaleTest {

    /**
     * Test method for {@link GrayScale#getColor(int, int)} with no iteration.
     */
    @Test
    void testGetColorWithZeroIteration() {
        GrayScale palette = new GrayScale();

        assertEquals(Color.WHITE, palette.getColor(0, 100));
    }

    /**
     * Test method for {@link GrayScale#getColor(int, int)} with the maximum number of
     * iterations.
     */
    @Test
    void testGetColorWithMaxIterations() {
        GrayScale palette = new GrayScale();

        assertEquals(Color.BLACK, palette.getColor(100, 100));
    }

    /**
     * Test method for {@link GrayScale#getColor(int, int)} with an intermediate number
     * of iterations.
     */
    @Test
    void testGetColorWithIntermediateIterations() {
        GrayScale palette = new GrayScale();

        Color color = palette.getColor(50, 100);

        assertEquals(color.getRed(), color.getGreen());
        assertEquals(color.getGreen(), color.getBlue());
        assertEquals(new Color(0.5f, 0.5f, 0.5f), color);
    }

    /**
     * Test method for {@link GrayScale#getColor(int, int)} with a negative number of
     * iterations.
     */
    @Test
    void testGetColorWithNegativeIterations() {
        GrayScale palette = new GrayScale();

        assertThrows(IllegalArgumentException.class, () -> palette.getColor(-1, 100));
    }

    /**
     * Test method for {@link GrayScale#getColor(int, int)} with more iterations than
     * the maximum.
     */
    @Test
    void testGetColorWithTooManyIterations() {
        GrayScale palette = new GrayScale();

        assertThrows(IllegalArgumentException.class, () -> palette.getColor(101, 100));
    }

}