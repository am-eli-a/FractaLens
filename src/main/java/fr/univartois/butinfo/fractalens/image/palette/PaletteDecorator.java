package fr.univartois.butinfo.fractalens.image.palette;

/**
 * The PaletteDecorator class is the base class of the decorators of palettes.
 * A decorator is itself a palette, which relies on another palette (the decorated one)
 * to compute a color, and then modifies this color.
 * Subclasses only have to define how the color given by the decorated palette is
 * transformed.
 *
 * @author Amelia Dufour-Lazrek
 *
 * @version 0.1.0
 */
public abstract class PaletteDecorator implements IPalette {

    /**
     * The palette decorated by this decorator.
     */
    protected final IPalette palette;

    /**
     * Creates a new PaletteDecorator.
     *
     * @param palette The palette to decorate.
     */
    public PaletteDecorator(IPalette palette) {
        this.palette = palette;
    }

}