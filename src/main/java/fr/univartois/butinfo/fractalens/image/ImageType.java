package fr.univartois.butinfo.fractalens.image;

/**
 * The ImageType enumeration lists the kinds of images that the application can
 * generate.
 *
 * @author Amelia Dufour-Lazrek
 *
 * @version 0.1.0
 */
public enum ImageType {

    /**
     * A single image of a fractal.
     */
    SINGLE,

    /**
     * An animation zooming on a point of a fractal.
     */
    ZOOM,

    /**
     * An animation rotating the constant of Julia's sequence.
     */
    ROTATION;
}