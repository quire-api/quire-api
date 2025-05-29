package io.quire.api.process;

import io.quire.api.annotation.CodeSample;
import io.quire.api.annotation.CodeSampleLangs;
import io.swagger.jaxrs.ext.AbstractSwaggerExtension;
import io.swagger.jaxrs.ext.SwaggerExtension;
import io.swagger.models.Operation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by hugomartins on 12/26/17.
 */
public class CodeSampleSwagger extends AbstractSwaggerExtension {


    public void decorateOperation(Operation operation, Method method, Iterator<SwaggerExtension> chain) {

        CodeSample cs = method.getAnnotation(CodeSample.class);

        if (cs == null){
            super.decorateOperation(operation, method, chain);
            return;
        }

        List<Language> langs = new ArrayList<Language>();

        for (CodeSampleLangs l : cs.langs()){
            Language lang = new Language();
            lang.setSource(l.source());
            lang.setLang(l.lang());
            langs.add(lang);
        }

        operation.getVendorExtensions().put("x-code-samples", langs);


        super.decorateOperation(operation, method, chain);

    }
}