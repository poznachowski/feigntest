package pl.poznachowski.feigntest;

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.annotation.MergedAnnotations.SearchStrategy;

/**
 * Substitutes application properties related to FeignClient urls
 */
class FeignTestContextBootstrapper extends SpringBootTestContextBootstrapper {

    @Override
    protected String[] getProperties(Class<?> testClass) {
        var urls = MergedAnnotations.from(testClass, SearchStrategy.INHERITED_ANNOTATIONS)
                .get(FeignTest.class)
                .getValue("substituteProperties", String[].class)
                .orElse(null);

        if (urls != null) {
            var substitutionValue = MergedAnnotations.from(testClass, SearchStrategy.INHERITED_ANNOTATIONS)
                    .get(FeignTest.class)
                    .getValue("substitutionValue", String.class)
                    .orElse(null);

            urls = Arrays.stream(urls)
                    .map(urlProp -> String.join("=", urlProp, substitutionValue))
                    .toArray(String[]::new);
        }

        String[] properties = MergedAnnotations.from(testClass, SearchStrategy.INHERITED_ANNOTATIONS)
                .get(FeignTest.class)
                .getValue("properties", String[].class)
                .orElse(null);
        return ArrayUtils.addAll(properties, urls);
    }
}
