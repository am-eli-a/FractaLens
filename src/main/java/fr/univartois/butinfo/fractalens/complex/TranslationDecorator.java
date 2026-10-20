package fr.univartois.butinfo.fractalens.complex;

/**
 * Decorator of {@link IComplexPlane} that translates the plane
 * by adding a complex number (the new center) to the complex numbers.
 *
 * @author Nathan Salomé
 *
 * @version 0.1.0
 */
public class TranslationDecorator extends AbstractPlaneDecorator {

  /** The new center of the plane. */
  private final IComplex center;

  /**
   * Creates a new TranslationDecorator.
   *
   * @param center The new center of the plane.
   * @param plane The plane to decorate.
   */
  public TranslationDecorator(IComplex center, IComplexPlane plane) {
    super(plane);
    this.center = center;
  }

  /**
   * @param row The row of the pixel.
   * @param column The column of the pixel.
   * @return The complex number of the pixel, translated by the center.
   */
  @Override
  public IComplex asComplex(int row, int column) {
    return getPlane().asComplex(row, column).add(center);
  }
}