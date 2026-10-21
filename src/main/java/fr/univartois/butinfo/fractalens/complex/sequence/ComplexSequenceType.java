package fr.univartois.butinfo.fractalens.complex.sequence;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.IComplex;

import java.util.function.BiFunction;

/**
 * The ComplexSequenceType enumeration names the complex sequences available
 * in the library. Each constant gives a function creating its sequence from
 * the first term and the maximum number of iterations, with default values
 * for the constant c (-0.4 + 0.6i) and the power p (3) when needed.
 *
 * @author Nathan Salomé
 *
 * @version 0.1.0
 */
public enum ComplexSequenceType {

  /** The Julia sequence: z^2 + c, with c = -0.4 + 0.6i. */
  JULIA((z0, max) -> new JuliaSequence(z0, max, new Complex(-0.4, 0.6))),

  /** The Mandelbrot sequence: z^2 + z0. */
  MANDELBROT(ComplexSequences::mandelbrot),

  /** The Burning Ship sequence: (|Re z| + i|Im z|)^2 + c, with c = -0.4 + 0.6i. */
  BURNING_SHIP((z0, max) -> ComplexSequences.burningShip(z0, max, new Complex(-0.4, 0.6))),

  /** The generalized Burning Ship sequence: (|Re z| + i|Im z|)^p + c, with c = -0.4 + 0.6i and p = 3. */
  GENERALIZED_BURNING_SHIP((z0, max) -> ComplexSequences.generalizedBurningShip(z0, max, new Complex(-0.4, 0.6), 3)),

  /** The Tricorn sequence: conj(z)^2 + c, with c = -0.4 + 0.6i. */
  TRICORN((z0, max) -> ComplexSequences.tricorn(z0, max, new Complex(-0.4, 0.6))),

  /** The generalized Tricorn sequence: conj(z)^p + c, with c = -0.4 + 0.6i and p = 3. */
  GENERALIZED_TRICORN((z0, max) -> ComplexSequences.tricornGeneral(z0, max, new Complex(-0.4, 0.6), 3)),

  /** The generalized Julia sequence: z^p + c, with c = -0.4 + 0.6i and p = 3. */
  MULTI_JULIA((z0, max) -> ComplexSequences.multiJulia(z0, max, new Complex(-0.4, 0.6), 3)),

  /** The generalized Mandelbrot sequence: z^p + z0, with p = 3. */
  MULTIBROT((z0, max) -> ComplexSequences.multibrot(z0, max, 3)),

  /** The cubic Julia sequence: z^3 + z^2 + c, with c = -0.4 + 0.6i. */
  CUBIC_JULIA((z0, max) -> ComplexSequences.cubicJulia(z0, max, new Complex(-0.4, 0.6))),

  /** The mixed Julia-Mandelbrot sequence: z^2 + c * z0, with c = -0.4 + 0.6i. */
  MIXED_JULIA_MANDELBROT((z0, max) -> ComplexSequences.mixedJuliaMandelbrot(z0, max, new Complex(-0.4, 0.6)));

  /**
   * The function creating the sequence from its first term and its maximum
   * number of iterations.
   */
  private final BiFunction<IComplex, Integer, IComplexSequence> sequenceFactory;

  /**
   * Creates a new ComplexSequenceType.
   *
   * @param sequenceFactory The function creating the sequence of this type.
   */
  ComplexSequenceType(BiFunction<IComplex, Integer, IComplexSequence> sequenceFactory) {
    this.sequenceFactory = sequenceFactory;
  }

  /**
   * Gives the function creating the sequence of this type.
   *
   * @return The function creating the sequence from its first term and its
   *         maximum number of iterations.
   */
  public BiFunction<IComplex, Integer, IComplexSequence> getSequenceFactory() {
    return sequenceFactory;
  }

}