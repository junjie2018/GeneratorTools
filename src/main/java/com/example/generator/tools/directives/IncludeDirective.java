package com.example.generator.tools.directives;

import freemarker.core.Environment;
import freemarker.template.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class IncludeDirective implements TemplateDirectiveModel {

    private static final String TPL = "tpl";
    private static final String FRAGMENT = "fragment";

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {

        String fragmentTemplate = params.get(TPL) == null ? "" : params.get(TPL).toString();
        String fragmentName = params.get(FRAGMENT) == null ? "" : params.get(FRAGMENT).toString();

        if (StringUtils.isBlank(fragmentTemplate) || StringUtils.isBlank(fragmentName)) {
            throw new RuntimeException("fragmentTemplate或fragmentName未配置");
        }

        String fragmentKey = fragmentTemplate + "#" + fragmentName;

        Template template = env.getConfiguration().getTemplate(fragmentKey);

        if (template == null) {
            throw new RuntimeException("片段未找到");
        }

        env.include(template);
    }
}
