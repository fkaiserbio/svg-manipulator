package de.fkaiser.svg.manipulator;

import java.io.File;
import java.io.FileFilter;
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

    private static final String SVG_SUFFIX = ".svg";
    private static final FileFilter SVG_FILE_FILTER = pathname -> pathname.getName().toLowerCase().endsWith(SVG_SUFFIX);
    private final Path inputPath;
    private Map<Path, List<String>> svgFiles;

    public SVGReader(String location) throws IOException {

        svgFiles = new HashMap<>();

        File file = new File(location);
        inputPath = file.toPath();
        if (file.isDirectory()) {
            readDirectory();
        } else if (file.isFile() && isSVGFile(file)) {
            svgFiles.put(inputPath, readSVG(inputPath));
        }
    }

    private boolean isSVGFile(File file) {
        return SVG_FILE_FILTER.accept(file);
    }

    private boolean isSVGFile(Path path) {
        return isSVGFile(path.toFile());
    }

    public Map<Path, List<String>> getSvgFiles() {
        return svgFiles;
    }

    private void readDirectory() throws IOException {
        Files.walk(inputPath, Integer.MAX_VALUE, FileVisitOption.FOLLOW_LINKS).filter(this::isSVGFile).forEach(path -> {
            try {
                if (path.toFile().isFile()) {
                    svgFiles.put(path, readSVG(path));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private List<String> readSVG(Path inputPath) throws IOException {
        return Files.readAllLines(inputPath).stream().collect(Collectors.toList());
    }
}
