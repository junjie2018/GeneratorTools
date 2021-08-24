package com.example.generator.tools.directives;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

public class NoSpaceLineDiretive implements TemplateDirectiveModel {
    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        StringWriter stringWriter = new StringWriter();

        body.render(stringWriter);

//        String result = TemplateUtilsMax.changeSpaceLinesToNone(stringWriter.toString());

        env.getOut().write(stringWriter.toString());
    }
}
