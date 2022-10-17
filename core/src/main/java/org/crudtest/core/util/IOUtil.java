package org.crudtest.core.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.crudtest.core.exception.FileIOException;

public class IOUtil {

    public static void writeFile(Path filePath, List<String> dataList) throws FileIOException {
        try (BufferedWriter bw = Files.newBufferedWriter(filePath)) {
            for (String line : dataList) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException ie) {
            throw new FileIOException(ie);
        }
    }

    public static Path getTemplatePath(String templateName) throws FileIOException {
        URL url = ClassLoader.getSystemResource(templateName);
        try {
            return Paths.get(url.toURI());
        } catch (URISyntaxException e) {
            throw new FileIOException(e);
        }
    }

    public static List<String> readAllLine(Path path) throws FileIOException {
        try {
            return Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException ie) {
            throw new FileIOException(ie);
        }
    }

    public static List<String> readTemplateAllLine(String templateName) throws FileIOException {
        List<String> line = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(ClassLoader.getSystemResourceAsStream(templateName), StandardCharsets.UTF_8))) {

            for (String l; (l = br.readLine()) != null;) {
                line.add(l);
            }
        } catch (IOException ie) {
            throw new FileIOException(ie);
        }
        return line;
    }
}
