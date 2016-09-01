package de.fkaiser.svg.manipulator;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by fkaiser on 31.08.16.
 */
public class SVGManipulator {

    private static final Logger LOGGER = Logger.getLogger(SVGManipulator.class.getSimpleName());

    public static void main(String[] args) {

        String inputLocation = args[0];

        for (int i = 1; i < args.length; i++) {

            String styleKey = args[i].split(":")[0];
            String newStyleValue = args[i].split(":")[1];
            try {
                LOGGER.info("reading SVG file " + inputLocation);
                SVGReader svgReader = new SVGReader(inputLocation);
                svgReader.getSvgFiles().entrySet().forEach(entry -> {

                    LOGGER.info("manipulating SVG file " + inputLocation);
                    SVGManipulator svgManipulator = new SVGManipulator();
                    List<String>
                            manipulatedSVG =
                            svgManipulator.manipulateSVG(entry.getValue(), styleKey, newStyleValue);
                    try {
                        Files.write(entry.getKey().resolveSibling(entry.getKey()),
                                    manipulatedSVG.stream().collect(Collectors.joining("\n")).getBytes());
                    } catch (IOException e) {
                        LOGGER.log(Level.SEVERE, e.getMessage(), e);
                    }

                    LOGGER.info("converting SVG to PDF");
                    SVGConverter svgConverter = new SVGConverter(entry.getKey());
                    try {
                        svgConverter.convert();
                    } catch (IOException | InterruptedException e) {
                        LOGGER.log(Level.SEVERE, e.getMessage(), e);
                    }
                });
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }
        }

    }

    public List<String> manipulateSVG(List<String> svgContent, String styleKey, String newStyleValue) {

        List<String> modifiedSVGCongtent = new ArrayList<>();

        for (String svgLine : svgContent) {
            if (svgLine.contains("font-family")) {
                String newLine = svgLine.replaceAll(styleKey + "\\:\\#\\w{6}", "fill:" + newStyleValue);
                modifiedSVGCongtent.add(newLine);
            } else
                modifiedSVGCongtent.add(svgLine);
        }

        return modifiedSVGCongtent;
    }
}
