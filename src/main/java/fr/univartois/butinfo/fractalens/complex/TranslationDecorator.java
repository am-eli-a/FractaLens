package fr.univartois.butinfo.fractalens.complex;

public class TranslationDecorator extends AbstractPlaneDecorator{
  private final IComplexPlane centre;

  public TranslationDecorator(IComplexPlane centre, IComplexPlane plane) {
    super(plane);
    this.centre = centre;
  }

  @Override
  public IComplex asComplex(int row, int column) {
    return getPlane().asComplex(row, column).add(centre);
  }
}
