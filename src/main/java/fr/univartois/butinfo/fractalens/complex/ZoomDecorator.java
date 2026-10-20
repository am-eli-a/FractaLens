package fr.univartois.butinfo.fractalens.complex;

public class ZoomDecorator implements IComplexPlane{

  private final double zoom;
  private final IComplexPlane plane;

  public ZoomDecorator(double zoom, IComplexPlane plane){
    this.zoom = zoom;
    this.plane = plane;
  }

  @Override
  public IComplex asComplex(int row, int column) {
    return null;
  }


}
