package fr.univartois.butinfo.fractalens.complex;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link ZoomDecorator}.
 *
 * @author Nathan Salomé
 *
 * @version 0.1.0
 */
public class ZoomDecoratorTest {

  /**
   * Test method for {@link ZoomDecorator#asComplex(int, int)}.
   */
  @Test
  void testAsComplex() {
    IComplexPlane plane = new ComplexPlane(100, 200);
    IComplexPlane decorator = new ZoomDecorator(0.5, plane);

    assertEquals(new Complex(-50, 25), decorator.asComplex(0, 0));
  }
}