package fr.univartois.butinfo.fractalens.image;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The FractalImage class is an adapter that allows to manipulate a
 * {@link BufferedImage} through the {@link IFractalImage} interface.
 * Pixels are designated by their row and column, whereas a {@link BufferedImage}
 * uses (x, y) coordinates: this class takes care of the conversion.
 *
 *
 * @version 0.1.0
 */
public class FractalImage implements IFractalImage {

    /**
     * The adapted image.
     */
    private final BufferedImage image;

    /**
     * Creates a new FractalImage, with a new image of the given size.
     *
     * @param height The height of the image (in pixels).
     * @param width The width of the image (in pixels).
     */
    public FractalImage(int height, int width) {
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    /**
     * Gives the height of this image.
     *
     * @return The height of this image (in pixels).
     *
     * @see IFractalImage#getHeight()
     */
    @Override
    public int getHeight() {
        return image.getHeight();
    }

    /**
     * Gives the width of this image.
     *
     * @return The width of this image (in pixels).
     *
     * @see IFractalImage#getWidth()
     */
    @Override
    public int getWidth() {
        return image.getWidth();
    }

    /**
     * Updates the color of a pixel. The coordinates are swapped, since
     * {@link BufferedImage} uses (x, y) coordinates, that is (column, row).
     *
     * @param row The row of the pixel to update.
     * @param column The column of the pixel to update.
     * @param color The new color of the pixel.
     *
     * @see IFractalImage#setColor(int, int, Color)
     */
    @Override
    public void setColor(int row, int column, Color color) {
        image.setRGB(column, row, color.getRGB());
    }

    /**
     * Saves this image in a file, using the PNG format.
     *
     * @param path The path of the file in which to save this image.
     *
     * @throws IOException If the image cannot be written in the file.
     *
     * @see IFractalImage#saveAs(String)
     */
    @Override
    public void saveAs(String path) throws IOException {
        if (!ImageIO.write(image, "png", new File(path))) {
            throw new IOException("Impossible de sauvegarder");
        }
    }

    /**
     * Gives the {@link BufferedImage} adapted by this image.
     *
     * @return The adapted image.
     */
    @Override
    public BufferedImage asBufferedImage() {
        return image;
    }
}