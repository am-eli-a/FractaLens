package fr.univartois.butinfo.fractalens.generator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.function.BiFunction;

import org.junit.jupiter.api.Test;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.IComplex;
import fr.univartois.butinfo.fractalens.complex.IComplexPlane;
import fr.univartois.butinfo.fractalens.complex.sequence.ComplexSequenceType;
import fr.univartois.butinfo.fractalens.complex.sequence.IComplexSequence;
import fr.univartois.butinfo.fractalens.image.IFractalImage;
import fr.univartois.butinfo.fractalens.image.palette.GrayScale;
import fr.univartois.butinfo.fractalens.image.palette.IPalette;
import fr.univartois.butinfo.fractalens.image.palette.PaletteType;

/**
 * The FractalGeneratorBuilderTest class tests the {@link FractalGeneratorBuilder}
 * class.
 *
 * @author Baptiste Wagee
 *
 * @version 0.1.0
 */
class FractalGeneratorBuilderTest {

  /**
   * Tests that every configuration method returns the builder itself, so that
   * the calls can be chained.
   */
  @Test
  void testEachMethodReturnsTheBuilder() {
    FractalGeneratorBuilder builder = new FractalGeneratorBuilder();

    assertSame(builder, builder.usingSequence(ComplexSequenceType.MANDELBROT));
    assertSame(builder, builder.usingSequence(ComplexSequenceType.MANDELBROT.getSequenceFactory()));
    assertSame(builder, builder.withSize(800, 600));
    assertSame(builder, builder.zoomedBy(0.001));
    assertSame(builder, builder.centeredAt(new Complex(-0.75, 0)));
    assertSame(builder, builder.withMaxIterations(250));
    assertSame(builder, builder.coloredWith(PaletteType.GRAYSCALE));
    assertSame(builder, builder.coloredWith(new GrayScale()));
    assertSame(builder, builder.savedAs("test.png"));
  }

  /**
   * Tests the default values used when nothing is configured.
   */
  @Test
  void testDefaultValues() {
    FractalGeneratorBuilder builder = new FractalGeneratorBuilder();

    assertEquals(64, builder.getMaxIterations());
    assertEquals("fractal.png", builder.getOutputFile());
    assertEquals(800, builder.getImage().getWidth());
    assertEquals(400, builder.getImage().getHeight());
    assertInstanceOf(GrayScale.class, builder.getPalette());
    assertNotNull(builder.getSequenceFactory());
    // The pixel in the middle of the image is the default center (0 + 0i).
    assertEquals(new Complex(0, 0), builder.getPlane().asComplex(200, 400));
  }

  /**
   * Tests that the simple values are stored by the builder.
   */
  @Test
  void testValuesAreStored() {
    IPalette palette = new GrayScale();
    BiFunction<IComplex, Integer, IComplexSequence> sequence =
            ComplexSequenceType.JULIA.getSequenceFactory();

    FractalGeneratorBuilder builder = new FractalGeneratorBuilder()
            .usingSequence(sequence)
            .withMaxIterations(250)
            .coloredWith(palette)
            .savedAs("mandelbrot.png");

    assertSame(sequence, builder.getSequenceFactory());
    assertEquals(250, builder.getMaxIterations());
    assertSame(palette, builder.getPalette());
    assertEquals("mandelbrot.png", builder.getOutputFile());
  }

  /**
   * Tests that the values given through the enumerations are used.
   */
  @Test
  void testEnumerationsAreUsed() {
    FractalGeneratorBuilder builder = new FractalGeneratorBuilder()
            .usingSequence(ComplexSequenceType.MANDELBROT)
            .coloredWith(PaletteType.GRAYSCALE);

    assertSame(ComplexSequenceType.MANDELBROT.getSequenceFactory(), builder.getSequenceFactory());
    assertInstanceOf(GrayScale.class, builder.getPalette());
  }

  /**
   * Tests that the image is created with the configured size.
   * The image is not square, so that a swap of width and height would be detected.
   */
  @Test
  void testImageHasTheConfiguredSize() {
    IFractalImage image = new FractalGeneratorBuilder().withSize(800, 600).getImage();

    assertEquals(800, image.getWidth());
    assertEquals(600, image.getHeight());
  }

  /**
   * Tests that the plane takes into account the size, the zoom and the center.
   * The middle pixel (row 300, column 400) must be the center, and the pixel
   * 100 columns to the right must be center + 100 * zoom = (1 + 50) + 2i.
   */
  @Test
  void testPlaneUsesSizeZoomAndCenter() {
    IComplexPlane plane = new FractalGeneratorBuilder()
            .withSize(800, 600)
            .zoomedBy(0.5)
            .centeredAt(new Complex(1, 2))
            .getPlane();

    assertEquals(new Complex(1, 2), plane.asComplex(300, 400));
    assertEquals(new Complex(51, 2), plane.asComplex(300, 500));
  }

  /**
   * Tests that changing the size updates both the image and the plane.
   */
  @Test
  void testChangingTheSizeUpdatesImageAndPlane() {
    FractalGeneratorBuilder builder = new FractalGeneratorBuilder()
            .withSize(800, 600)
            .zoomedBy(0.5)
            .centeredAt(new Complex(1, 2))
            .withSize(200, 100);

    assertEquals(200, builder.getImage().getWidth());
    assertEquals(100, builder.getImage().getHeight());
    // The middle of the new image (row 50, column 100) is still the center.
    assertEquals(new Complex(1, 2), builder.getPlane().asComplex(50, 100));
  }

  /**
   * Tests that changing the number of iterations keeps the last value.
   */
  @Test
  void testChangingTheNumberOfIterations() {
    FractalGeneratorBuilder builder = new FractalGeneratorBuilder()
            .withMaxIterations(100)
            .withMaxIterations(250);

    assertEquals(250, builder.getMaxIterations());
  }

  /**
   * Tests that the builder creates a generator.
   */
  @Test
  void testBuildCreatesAGenerator() {
    assertNotNull(new FractalGeneratorBuilder().build());
  }

}