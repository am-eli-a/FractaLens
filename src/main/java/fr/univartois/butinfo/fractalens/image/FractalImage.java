package fr.univartois.butinfo.fractalens.image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class FractalImage implements IFractalImage {

    private BufferedImage image;
    private int height;
    private int width;

    public FractalImage(int height, int width) {
        this.height = height;
        this.width = width;
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return  width;
    }

    @Override
    public void setColor(int row, int column, Color color) {

    }

    @Override
    public void saveAs(String path) throws IOException {

    }
}
