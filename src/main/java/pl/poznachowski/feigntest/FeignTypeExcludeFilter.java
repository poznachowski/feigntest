package pl.poznachowski.feigntest;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.boot.test.autoconfigure.filter.StandardAnnotationCustomizableTypeExcludeFilter;
import org.springframework.cloud.openfeign.FeignLoggerFactory;

import feign.Client;
import feign.Contract;
import feign.Feign;
import feign.Logger;
import feign.QueryMapEncoder;
import feign.Request;
import feign.RequestInterceptor;
import feign.Retryer;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;

public class FeignTypeExcludeFilter extends StandardAnnotationCustomizableTypeExcludeFilter<FeignTest> {

    private static final Set<Class<?>> DEFAULT_INCLUDES;

    static {
        Set<Class<?>> includes = new LinkedHashSet<>();
        includes.add(Decoder.class);
        includes.add(Encoder.class);
        includes.add(Contract.class);
        includes.add(Logger.class);
        includes.add(Logger.Level.class);
        includes.add(FeignLoggerFactory.class);
        includes.add(Feign.Builder.class);
        includes.add(Client.class);
        includes.add(Retryer.class);
        includes.add(ErrorDecoder.class);
        includes.add(Request.Options.class);
        includes.add(RequestInterceptor.class);
        includes.add(QueryMapEncoder.class);
        DEFAULT_INCLUDES = Collections.unmodifiableSet(includes);
    }

    FeignTypeExcludeFilter(Class<?> testClass) {
        super(testClass);
    }

    @Override
    protected Set<Class<?>> getDefaultIncludes() {
        return DEFAULT_INCLUDES;
    }

}
