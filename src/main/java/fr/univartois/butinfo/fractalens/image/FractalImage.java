package fr.univartois.butinfo.fractalens.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
}
