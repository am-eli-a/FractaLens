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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * The FractalImageTest tests the {@link FractalImage} class.
 * As the adapted {@link BufferedImage} is not exposed, the pixels are checked by saving
 * the image in a temporary directory and by reading the saved file back.
 *
 * @author [Ton nom]
 *
 * @version 0.1.0
 */
class FractalImageTest {

    /**
     * The height of the image used in the tests.
     */
    private static final int HEIGHT = 3;

    /**
     * The width of the image used in the tests.
     */
    private static final int WIDTH = 4;

    /**
     * The temporary directory in which the images are saved.
     */
    @TempDir
    Path directory;

    /**
     * The image tested.
     */
    private FractalImage image;

    /**
     * Creates the image to test before each test.
     */
    @BeforeEach
    void setUp() {
        image = new FractalImage(HEIGHT, WIDTH);
    }

    /**
     * Saves the tested image and reads it back.
     *
     * @return The image read from the saved file.
     *
     * @throws IOException If the image cannot be saved or read.
     */
    private BufferedImage saveAndReload() throws IOException {
        File file = directory.resolve("fractale.png").toFile();
        image.saveAs(file.getPath());
        BufferedImage result = ImageIO.read(file);
        assertNotNull(result);
        return result;
    }

    /**
     * Test method for {@link FractalImage#getHeight()}.
     */
    @Test
    void testGetHeight() {
        assertEquals(HEIGHT, image.getHeight());
    }

    /**
     * Test method for {@link FractalImage#getWidth()}.
     */
    @Test
    void testGetWidth() {
        assertEquals(WIDTH, image.getWidth());
    }

    /**
     * Test method for {@link FractalImage#setColor(int, int, Color)}, checking that the
     * row is used as the y coordinate and the column as the x coordinate.
     *
     * @throws IOException If the image cannot be saved or read.
     */
    @Test
    void testSetColorUsesRowAndColumn() throws IOException {
        image.setColor(1, 2, Color.RED);

        BufferedImage result = saveAndReload();

        assertEquals(Color.RED.getRGB(), result.getRGB(2, 1));
        assertEquals(Color.BLACK.getRGB(), result.getRGB(1, 2));
    }

    /**
     * Test method for {@link FractalImage#setColor(int, int, Color)}, checking that only
     * the requested pixel is modified.
     *
     * @throws IOException If the image cannot be saved or read.
     */
    @Test
    void testSetColorOnlyModifiesOnePixel() throws IOException {
        image.setColor(1, 2, Color.RED);

        BufferedImage result = saveAndReload();

        for (int row = 0; row < HEIGHT; row++) {
            for (int column = 0; column < WIDTH; column++) {
                if (row != 1 || column != 2) {
                    assertEquals(Color.BLACK.getRGB(), result.getRGB(column, row));
                }
            }
        }
    }

    /**
     * Test method for {@link FractalImage#setColor(int, int, Color)} on the corners of
     * the image.
     *
     * @throws IOException If the image cannot be saved or read.
     */
    @Test
    void testSetColorOnCorners() throws IOException {
        image.setColor(0, 0, Color.RED);
        image.setColor(HEIGHT - 1, WIDTH - 1, Color.GREEN);

        BufferedImage result = saveAndReload();

        assertEquals(Color.RED.getRGB(), result.getRGB(0, 0));
        assertEquals(Color.GREEN.getRGB(), result.getRGB(WIDTH - 1, HEIGHT - 1));
    }

    /**
     * Test method for {@link FractalImage#saveAs(String)}, checking the file and the
     * dimensions of the saved image.
     *
     * @throws IOException If the image cannot be saved or read.
     */
    @Test
    void testSaveAs() throws IOException {
        File file = directory.resolve("fractale.png").toFile();

        image.saveAs(file.getPath());

        assertTrue(file.exists());
        BufferedImage result = ImageIO.read(file);
        assertEquals(HEIGHT, result.getHeight());
        assertEquals(WIDTH, result.getWidth());
    }

    /**
     * Test method for {@link FractalImage#saveAs(String)} when the file cannot be
     * written.
     */
    @Test
    void testSaveAsWhenAnErrorOccurs() {
        String path = directory.resolve("missing").resolve("fractale.png").toString();

        assertThrows(IOException.class, () -> image.saveAs(path));
    }

}