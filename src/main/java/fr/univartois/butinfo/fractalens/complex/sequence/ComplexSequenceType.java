package fr.univartois.butinfo.fractalens.complex.sequence;

import fr.univartois.butinfo.fractalens.complex.IComplex;

import java.util.function.BiFunction;

public enum ComplexSequenceType {
  JULIA, MANDELBROT, BURNING_SHIP, GENERALIZED_BURNING_SHIP, TRICORN, GENERALIZED_TRICORN, MULTI_JULIA, MULTIBROT, CUBIC_JULIA, MIXED_JULIA_MANDELBROT;

  private final BiFunction<IComplex, Integer, IComplexSequence> sequenceFactory;

  ComplexSequenceType(BiFunction<IComplex, Integer, IComplexSequence> sequenceFactory) {
    this.sequenceFactory = sequenceFactory;
  }


}