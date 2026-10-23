package fr.univartois.butinfo.fractalens.application;

import picocli.CommandLine;

/**
 * The Main class is the entry point of the application.
 * It reads the options of the command line with the picocli library, and generates
 * the corresponding image or animation. When no option is given, it displays the
 * help message.
 *
 *
 * @version 0.6.0
 */
public class Main {

    /**
     * Runs the application with the options given on the command line.
     *
     * @param args The options of the command line. If there is none, the help message
     *        is displayed.
     */
    public static void main(String[] args) {
        FractaLensCommand options = new FractaLensCommand();
        CommandLine commandLine = new CommandLine(options);

        // Without any option, the help message is displayed.
        String[] arguments = (args.length == 0) ? new String[] { "--help" } : args;
        System.exit(commandLine.execute(arguments));
    }
}