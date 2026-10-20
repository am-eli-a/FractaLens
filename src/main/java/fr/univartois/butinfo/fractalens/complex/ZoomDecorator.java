package fr.univartois.butinfo.fractalens.complex;

/**
 * Decorator of {@link IComplexPlane} that zooms on the plane
 * by multiplying the complex numbers by a factor.
 *
 * @author Nathan Salomé
 *
 * @version 0.1.0
 */
public class ZoomDecorator extends AbstractPlaneDecorator {
  /** The zoom factor. */
  private final double zoom;

  /**
   * Creates a new ZoomDecorator.
   * @param zoom The zoom factor.
   * @param plane describe the plane
   */
  public ZoomDecorator(double zoom, IComplexPlane plane) {
    super(plane);
    this.zoom = zoom;
  }

  /**
   * @param row The row of the pixel.
   * @param column The column of the pixel.
   * @return The complex number of the pixel, multiplied by the zoom factor.
   */
  @Override
  public IComplex asComplex(int row, int column) {

    return getPlane().asComplex(row, column).multiply(zoom);
  }
}