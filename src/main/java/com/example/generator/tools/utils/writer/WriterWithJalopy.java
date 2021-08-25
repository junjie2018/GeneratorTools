package com.example.generator.tools.utils.writer;

import de.hunsicker.jalopy.Jalopy;

import java.io.IOException;
import java.io.Writer;

public class WriterWithJalopy extends Writer {
    private final Writer writer;
    private StringBuilder stringBuilder;

    public WriterWithJalopy(Writer writer) {
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

        Jalopy jalopy = new Jalopy();
        jalopy.setEncoding("UTF-8");
        jalopy.setInput(inputStr, "");
        jalopy.setOutput(writer);
        jalopy.format();
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }
}
