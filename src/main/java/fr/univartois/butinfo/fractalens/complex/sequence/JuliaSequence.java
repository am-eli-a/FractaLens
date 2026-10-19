package fr.univartois.butinfo.fractalens.complex.sequence;

import fr.univartois.butinfo.fractalens.complex.IComplex;
import java.util.Iterator;

public class JuliaSequence implements IComplexSequence {

  private final IComplex first ;
  private final int max ;
  private final IComplex c ;

  public JuliaSequence(IComplex first, int max, IComplex c) {
    this.first = first;
    this.max = max;
    this.c = c;
  }

  @Override
  public IComplex computeNext(IComplex previous){
    return previous.multiply(previous).add(c);
  }

  @Override
  public Iterator<IComplex> iterator() {
    return new ComplexSequenceIterator(this, first, max);
  }

}
