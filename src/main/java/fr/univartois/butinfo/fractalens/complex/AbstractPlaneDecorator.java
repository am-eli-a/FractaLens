package fr.univartois.butinfo.fractalens.complex;

/**
 * The AbstractPlaneDecorator class is the base class of the decorators of
 * {@link IComplexPlane}.
 *
 * It stores the decorated plane, so that its subclasses (such as the zoom and the
 * translation) only have to transform the complex number given by this plane.
 *
 *
 * @version 0.1.0
 */
public abstract class AbstractPlaneDecorator implements IComplexPlane{

  /**
   * The decorated complex plane.
   */
  private final IComplexPlane plane;

  /**
   * Creates a new AbstractPlaneDecorator.
   *
   * @param plane The complex plane to decorate.
   */
  protected AbstractPlaneDecorator(IComplexPlane plane) {
    this.plane = plane;
  }

  /**
   * Gives the decorated complex plane.
   *
   * @return The decorated plane.
   */
  protected IComplexPlane getPlane(){
    return plane;
  }
}