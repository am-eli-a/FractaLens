package fr.univartois.butinfo.fractalens.application;

import picocli.CommandLine;
import java.awt.*;

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
        FractaLensCommand options = new FractaLensCommand();
        CommandLine commandLine = new CommandLine(options);

        if (args.length == 0) {
            commandLine.usage(System.out);
            return;
        }
        System.exit(commandLine.execute(args));
    }
}
