package fr.univartois.butinfo.fractalens.image.palette;

public abstract class PaletteDecorator implements IPalette {
    protected final IPalette palette;

    public PaletteDecorator(IPalette palette) {
        this.palette = palette;
    }
}
