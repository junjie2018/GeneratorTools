package com.example.generator.tools.utils.writer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class WriterWithOnlyOneSpaceLine extends Writer {
    private final FileWriter fileWriter;
    private final StringBuffer stringBuffer;

    public WriterWithOnlyOneSpaceLine(FileWriter fileWriter) {
        this.fileWriter = fileWriter;
        this.stringBuffer = new StringBuffer();
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        stringBuffer.append(String.valueOf(cbuf, off, len));
    }

    @Override
    public void flush() throws IOException {
        String inputStr = stringBuffer.toString();


//        String result = TemplateUtilsMax.changeSpaceLinesToOne(inputStr);

        fileWriter.write(inputStr);
        fileWriter.flush();
    }

    @Override
    public void close() throws IOException {
        fileWriter.close();
    }
}
