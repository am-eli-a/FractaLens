package fr.univartois.butinfo.fractalens.complex;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class ZoomDecoratorTest {

  @Test
  void testAsComplex(){
    IComplexPlane plane = new ComplexPlane(100, 200);
    IComplexPlane decorator = new ZoomDecorator(0.5, plane);

    assertEquals(new Complex(-50, 25), decorator.asComplex(0, 0));
  }
}
