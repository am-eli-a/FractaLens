package fr.univartois.butinfo.fractalens.application;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.sequence.ComplexSequenceType;
import fr.univartois.butinfo.fractalens.generator.FractalGeneratorBuilder;
import fr.univartois.butinfo.fractalens.generator.IFractalGenerator;
import fr.univartois.butinfo.fractalens.image.palette.FixedPalette;
import fr.univartois.butinfo.fractalens.image.palette.IPalette;

import java.awt.*;
import java.util.List;

/**
 * The Main class generates two animated GIF images using the fractal generator
 * builder: a zoom on the Mandelbrot fractal, centered on
 * -0.743643887037151 + 0.131825904205330i, and a rotation of the constant of
 * Julia's sequence, starting from c = 0.7885.
 * Each animation is described by a chain of calls on the builder, which creates one
 * generator per frame and gathers them in a composite generator.
 *
 * @author Nathan Salomé
 * @author Matty Fourmestraux
 * @author Baptiste Wagee
 *
 * @version 0.5.0
 */
public class Main {

  /**
   * Generates the two demonstration animations and saves them.
   *
   * @param args The names of the two output files (optional). If they are not
   *        given, the animations are saved in {@code mandelbrot-zoom.gif} and
   *        {@code julia-rotation.gif}.
   */
  public static void main(String[] args) {
    String zoomOutput = (args.length > 0) ? args[0] : "mandelbrot-zoom.gif";
    String rotationOutput = (args.length > 1) ? args[1] : "julia-rotation.gif";

    IPalette firePalette = new FixedPalette(List.of(
            new Color(255, 255, 220), Color.YELLOW, Color.ORANGE, Color.RED, Color.BLACK));

    IPalette nightPalette = new FixedPalette(List.of(
            new Color(10, 10, 40), new Color(60, 30, 120), new Color(220, 80, 160),
            new Color(255, 220, 120), Color.BLACK));

    IPalette forestPalette = new FixedPalette(List.of(
            new Color(5, 20, 10), new Color(20, 90, 40), new Color(120, 200, 80),
            new Color(230, 255, 200), Color.BLACK));

    // Animation 1 : zoom sur Mandelbrot
    IFractalGenerator mandelbrotZoom = new FractalGeneratorBuilder()
            .usingSequence(ComplexSequenceType.MANDELBROT)
            .withSize(600, 600)
            .zoomedBy(0.015)
            .centeredAt(new Complex(-0.743643887037151, 0.131825904205330))
            .withMaxIterations(500)
            .coloredWith(firePalette)
            .animatedWithZoom(0.92, 120)
            .withFrameDelay(80)
            .savedAs(zoomOutput)
            .build();
    mandelbrotZoom.generateImage();
    mandelbrotZoom.saveImage();

    // Animation 2 : rotation de Julia (c = 0.7885 au départ)
    IFractalGenerator juliaRotation = new FractalGeneratorBuilder()
            .withSize(400, 400)
            .zoomedBy(0.016)
            .centeredAt(new Complex(0, 0))
            .withMaxIterations(50)
            .coloredWith(forestPalette)
            .animatedWithRotation(new Complex(0.7885, 0), 2 * Math.PI / 72, 72)
            .withFrameDelay(60)
            .savedAs(rotationOutput)
            .build();
    juliaRotation.generateImage();
    juliaRotation.saveImage();
  }

}
