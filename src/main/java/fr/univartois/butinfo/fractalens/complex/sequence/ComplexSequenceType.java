package fr.univartois.butinfo.fractalens.complex.sequence;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.IComplex;

import java.util.function.BiFunction;

public enum ComplexSequenceType {
  JULIA((z0, max) -> new JuliaSequence(z0, max, new Complex(-0.4, 0.6))),

  MANDELBROT((z0, max) -> ComplexSequences.mandelbrot(z0, max)),

  BURNING_SHIP((z0, max) -> ComplexSequences.burningShip(z0, max, new Complex(-0.4, 0.6))),

  GENERALIZED_BURNING_SHIP((z0, max) -> ComplexSequences.generalizedBurningShip(z0, max, new Complex(-0.4, 0.6), 3)),

  TRICORN((z0, max) -> ComplexSequences.tricorn(z0, max, new Complex(-0.4, 0.6))),

  GENERALIZED_TRICORN((z0, max) -> ComplexSequences.tricornGeneral(z0, max, new Complex(-0.4, 0.6), 3)),

  MULTI_JULIA((z0, max) -> ComplexSequences.multiJulia(z0, max, new Complex(-0.4, 0.6), 3)),

  MULTIBROT((z0, max) -> ComplexSequences.multibrot(z0, max, 3)),

  CUBIC_JULIA((z0, max) -> ComplexSequences.cubicJulia(z0, max, new Complex(-0.4, 0.6))),

  MIXED_JULIA_MANDELBROT((z0, max) -> ComplexSequences.mixedJuliaMandelbrot(z0, max, new Complex(-0.4, 0.6)));

  private final BiFunction<IComplex, Integer, IComplexSequence> sequenceFactory;

  ComplexSequenceType(BiFunction<IComplex, Integer, IComplexSequence> sequenceFactory) {
    this.sequenceFactory = sequenceFactory;
  }

  public BiFunction<IComplex, Integer, IComplexSequence> getSequenceFactory(){
    return sequenceFactory;
  }
}