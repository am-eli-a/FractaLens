package fr.univartois.butinfo.fractalens.complex;

public abstract class AbstractPlaneDecorator implements IComplexPlane{
  private final IComplexPlane plane;

  public AbstractPlaneDecorator(IComplexPlane plane) {
    this.plane = plane;
  }

  protected IComplexPlane getPlane(){
    return plane;
  }
}
