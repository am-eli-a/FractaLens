package fr.univartois.butinfo.fractalens.image.palette;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;

import org.junit.jupiter.api.Test;

/**
 * The MasqueDecoratorTest tests the {@link MaskDecorator} class.
 * The decorated palette is a fake one, so that these tests do not depend on the other
 * palettes of the project.
 *
 * @author Amelia Dufour-Lazrek
 *
 * @version 0.1.0
 */
class MaskDecoratorTest {

    /**
     * A palette that always gives the same color.
     */
    private static final IPalette PALETTE = (nbIteration, maxIteration) -> new Color(100, 150, 200);

    /**
     * A palette that encodes its parameters in the returned color, so that the tests can
     * check which values the decorator gave to it.
     */
    private static final IPalette ENCODING_PALETTE = (nbIteration, maxIteration) -> new Color(nbIteration, maxIteration, 0);

    /**
     * Test method for {@link MaskDecorator#getColor(int, int)} keeping the red and
     * the blue components.
     */
    @Test
    void testGetColorKeepingRedAndBlue() {
        MaskDecorator palette = new MaskDecorator(PALETTE, true, false, true);

        assertEquals(new Color(100, 0, 200), palette.getColor(5, 10));
    }

    /**
     * Test method for {@link MaskDecorator#getColor(int, int)} keeping only the green
     * component.
     */
    @Test
    void testGetColorKeepingGreenOnly() {
        MaskDecorator palette = new MaskDecorator(PALETTE, false, true, false);

        assertEquals(new Color(0, 150, 0), palette.getColor(5, 10));
    }

    /**
     * Test method for {@link MaskDecorator#getColor(int, int)} keeping no component.
     */
    @Test
    void testGetColorKeepingNothing() {
        MaskDecorator palette = new MaskDecorator(PALETTE, false, false, false);

        assertEquals(Color.BLACK, palette.getColor(5, 10));
    }

    /**
     * Test method for {@link MaskDecorator#getColor(int, int)} keeping all the
     * components.
     */
    @Test
    void testGetColorKeepingEverything() {
        MaskDecorator palette = new MaskDecorator(PALETTE, true, true, true);

        assertEquals(new Color(100, 150, 200), palette.getColor(5, 10));
    }

    /**
     * Test method for {@link MaskDecorator#getColor(int, int)}, checking that the
     * parameters are given to the decorated palette.
     */
    @Test
    void testGetColorGivesParametersToDecoratedPalette() {
        MaskDecorator palette = new MaskDecorator(ENCODING_PALETTE, true, true, true);

        assertEquals(new Color(5, 10, 0), palette.getColor(5, 10));
    }

}