package de.fkaiser.svg.manipulator;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by fkaiser on 31.08.16.
 */
public class SVGReader {

    private final Path inputPath;
    private Map<Path, List<String>> svgFiles;

    public SVGReader(String location) throws IOException {

        svgFiles = new HashMap<>();

        File file = new File(location);
        inputPath = file.toPath();
        if (file.isDirectory()) {
            readDirectory();
        } else if (file.isFile()) {
            svgFiles.put(inputPath, readSVG(inputPath));
        }
    }

    public Map<Path, List<String>> getSvgFiles() {
        return svgFiles;
    }

    private void readDirectory() throws IOException {
        Files.walk(inputPath, Integer.MAX_VALUE, FileVisitOption.FOLLOW_LINKS).forEach(path -> {
            try {
                if (path.toFile().isFile())
                    svgFiles.put(path, readSVG(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private List<String> readSVG(Path inputPath) throws IOException {
        return Files.readAllLines(inputPath).stream().collect(Collectors.toList());
    }
}
