/*
 * (même bloc de licence que dans les autres fichiers)
 */

package fr.univartois.butinfo.fractalens.image;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * The BufferedImageAdapterTest class tests the {@link BufferedImageAdapter} class.
 *
 * All the tests use an image of 6 × 21 pixels, which is not square, so that a swap
 * of the width and the height, or of the row and the column, would be detected.
 *
 * @author Baptiste Wagee
 *
 * @version 0.2.0
 */
class BufferedImageAdapterTest {

  /**
   * The width of the image used in the tests (in pixels).
   */
  private static final int WIDTH = 6;

  /**
   * The height of the image used in the tests (in pixels).
   */
  private static final int HEIGHT = 21;

  /**
   * The adapted image.
   */
  private BufferedImage image;

  /**
   * The adapter under test.
   */
  private BufferedImageAdapter adapter;

  /**
   * Creates a new image and its adapter before each test.
   */
  @BeforeEach
  void setUp() {
    image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    adapter = new BufferedImageAdapter(image);
  }

  /**
   * Test method for {@link BufferedImageAdapter#getWidth()}.
   */
  @Test
  void testGetWidth() {
    assertEquals(WIDTH, adapter.getWidth());
  }

  /**
   * Test method for {@link BufferedImageAdapter#getHeight()}.
   */
  @Test
  void testGetHeight() {
    assertEquals(HEIGHT, adapter.getHeight());
  }

  /**
   * Test method for {@link BufferedImageAdapter#setColor(int, int, Color)}.
   * The row and the column are different, so that a swap would be detected:
   * the pixel at row 20 and column 5 is the pixel (x = 5, y = 20) of the image.
   */
  @Test
  void testSetColor() {
    adapter.setColor(20, 5, Color.RED);

    assertEquals(Color.RED.getRGB(), image.getRGB(5, 20));
  }

  /**
   * Test method for {@link BufferedImageAdapter#setColor(int, int, Color)} on the
   * four corners of the image, to check that the whole image can be colored.
   */
  @Test
  void testSetColorOnCorners() {
    adapter.setColor(0, 0, Color.RED);
    adapter.setColor(0, WIDTH - 1, Color.GREEN);
    adapter.setColor(HEIGHT - 1, 0, Color.BLUE);
    adapter.setColor(HEIGHT - 1, WIDTH - 1, Color.WHITE);

    assertEquals(Color.RED.getRGB(), image.getRGB(0, 0));
    assertEquals(Color.GREEN.getRGB(), image.getRGB(WIDTH - 1, 0));
    assertEquals(Color.BLUE.getRGB(), image.getRGB(0, HEIGHT - 1));
    assertEquals(Color.WHITE.getRGB(), image.getRGB(WIDTH - 1, HEIGHT - 1));
  }

  /**
   * Test method for {@link BufferedImageAdapter#setColor(int, int, Color)} when a
   * pixel is colored twice: the last color must be kept.
   */
  @Test
  void testSetColorReplacesThePreviousColor() {
    adapter.setColor(3, 2, Color.RED);
    adapter.setColor(3, 2, Color.BLUE);

    assertEquals(Color.BLUE.getRGB(), image.getRGB(2, 3));
  }

  /**
   * Test method for {@link BufferedImageAdapter#asBufferedImage()}: it must give the
   * adapted image itself, and not a copy.
   */
  @Test
  void testAsBufferedImage() {
    assertSame(image, adapter.asBufferedImage());
  }

  /**
   * Test method for {@link BufferedImageAdapter#saveAs(String)}: the file must be
   * created.
   *
   * @param folder A temporary folder in which to save the image.
   *
   * @throws IOException If an I/O error occurs when writing the file.
   */
  @Test
  void testSaveAsCreatesTheFile(@TempDir Path folder) throws IOException {
    Path file = folder.resolve("image.png");

    adapter.saveAs(file.toString());

    assertTrue(Files.exists(file));
  }

  /**
   * Test method for {@link BufferedImageAdapter#saveAs(String)}: reading back the
   * saved file must give an image with the same size and the same colors.
   *
   * @param folder A temporary folder in which to save the image.
   *
   * @throws IOException If an I/O error occurs when writing or reading the file.
   */
  @Test
  void testSaveAsKeepsTheContent(@TempDir Path folder) throws IOException {
    Path file = folder.resolve("image.png");
    adapter.setColor(20, 5, Color.RED);
    adapter.setColor(0, 0, Color.BLUE);

    adapter.saveAs(file.toString());
    BufferedImage saved = ImageIO.read(file.toFile());

    assertNotNull(saved);
    assertEquals(WIDTH, saved.getWidth());
    assertEquals(HEIGHT, saved.getHeight());
    assertEquals(Color.RED.getRGB(), saved.getRGB(5, 20));
    assertEquals(Color.BLUE.getRGB(), saved.getRGB(0, 0));
    assertEquals(Color.BLACK.getRGB(), saved.getRGB(1, 1));
  }

}