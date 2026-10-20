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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * The BufferedImageAdapterTest class tests the {@link BufferedImageAdapter} class.
 *
 * @author Baptiste Wagee
 *
 * @version 0.1.0
 */
class BufferedImageAdapterTest {

  /**
   * Test method for {@link BufferedImageAdapter#getWidth()} and
   * {@link BufferedImageAdapter#getHeight()}.
   */
  @Test
  void testGetWidthAndHeight() {
    BufferedImage image = new BufferedImage(6, 21, BufferedImage.TYPE_INT_RGB);
    BufferedImageAdapter adapter = new BufferedImageAdapter(image);
    assertEquals(6, adapter.getWidth());
    assertEquals(21, adapter.getHeight());
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
  @Test
  void testSetColor() {
    BufferedImage image = new BufferedImage(6, 21, BufferedImage.TYPE_INT_RGB);
    BufferedImageAdapter adapter = new BufferedImageAdapter(image);
    adapter.setColor(20, 5, Color.RED);
    assertEquals(Color.RED.getRGB(), image.getRGB(5, 20));
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
  @Test
  void testSaveAs(@TempDir Path folder) throws IOException {
    BufferedImage image = new BufferedImage(5, 20, BufferedImage.TYPE_INT_RGB);
    BufferedImageAdapter adapter = new BufferedImageAdapter(image);
    Path file = folder.resolve("Image.png");
    adapter.saveAs(file.toString());
    assertTrue(Files.exists(Path.of(file.toString())));
  }

}