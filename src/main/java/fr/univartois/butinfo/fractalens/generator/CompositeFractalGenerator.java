package fr.univartois.butinfo.fractalens.generator;

import com.madgag.gif.fmsware.AnimatedGifEncoder;
import fr.univartois.butinfo.fractalens.image.IFractalImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class CompositeFractalGenerator implements IFractalGenerator {
  private final List<IFractalGenerator> generators;

  private final int delay;

  private final String outputFile;

  private final List<BufferedImage> images = new ArrayList<>();

  public CompositeFractalGenerator(List<IFractalGenerator> generators, int delay, String outputFile){
    this.generators = generators;
    this.delay = delay;
    this.outputFile = outputFile;
  }

  @Override
  public IFractalImage generateImage() {
    images.clear();
    IFractalImage last = null;

    for (int i = 0; i < generators.size(); i++) {
      IFractalImage image = generators.get(i).generateImage();
      images.add(image.asBufferedImage());
      last = image;
    }
    return last;
  }

  @Override
  public Color computePixelColor(int row, int column) {
    return null;
  }

  @Override
  public void saveImage() {
    AnimatedGifEncoder animatedGif = new AnimatedGifEncoder();
    animatedGif.start(outputFile);
    animatedGif.setDelay(delay);
    animatedGif.setRepeat(0);
    animatedGif.setQuality(10); // if number small, more quality but take more time

     for ( BufferedImage image : images){
       animatedGif.addFrame(image);
     }
    animatedGif.finish();
  }
}
