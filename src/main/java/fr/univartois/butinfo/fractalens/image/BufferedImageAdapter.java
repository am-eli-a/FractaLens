/*
 * FractaLens - A Fractal Generator Library Developed in Lens.
 * Copyright (c) 2022-2026 Romain Wallon - Université d'Artois.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package fr.univartois.butinfo.fractalens.image;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The BufferedImageAdapter class is an adapter allowing to use a
 * {@link BufferedImage} through the {@link IFractalImage} interface.
 *
 * @author Baptiste Wagee
 *
 * @version 0.1.0
 */
public class BufferedImageAdapter implements IFractalImage {

  /**
   * The adapted image, on which the fractal is drawn.
   */
  private final BufferedImage image;


  /**
   * Creates a new BufferedImageAdapter.
   *
   * @param image The image to adapt.
   */
  public BufferedImageAdapter(BufferedImage image) {
    this.image = image;
  }


  /*
   * (non-Javadoc)
   *
   * @see fr.univartois.butinfo.fractalens.image.IFractalImage#getHeight()
   */
  @Override
  public int getHeight() {
    return image.getHeight();
  }

  /*
   * (non-Javadoc)
   *
   * @see fr.univartois.butinfo.fractalens.image.IFractalImage#getWidth()
   */
  @Override
  public int getWidth() {
    return image.getWidth();
  }

  /**
   * Updates the color of the pixel at the given position in this image.
   * Note that {@link BufferedImage} uses (x, y) coordinates, i.e.
   * (column, row), so the coordinates are swapped here.
   *
   * @param row The row of the pixel to update.
   * @param column The column of the pixel to update.
   * @param color The new color for the pixel.
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
   * @throws IOException If an I/O error occurs when writing the file.
   *
   * @see IFractalImage#saveAs(String)
   */
  @Override
  public void saveAs(String path) throws IOException {
    ImageIO.write(image, "png", new File(path));
  }

  @Override
  public BufferedImage asBufferedImage() {
    return image;
  }
}
