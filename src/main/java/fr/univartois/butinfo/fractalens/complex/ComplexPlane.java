package fr.univartois.butinfo.fractalens.complex;


/**
 * The ComplexPlane class allows to convert the coordinates of a pixel (given by its
 * row and its column in an image) into the corresponding complex number.
 * The origin of the complex plane (0 + 0i) is located at the center of the image,
 * the real part increasing towards the right and the imaginary part increasing
 * towards the top.
 *
 *
 * @version 0.1.0
 */
public class ComplexPlane implements IComplexPlane {

    /**
     * The height of the image (in pixels).
     */
    private int height;

    /**
     * The width of the image (in pixels).
     */
    private int width;

    /**
     * Creates a new ComplexPlane.
     *
     * @param height The height of the image (in pixels).
     * @param width The width of the image (in pixels).
     */
    public ComplexPlane(int height, int width) {
        this.height = height;
        this.width = width;
    }

    /**
     * Gives the complex number associated with a pixel, so that the center of the
     * image is 0: the real part is {@code column - width / 2}, and the imaginary
     * part is {@code height / 2 - row}.
     *
     * @param row The row of the pixel.
     * @param column The column of the pixel.
     *
     * @return The complex number associated with the pixel.
     *
     * @see IComplexPlane#asComplex(int, int)
     */
    @Override
    public IComplex asComplex(int row, int column) {
        double realPart = column - (width / 2.0);
        double imaginaryPart = (height / 2.0) - row;
        return new Complex(realPart, imaginaryPart);
    }
}