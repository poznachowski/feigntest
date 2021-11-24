package pl.poznachowski.feigntest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;

import pl.poznachowski.feigntest.app.TestFeignApplication;

@FeignTest(substituteProperties = "test.url")
@ContextConfiguration(classes = TestFeignApplication.class)
class FeignTestPropertiesTest {

    @Autowired
    private Environment environment;

    @Test
    void substitutedProperty() {
        var port = environment.getProperty("wiremock.server.port");
        assertThat(environment.getProperty("test.url")).isEqualTo("http://localhost:".concat(port));
    }

    @Test
    void defaultProfile() {
        assertThat(environment.getActiveProfiles()).containsExactly("itest");
    }
}
