package fr.univartois.butinfo.fractalens.generator;

import fr.univartois.butinfo.fractalens.complex.IComplex;
import fr.univartois.butinfo.fractalens.complex.IComplexPlane;
import fr.univartois.butinfo.fractalens.complex.sequence.IComplexSequence;
import fr.univartois.butinfo.fractalens.image.IFractalImage;

import java.awt.*;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.function.BiFunction;

public class FractalGenerator implements IFractalGenerator{
	private final IFractalImage image;
	private final IComplexPlane plane;
	private final int nbMaxIteration;
	private final IPalette palette;
	private final String filePath;
	private final BiFunction<IComplex, Integer, IComplexSequence> sequenceBiFunction;

	public FractalGenerator(IFractalImage image, IComplexPlane plane, int nbMaxIteration, IPalette palette, String filePath, BiFunction<IComplex, Integer, IComplexSequence> sequenceBiFunction){
		this.image = image;
		this.plane = plane;
		this.nbMaxIteration = nbMaxIteration;
		this.palette = palette;
		this.filePath = filePath;
		this.sequenceBiFunction = sequenceBiFunction;
	}

	@Override
	public Color computePixelColor(int row, int column) {
		IComplex z0 = plane.asComplex(row, column);
		IComplexSequence suite = sequenceBiFunction.apply(z0, nbMaxIteration);
		int counter = 0;
		for (var _ : suite){
			counter++;
		}
		return palette.getCouleur(counter, nbMaxIteration);
	}


	@Override
	public IFractalImage generateImage() {
		int height = image.getHeight();
		int width = image.getWidth();
		for (int row = 0 ; row < height; row++){
			for (int column = 0 ; column < width ; column++){
				Color color = computePixelColor(row, column);
				 image.setColor(row, column, color);
			}
		} return image;
	}

	@Override
	public void saveImage() {
		try {
			image.saveAs(filePath);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
