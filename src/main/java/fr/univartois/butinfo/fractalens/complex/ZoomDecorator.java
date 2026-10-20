package fr.univartois.butinfo.fractalens.complex;

/**
 * Decorator of {@link IComplexPlane} that zooms on the plane
 * by multiplying the complex numbers by a factor.
 *
 * @author Nathan Salomé
 *
 * @version 0.1.0
 */
public class ZoomDecorator implements IComplexPlane {
  /** The zoom factor. */
  private final double zoom;
  /** The decorated plane. */
  private final IComplexPlane plane;

  /**
   * Creates a new ZoomDecorator.
   *
   * @param zoom The zoom factor.
   * @param plane The plane to decorate.
   */
  public ZoomDecorator(double zoom, IComplexPlane plane) {
    this.zoom = zoom;
    this.plane = plane;
  }

  /**
   * @param row The row of the pixel.
   * @param column The column of the pixel.
   * @return The complex number of the pixel, multiplied by the zoom factor.
   */
  @Override
  public IComplex asComplex(int row, int column) {
    return plane.asComplex(row, column).multiply(zoom);
  }
}