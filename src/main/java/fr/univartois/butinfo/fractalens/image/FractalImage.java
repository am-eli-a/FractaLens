package fr.univartois.butinfo.fractalens.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The FractalImage class is an adapter that allows to manipulate a
 * {@link BufferedImage} through the {@link IFractalImage} interface.
 * Pixels are designated by their row and column, whereas a {@link BufferedImage}
 * uses (x, y) coordinates: this class takes care of the conversion.
 *
 * @author Amelia Dufour-Lazrek
 *
 * @version 0.1.0
 */
public class FractalImage implements IFractalImage {

    private final BufferedImage image;

    public FractalImage(int height, int width) {
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    @Override
    public int getHeight() {
        return image.getHeight();
    }

    @Override
    public int getWidth() {
        return image.getWidth();
    }

    @Override
    public void setColor(int row, int column, Color color) {
        image.setRGB(column, row, color.getRGB());
    }

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
