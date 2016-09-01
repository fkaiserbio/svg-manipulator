package de.fkaiser.svg.manipulator;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by fkaiser on 01.09.16.
 */
public class SVGConverter {

    private final ProcessBuilder processBuilder;

    public SVGConverter(Path svgPath) {
        this.processBuilder =
                new ProcessBuilder("inkscape", "-A", svgPath.toString().split("\\.")[0] + ".pdf", svgPath.toString());
        processBuilder.inheritIO();
    }

    public int convert() throws IOException, InterruptedException {
        Process process = processBuilder.start();
        return process.waitFor();
    }
}
