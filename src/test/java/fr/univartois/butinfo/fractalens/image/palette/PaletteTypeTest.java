package fr.univartois.butinfo.fractalens.image.palette;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.awt.Color;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link PaletteType}.
 * Each constant must create a palette of the right class, with the right colors.
 * The colors are checked on the first and last colors of the palettes, with a maximum
 * of 100 iterations, so that the expected results can be computed by hand.
 *
 * @version 0.1.0
 */
class PaletteTypeTest {

    /** The maximum number of iterations used in the tests. */
    private static final int MAX_ITERATIONS = 100;

    /**
     * Checks that the given palette gives the expected color for a number of iterations.
     *
     * @param palette The palette to test.
     * @param nbIteration The number of iterations performed.
     * @param expected The expected color.
     */
    private static void assertColor(IPalette palette, int nbIteration, Color expected) {
        assertEquals(expected, palette.getColor(nbIteration, MAX_ITERATIONS));
    }

    /** Test for {@link PaletteType#GRAYSCALE}: the type of the created palette. */
    @Test
    void testGrayScaleType() {
        assertInstanceOf(GrayScale.class, PaletteType.GRAYSCALE.getPalette());
    }

    /** Test for {@link PaletteType#GRAYSCALE}: white without iteration, black at the max. */
    @Test
    void testGrayScaleColors() {
        IPalette palette = PaletteType.GRAYSCALE.getPalette();

        assertColor(palette, 0, Color.WHITE);
        assertColor(palette, MAX_ITERATIONS, Color.BLACK);
    }

    /** Test for {@link PaletteType#FIXED}: the type of the created palette. */
    @Test
    void testFixedType() {
        assertInstanceOf(FixedPalette.class, PaletteType.FIXED.getPalette());
    }

    /** Test for {@link PaletteType#FIXED}: first and last colors of the list. */
    @Test
    void testFixedFirstAndLastColors() {
        IPalette palette = PaletteType.FIXED.getPalette();

        assertColor(palette, 0, Color.WHITE);
        assertColor(palette, MAX_ITERATIONS, Color.BLACK);
    }

    /**
     * Test for {@link PaletteType#FIXED}: the colors of the list are used at regular
     * intervals (with 5 colors, 25 iterations out of 100 give the second one, and 50
     * give the third one).
     */
    @Test
    void testFixedIntermediateColors() {
        IPalette palette = PaletteType.FIXED.getPalette();

        assertColor(palette, 25, new Color(150, 220, 255));
        assertColor(palette, 50, new Color(30, 120, 220));
    }

    /** Test for {@link PaletteType#GRADIENT}: the type of the created palette. */
    @Test
    void testGradientType() {
        assertInstanceOf(GradientPalette.class, PaletteType.GRADIENT.getPalette());
    }

    /** Test for {@link PaletteType#GRADIENT}: start color, then black at the max. */
    @Test
    void testGradientStartAndEndColors() {
        IPalette palette = PaletteType.GRADIENT.getPalette();

        assertColor(palette, 0, new Color(10, 60, 30));
        assertColor(palette, MAX_ITERATIONS, Color.BLACK);
    }

    /** Test for {@link PaletteType#GRADIENT}: half of the way between the two colors. */
    @Test
    void testGradientMiddleColor() {
        IPalette palette = PaletteType.GRADIENT.getPalette();

        assertColor(palette, 50, new Color(5, 30, 15));
    }

    /** Test for {@link PaletteType#getPalette()}: no constant gives a null palette. */
    @Test
    void testAllTypesGiveAPalette() {
        for (PaletteType type : PaletteType.values()) {
            assertNotNull(type.getPalette());
        }
    }

    /** Test for {@link PaletteType#getPalette()}: each call creates a new palette. */
    @Test
    void testGetPaletteCreatesNewInstances() {
        for (PaletteType type : PaletteType.values()) {
            assertNotSame(type.getPalette(), type.getPalette());
        }
    }

}