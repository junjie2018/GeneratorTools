package com.example.generator.tools.directives;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.Map;

public class NoSpaceLineDiretive implements TemplateDirectiveModel {
    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        StringWriter stringWriter = new StringWriter();

        body.render(stringWriter);

        env.getOut().write(removeSpaceLines(stringWriter.toString()));
    }

    private String removeSpaceLines(String inputStr) throws IOException {
        StringWriter sw = new StringWriter();
        BufferedWriter bw = new BufferedWriter(sw);
        BufferedReader br = new BufferedReader(new StringReader(inputStr));

        String line;
        while ((line = br.readLine()) != null) {
            if ("".equals(StringUtils.trim(line))) {
                continue;
            }
            bw.write(line);
            bw.newLine();
        }
        bw.flush();
        return sw.toString();
    }
}
