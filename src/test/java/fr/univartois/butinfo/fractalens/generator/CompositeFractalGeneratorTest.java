package fr.univartois.butinfo.fractalens.generator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import fr.univartois.butinfo.fractalens.image.BufferedImageAdapter;
import fr.univartois.butinfo.fractalens.image.IFractalImage;

/**
 * Tests for {@link CompositeFractalGenerator}.
 *
 * @author Nathan Salomé
 *
 * @version 0.1.0
 */
class CompositeFractalGeneratorTest {

  /** A temporary folder, deleted after the tests, where the GIF files are saved. */
  @TempDir
  Path tempDir;

  /**
   * A fake generator, which counts how many times it is asked to generate
   * its image, and always returns the same small image.
   */
  private static class FakeGenerator implements IFractalGenerator {

    /** The number of calls to {@link #generateImage()}. */
    private int calls;

    /** The image returned by this generator. */
    private final IFractalImage image =
            new BufferedImageAdapter(new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB));

    @Override
    public IFractalImage generateImage() {
      calls++;
      return image;
    }

    @Override
    public Color computePixelColor(int row, int column) {
      return Color.BLACK;
    }

    @Override
    public void saveImage() {
      // Nothing to save for a fake generator.
    }
  }

  /**
   * Test method for {@link CompositeFractalGenerator#generateImage()}:
   * each generator must be called exactly once, and the last image is returned.
   */
  @Test
  void testGenerateImageCallsAllGenerators() {
    FakeGenerator first = new FakeGenerator();
    FakeGenerator second = new FakeGenerator();
    FakeGenerator third = new FakeGenerator();
    CompositeFractalGenerator composite = new CompositeFractalGenerator(
            List.of(first, second, third), 100, tempDir.resolve("test.gif").toString());

    IFractalImage result = composite.generateImage();

    assertEquals(1, first.calls);
    assertEquals(1, second.calls);
    assertEquals(1, third.calls);
    assertSame(third.image, result);
  }

  /**
   * Test method for {@link CompositeFractalGenerator#generateImage()}
   * with no generator: there is no image to return.
   */
  @Test
  void testGenerateImageWithoutGenerator() {
    CompositeFractalGenerator composite = new CompositeFractalGenerator(
            List.of(), 100, tempDir.resolve("test.gif").toString());

    assertNull(composite.generateImage());
  }

  /**
   * Test method for {@link CompositeFractalGenerator#saveImage()}:
   * the GIF file must be created and not empty.
   *
   * @throws IOException If the size of the file cannot be read.
   */
  @Test
  void testSaveImageCreatesGif() throws IOException {
    Path output = tempDir.resolve("animation.gif");
    CompositeFractalGenerator composite = new CompositeFractalGenerator(
            List.of(new FakeGenerator(), new FakeGenerator()), 100, output.toString());

    composite.generateImage();
    composite.saveImage();

    assertTrue(Files.exists(output));
    assertTrue(Files.size(output) > 0);
  }

  /**
   * Test method for {@link CompositeFractalGenerator#computePixelColor(int, int)}:
   * a composite cannot compute a single pixel color.
   */
  @Test
  void testComputePixelColorIsUnsupported() {
    CompositeFractalGenerator composite = new CompositeFractalGenerator(
            List.of(new FakeGenerator()), 100, tempDir.resolve("test.gif").toString());

    assertThrows(UnsupportedOperationException.class,
            () -> composite.computePixelColor(0, 0));
  }

}