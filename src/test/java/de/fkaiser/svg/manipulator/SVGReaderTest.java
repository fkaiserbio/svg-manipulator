package de.fkaiser.svg.manipulator;

import org.junit.Test;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * Created by fkaiser on 31.08.16.
 */
public class SVGReaderTest {
    @Test
    public void readSVG() throws Exception {

        SVGReader svgReader = new SVGReader("/home/fkaiser/Workspace/IdeaProjects/svg-manipulator/src/test/resources/binding_modes.svg");
        Map<Path, List<String>> svgFiles = svgReader.getSvgFiles();
        svgFiles.entrySet().forEach(entry -> {
            SVGManipulator svgManipulator = new SVGManipulator();
            svgManipulator.manipulateSVG(entry.getValue(), "fill", "#ff0000");
        });

    }

}