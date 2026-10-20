package fr.univartois.butinfo.fractalens.image.palette;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;

import org.junit.jupiter.api.Test;

/**
 * The InversionDecoratorTest tests the {@link InversionDecorator} class.
 * The decorated palette is a fake one, so that these tests do not depend on the other
 * palettes of the project.
 *
 * @author Amelia Dufour-Lazrek
 *
 * @version 0.1.0
 */
class InversionDecoratorTest {

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
     * Test method for {@link InversionDecorator#getColor(int, int)}.
     */
    @Test
    void testGetColor() {
        InversionDecorator palette = new InversionDecorator(PALETTE);

        assertEquals(new Color(155, 105, 55), palette.getColor(5, 10));
    }

    /**
     * Test method for {@link InversionDecorator#getColor(int, int)} on white, which must
     * become black.
     */
    @Test
    void testGetColorOnWhite() {
        InversionDecorator palette = new InversionDecorator((nb, max) -> Color.WHITE);

        assertEquals(Color.BLACK, palette.getColor(5, 10));
    }

    /**
     * Test method for {@link InversionDecorator#getColor(int, int)} on black, which must
     * become white.
     */
    @Test
    void testGetColorOnBlack() {
        InversionDecorator palette = new InversionDecorator((nb, max) -> Color.BLACK);

        assertEquals(Color.WHITE, palette.getColor(5, 10));
    }

    /**
     * Test method for {@link InversionDecorator#getColor(int, int)} when the inversion
     * is applied twice, which must give back the original color.
     */
    @Test
    void testGetColorWithDoubleInversion() {
        InversionDecorator palette = new InversionDecorator(new InversionDecorator(PALETTE));

        assertEquals(new Color(100, 150, 200), palette.getColor(5, 10));
    }

    /**
     * Test method for {@link InversionDecorator#getColor(int, int)}, checking that the
     * parameters are given to the decorated palette.
     */
    @Test
    void testGetColorGivesParametersToDecoratedPalette() {
        InversionDecorator palette = new InversionDecorator(ENCODING_PALETTE);

        assertEquals(new Color(250, 245, 255), palette.getColor(5, 10));
    }

}