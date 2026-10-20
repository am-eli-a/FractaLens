package fr.univartois.butinfo.fractalens.complex;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link TranslationDecorator}.
 *
 * @author Nathan Salomé
 *
 * @version 0.1.0
 */
public class TranslationDecoratorTest {

  /**
   * Test method for {@link TranslationDecorator#asComplex(int, int)}.
   * The pixel (0, 0) gives -100 + 50i, translated by -50 + 25i.
   */
  @Test
  void testAsComplex() {
    IComplexPlane plane = new ComplexPlane(100, 200);
    IComplexPlane decorator = new TranslationDecorator(new Complex(-50, 25), plane);

    assertEquals(new Complex(-150, 75), decorator.asComplex(0, 0));
  }

  /**
   * Test method for {@link TranslationDecorator#asComplex(int, int)}.
   * The middle pixel gives 0 + 0i, so after translation it must be the center.
   */
  @Test
  void testAsComplexCenter() {
    IComplexPlane plane = new ComplexPlane(100, 200);
    IComplexPlane decorator = new TranslationDecorator(new Complex(-50, 25), plane);

    assertEquals(new Complex(-50, 25), decorator.asComplex(50, 100));
  }
}