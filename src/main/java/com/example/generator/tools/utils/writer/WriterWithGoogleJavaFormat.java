package com.example.generator.tools.utils.writer;

import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;

import java.io.IOException;
import java.io.Writer;

public class WriterWithGoogleJavaFormat extends Writer {
    private final Writer writer;
    private StringBuilder stringBuilder;

    public WriterWithGoogleJavaFormat(Writer writer) {
        this.writer = writer;
        this.stringBuilder = new StringBuilder();
    }


    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        stringBuilder.append(String.valueOf(cbuf, off, len));
    }

    @Override
    public void flush() throws IOException {
        String inputStr = stringBuilder.toString();

        try {
            String formattedSource = new Formatter().formatSource(inputStr);
            writer.write(formattedSource);
            writer.flush();
        } catch (FormatterException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }
}
