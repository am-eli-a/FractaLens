package fr.univartois.butinfo.fractalens.application;

import java.util.concurrent.Callable;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.sequence.ComplexSequenceType;
import fr.univartois.butinfo.fractalens.generator.FractalGeneratorBuilder;
import fr.univartois.butinfo.fractalens.generator.IFractalGenerator;
import fr.univartois.butinfo.fractalens.image.ImageType;
import fr.univartois.butinfo.fractalens.image.palette.PaletteType;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * The FractaLensCommand class describes the command line of the application.
 *
 * Its attributes are filled by the picocli library from the options given on the
 * command line. They are then used to configure a {@link FractalGeneratorBuilder},
 * which creates the generator of a single image, of a zoom animation or of a
 * rotation animation.
 *
 * @author Matty Fourmestraux
 * @author Amelia Dufour-Lazrek
 *
 * @version 0.1.0
 */
@Command(name = "fractalens", description = "Generates images and animations of fractals.")
public class FractaLensCommand implements Callable<Integer> {

    /**
     * The fractal to generate (option {@code -f}).
     */
    @Option(names = {"-f", "--fractal-name"}, required = true, description = "The name of the fractal to generate: ${COMPLETION-CANDIDATES}.")
    private ComplexSequenceType fractalName;

    /**
     * The maximum number of iterations computed for each pixel (option {@code -n}).
     */
    @Option(names = {"-n", "--nb-iterations"}, required = true, description = "The maximum number of iterations.")
    private int nbIterations;

    /**
     * The height of the image, in pixels (option {@code -h}).
     */
    @Option(names = {"-h", "--height"}, required = true, description = "The height of the image, in pixels.")
    private int height;

    /**
     * The width of the image, in pixels (option {@code -w}).
     */
    @Option(names = {"-w", "--width"}, required = true, description = "The width of the image, in pixels.")
    private int width;

    /**
     * The zoom applied on the complex plane (option {@code -s}).
     */
    @Option(names = {"-s", "--scale"}, required = true, description = "The scale (zoom) applied on the image.")
    private double scale;

    /**
     * The real part of the center of the image (option {@code -x}).
     */
    @Option(names = {"-x", "--focus-x"}, required = true, description = "The real part of the center of the image.")
    private double focusX;

    /**
     * The imaginary part of the center of the image (option {@code -y}).
     */
    @Option(names = {"-y", "--focus-y"}, required = true, description = "The imaginary part of the center of the image.")
    private double focusY;

    /**
     * The palette used to color the image (option {@code -p}).
     */
    @Option(names = {"-p", "--palette-name"}, required = true, description = "The name of the color palette: ${COMPLETION-CANDIDATES}.")
    private PaletteType paletteName;

    /**
     * The type of image to generate: a single image, a zoom or a rotation (option {@code -t}).
     */
    @Option(names = {"-t", "--type"}, defaultValue = "SINGLE", description = "The type of image to generate: ${COMPLETION-CANDIDATES} (default: ${DEFAULT-VALUE}).")
    private ImageType type;

    /**
     * The number of images of an animation (option {@code -i}).
     */
    @Option(names = {"-i", "--image-count"}, defaultValue = "50", description = "The number of images of the animations (default: ${DEFAULT-VALUE}).")
    private int imageCount;

    /**
     * The name of the output file (option {@code -o}).
     */
    @Option(names = {"-o", "--output"}, required = true, description = "The name of the output file.")
    private String output;

    /**
     * The real part of the constant c, used by the rotation (option {@code --c-real}).
     */
    @Option(names = "--c-real", defaultValue = "-0.4", description = "The real part of the constant c of the sequence (default: ${DEFAULT-VALUE}).")
    private double cReal;

    /**
     * The imaginary part of the constant c, used by the rotation (option {@code --c-imaginary}).
     */
    @Option(names = "--c-imaginary", defaultValue = "0.6", description = "The imaginary part of the constant c of the sequence (default: ${DEFAULT-VALUE}).")
    private double cImaginary;

    /**
     * Whether the help message has been requested (option {@code --help}).
     */
    @Option(names = "--help", usageHelp = true, description = "Displays this help message.")
    private boolean helpRequested;

    /**
     * The delay between two images of an animation, in milliseconds (option {@code -d}).
     */
    @Option(names = {"-d", "--delay"}, defaultValue = "100", description = "The delay between two images of an animation, in milliseconds (default: ${DEFAULT-VALUE}).")
    private int delay;

    /**
     * The factor applied to the zoom between two images of a zoom animation (option {@code -z}).
     */
    @Option(names = {"-z", "--zoom-factor"}, defaultValue = "0.9", description = "The factor applied to the scale between two images of a zoom animation (default: ${DEFAULT-VALUE}).")
    private double zoomFactor;

    /**
     * Generates the image or the animation described by the options, and saves it in
     * the output file. This method is called by picocli once the options are read.
     *
     * @return The exit code of the application (0 when everything went well).
     *
     * @see Callable#call()
     */
    @Override
    public Integer call() {
        IFractalGenerator generator = createGenerator();
        generator.generateImage();
        generator.saveImage();
        return 0;
    }

    /**
     * Creates the generator described by the options, using the builder.
     * For a zoom, each image is zoomed by the zoom factor. For a rotation, the constant
     * of Julia's sequence makes a complete turn over all the images.
     *
     * @return The generator of a single image, or a composite generator for an
     *         animation.
     */
    IFractalGenerator createGenerator() {
        FractalGeneratorBuilder builder = new FractalGeneratorBuilder()
                .usingSequence(fractalName)
                .withSize(width, height)
                .zoomedBy(scale)
                .centeredAt(new Complex(focusX, focusY))
                .withMaxIterations(nbIterations)
                .coloredWith(paletteName)
                .savedAs(output)
                ;

        return switch (type) {
            case SINGLE -> builder.build();
            case ZOOM -> builder.animatedWithZoom(zoomFactor, imageCount).withFrameDelay(delay).build();
            case ROTATION -> builder.animatedWithRotation(new Complex(cReal, cImaginary), (2*Math.PI / imageCount), imageCount).withFrameDelay(delay).build();
        };
    }
}