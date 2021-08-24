package com.example.generator.tools.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Consumer;

public class FileUtils {

    public static void walkThrough(String path, Consumer<Path> disposer) {
        walkThrough(Paths.get(path), disposer);
    }

    public static void walkThrough(Path path, Consumer<Path> disposer) {
        if (path == null || !Files.exists(path) || !Files.isDirectory(path)) {
            throw new RuntimeException("路径不存在：" + path);
        }

        try {
            Files.list(path).forEach(pathItem -> {
                if (Files.isDirectory(pathItem)) {
                    walkThrough(pathItem, disposer);
                } else {
                    disposer.accept(pathItem);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readAllContent(String path) {
        return readAllContent(Paths.get(path));
    }

    public static String readAllContent(Path path) {
        try {
            List<String> lines = Files.readAllLines(path);
            return StringUtils.join(lines, '\n');
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void listDirectories(Path path, Consumer<Path> disposer) {
        try {
            Files.list(path).forEach(pathItem -> {
                if (Files.isDirectory(pathItem)) {
                    if (disposer != null) {
                        disposer.accept(pathItem);
                    }
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
