package fr.univartois.butinfo.fractalens.application;

import java.util.concurrent.Callable;
import fr.univartois.butinfo.fractalens.complex.sequence.ComplexSequenceType;
import fr.univartois.butinfo.fractalens.image.ImageType;
import fr.univartois.butinfo.fractalens.image.palette.PaletteType;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "fractalens", description = "Generates images and animations of fractals.")
public class FractaLensCommand implements Callable<Integer> {

  @Option(names = {"-f", "--fractal-name"}, required = true, description = "The name of the fractal to generate: ${COMPLETION-CANDIDATES}.")
  private ComplexSequenceType fractalName;

  @Option(names = {"-n", "--nb-iterations"}, required = true, description = "The maximum number of iterations.")
  private int nbIterations;

  @Option(names = {"-h", "--height"}, required = true, description = "The height of the image, in pixels.")
  private int height;

  @Option(names = {"-w", "--width"}, required = true, description = "The width of the image, in pixels.")
  private int width;

  @Option(names = {"-s", "--scale"}, required = true, description = "The scale (zoom) applied on the image.")
  private double scale;

  @Option(names = {"-x", "--focus-x"}, required = true, description = "The real part of the center of the image.")
  private double focusX;

  @Option(names = {"-y", "--focus-y"}, required = true, description = "The imaginary part of the center of the image.")
  private double focusY;

  @Option(names = {"-p", "--palette-name"}, required = true, description = "The name of the color palette: ${COMPLETION-CANDIDATES}.")
  private PaletteType paletteName;

  @Option(names = {"-t", "--type"}, defaultValue = "SINGLE", description = "The type of image to generate: ${COMPLETION-CANDIDATES} (default: ${DEFAULT-VALUE}).")
  private ImageType type;

  @Option(names = {"-i", "--image-count"}, defaultValue = "50", description = "The number of images of the animations (default: ${DEFAULT-VALUE}).")
  private int imageCount;

  @Option(names = {"-o", "--output"}, required = true, description = "The name of the output file.")
  private String output;

  @Option(names = "--c-real", defaultValue = "-0.4", description = "The real part of the constant c of the sequence (default: ${DEFAULT-VALUE}).")
  private double cReal;

  @Option(names = "--c-imaginary", defaultValue = "0.6", description = "The imaginary part of the constant c of the sequence (default: ${DEFAULT-VALUE}).")
  private double cImaginary;

  @Option(names = "--help", usageHelp = true, description = "Displays this help message.")
  private boolean helpRequested;

  @Option(names = {"-d", "--delay"}, defaultValue = "100", description = "The delay between two images of an animation, in milliseconds (default: ${DEFAULT-VALUE}).")
  private int delay;

  @Option(names = {"-z", "--zoom-factor"}, defaultValue = "0.9", description = "The factor applied to the scale between two images of a zoom animation (default: ${DEFAULT-VALUE}).")
  private double zoomFactor;

  @Override
  public Integer call() {

    return 0;
  }
}