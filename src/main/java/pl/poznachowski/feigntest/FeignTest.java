package pl.poznachowski.feigntest;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.filter.TypeExcludeFilters;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AliasFor;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ExtendWith(SpringExtension.class)
@OverrideAutoConfiguration(enabled = false)
@BootstrapWith(FeignTestContextBootstrapper.class)
@TypeExcludeFilters(FeignTypeExcludeFilter.class)
@ImportAutoConfiguration
@AutoConfigureWireMock(port = 0)
@AutoConfigureJson
@AutoConfigureFeign
@ActiveProfiles
@EnableFeignClients
public @interface FeignTest {

    /**
     * List of classes annotated with @FeignClient. If not empty, disables classpath
     * scanning.
     * @return list of FeignClient classes
     */
    @AliasFor(annotation = EnableFeignClients.class, attribute = "clients")
    Class<?>[] clients() default {};

    /**
     * Properties, which values should be replaced with the substitutionValue
     * @return the properties to substitute
     */
    String[] substituteProperties() default {};

    String substitutionValue() default "http://localhost:${wiremock.server.port}";

    /**
     * Properties in form {@literal key=value} that should be added to the Spring
     * {@link Environment} before the test runs.
     * @return the properties to add
     */
    String[] properties() default {};

    /**
     * The bean definition profiles to activate.
     */
    @AliasFor(annotation = ActiveProfiles.class, attribute = "profiles")
    String[] profiles() default {"itest"};

    /**
     * The resource locations to use for loading WireMock mappings.
     * <p>
     * When none specified, <em>src/test/resources/mappings</em> is used as default
     * location.
     * </p>
     * <p>
     * To customize the location, this attribute must be set to the directory where
     * mappings are stored.
     * </p>
     * @return locations to read WireMock mappings from
     */
    @AliasFor(annotation = AutoConfigureWireMock.class, attribute = "stubs")
    String[] stubs() default { "" };

    /**
     * The resource locations to use for loading WireMock response bodies.
     * <p>
     * When none specified, <em>src/test/resources/__files</em> is used as default.
     * </p>
     * <p>
     * To customize the location, this attribute must be set to the parent directory of
     * <strong>__files</strong> directory.
     * </p>
     * @return locations to read WireMock response bodies from
     */
    @AliasFor(annotation = AutoConfigureWireMock.class, attribute = "files")
    String[] files() default { "" };

    /**
     * Determines if default filtering should be used with
     * {@link SpringBootApplication @SpringBootApplication}. By default only
     * {@code @Controller} (when no explicit {@link #controllers() controllers} are
     * defined), {@code @ControllerAdvice} and {@code WebMvcConfigurer} beans are
     * included.
     * @see #includeFilters()
     * @see #excludeFilters()
     * @return if default filters should be used
     */
    boolean useDefaultFilters() default true;

    /**
     * A set of include filters which can be used to add otherwise filtered beans to the
     * application context.
     * @return include filters to apply
     */
    ComponentScan.Filter[] includeFilters() default {};

    /**
     * A set of exclude filters which can be used to filter beans that would otherwise be
     * added to the application context.
     * @return exclude filters to apply
     */
    ComponentScan.Filter[] excludeFilters() default {};

    /**
     * Auto-configuration exclusions that should be applied for this test.
     * @return auto-configuration exclusions to apply
     */
    @AliasFor(annotation = ImportAutoConfiguration.class, attribute = "exclude")
    Class<?>[] excludeAutoConfiguration() default {};
}
