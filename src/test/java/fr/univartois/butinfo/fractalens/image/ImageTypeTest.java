package fr.univartois.butinfo.fractalens.image;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link ImageType}.
 *
 * @author Nathan Salomé
 *
 * @version 0.1.0
 */
class ImageTypeTest {

  /**
   * Test method for {@link ImageType#values()}: the three types of images
   * must be available, in this order.
   */
  @Test
  void testValues() {
    assertArrayEquals(
            new ImageType[] {ImageType.SINGLE, ImageType.ZOOM, ImageType.ROTATION},
            ImageType.values());
  }

  /**
   * Test method for {@link ImageType#valueOf(String)}: the names given on the
   * command line must give the right constants.
   */
  @Test
  void testValueOf() {
    assertEquals(ImageType.SINGLE, ImageType.valueOf("SINGLE"));
    assertEquals(ImageType.ZOOM, ImageType.valueOf("ZOOM"));
    assertEquals(ImageType.ROTATION, ImageType.valueOf("ROTATION"));
  }

}