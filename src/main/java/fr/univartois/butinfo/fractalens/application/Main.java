package fr.univartois.butinfo.fractalens.application;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.IComplex;
import fr.univartois.butinfo.fractalens.complex.sequence.ComplexSequenceType;
import fr.univartois.butinfo.fractalens.complex.sequence.ComplexSequences;
import fr.univartois.butinfo.fractalens.generator.FractalGeneratorBuilder;
import fr.univartois.butinfo.fractalens.generator.IFractalGenerator;
import fr.univartois.butinfo.fractalens.image.palette.FixedPalette;
import fr.univartois.butinfo.fractalens.image.palette.IPalette;

import java.awt.Color;
import java.util.List;

/**
 * The Main class generates three fractal images using the fractal generator
 * builder: a Mandelbrot fractal, a Burning Ship fractal and a fractal defined by
 * a free formula (z³ + z² + c).
 * Each image is described by a chain of calls on the builder, which configures its
 * sequence, size, zoom, center, maximum number of iterations, palette and output file.
 *
 * @author Nathan Salomé
 * @author Matty Fourmestraux
 * @author Baptiste Wagee
 *
 * @version 0.4.0
 */
public class Main {

  /**
   * Generates the three demonstration images and saves them.
   *
   * @param args The names of the three output files (optional). If they are not
   *        given, the images are saved in {@code fractal1.png}, {@code fractal2.png}
   *        and {@code fractal3.png}.
   */
  public static void main(String[] args) {
    String firstOutput = (args.length > 0) ? args[0] : "fractal1.png";
    String secondOutput = (args.length > 1) ? args[1] : "fractal2.png";
    String thirdOutput = (args.length > 2) ? args[2] : "fractal3.png";

    IPalette firePalette = new FixedPalette(List.of(
            new Color(255, 255, 220), Color.YELLOW, Color.ORANGE, Color.RED, Color.BLACK));

    IPalette icePalette = new FixedPalette(List.of(
            Color.WHITE, new Color(150, 220, 255), new Color(30, 120, 220),
            new Color(10, 30, 90), Color.BLACK));

    IPalette forestPalette = new FixedPalette(List.of(
            new Color(5, 20, 10), new Color(20, 90, 40), new Color(120, 200, 80),
            new Color(230, 255, 200), Color.BLACK));

    IFractalGenerator mandelbrot = new FractalGeneratorBuilder()
            .usingSequence(ComplexSequenceType.MANDELBROT)
            .withSize(800, 600)
            .zoomedBy(0.001)
            .centeredAt(new Complex(-0.75, 0))
            .withMaxIterations(250)
            .coloredWith(firePalette)
            .savedAs(firstOutput)
            .build();
    mandelbrot.generateImage();
    mandelbrot.saveImage();

    IComplex burningShipConstant = new Complex(-1.6, -0.1);
    IFractalGenerator burningShip = new FractalGeneratorBuilder()
            .usingSequence((z0, max) -> ComplexSequences.burningShip(z0, max, burningShipConstant))
            .withSize(800, 800)
            .zoomedBy(0.0015)
            .centeredAt(new Complex(0, 0))
            .withMaxIterations(64)
            .coloredWith(icePalette)
            .savedAs(secondOutput)
            .build();
    burningShip.generateImage();
    burningShip.saveImage();


    IComplex cubicConstant = new Complex(-0.2, 0.75);
    IFractalGenerator cubicJulia = new FractalGeneratorBuilder()
            .usingSequence((z0, max) -> ComplexSequences.cubicJulia(z0, max, cubicConstant))
            .withSize(800, 800)
            .zoomedBy(0.0032)
            .centeredAt(new Complex(-0.25, 0))
            .withMaxIterations(40)
            .coloredWith(forestPalette)
            .savedAs(thirdOutput)
            .build();
    cubicJulia.generateImage();
    cubicJulia.saveImage();
  }

}
