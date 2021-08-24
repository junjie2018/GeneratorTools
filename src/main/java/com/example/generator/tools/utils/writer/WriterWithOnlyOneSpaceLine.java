package com.example.generator.tools.utils.writer;

import org.apache.commons.lang3.StringUtils;

import java.io.*;

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

        fileWriter.write(removeTooManySpaceLines(inputStr));
        fileWriter.flush();
    }

    @Override
    public void close() throws IOException {
        fileWriter.close();
    }

    private String removeTooManySpaceLines(String inputStr) throws IOException {
        StringWriter sw = new StringWriter();
        BufferedWriter bw = new BufferedWriter(sw);
        BufferedReader br = new BufferedReader(new StringReader(inputStr));

        String line;
        int spaceCount = 0;
        while ((line = br.readLine()) != null) {
            if ("".equals(StringUtils.trim(line))) {
                ++spaceCount;
                if (spaceCount == 1) {
                    bw.write(line);
                    bw.newLine();
                }
            } else {
                spaceCount = 0;
                bw.write(line);
                bw.newLine();
            }
        }
        bw.flush();
        return sw.toString();
    }
}
