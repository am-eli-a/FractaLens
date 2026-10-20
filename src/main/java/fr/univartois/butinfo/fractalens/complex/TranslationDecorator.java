package fr.univartois.butinfo.fractalens.complex;

public class TranslationDecorator extends AbstractPlaneDecorator{
  private final IComplex center;

  public TranslationDecorator(IComplex center, IComplexPlane plane) {
    super(plane);
    this.center = center;
  }

  @Override
  public IComplex asComplex(int row, int column) {
    return getPlane().asComplex(row, column).add(center);
  }
}
