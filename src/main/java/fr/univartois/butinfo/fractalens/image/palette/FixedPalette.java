package fr.univartois.butinfo.fractalens.image.palette;

import java.awt.Color;
import java.util.List;

public class FixedPalette implements IPalette{
	private final List<Color> colorList;

	public FixedPalette(List<Color> colorList){
		if (colorList.size()<2){
			throw new IllegalArgumentException("Trop peu de couleurs");
		}
		this.colorList = List.copyOf(colorList);
	}

	@Override
	public Color getColor(int nbIteration, int maxIteration) {
		if(nbIteration < 0 || nbIteration > maxIteration){
			throw new IllegalArgumentException("Erreur sur nbIteration");
		}
		double ratio = (double) nbIteration / maxIteration;
		double position = ratio * (colorList.size() - 1);
		int index = (int) position;
		if(nbIteration == maxIteration){
			return colorList.getLast();
		}
		double localRatio = position - index;
		Color color1 = colorList.get(index);
		Color color2 = colorList.get(index + 1);
		double r = color1.getRed() + localRatio * (color2.getRed() - color1.getRed());
		double g = color1.getGreen() + localRatio * (color2.getGreen() - color1.getGreen());
		double b = color1.getBlue() + localRatio * (color2.getBlue() - color1.getBlue());
		return new Color((int) Math.round(r), (int) Math.round(g), (int) Math.round(b));
	}
}
