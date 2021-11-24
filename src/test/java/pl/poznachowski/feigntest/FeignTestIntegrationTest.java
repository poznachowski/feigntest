package pl.poznachowski.feigntest;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import pl.poznachowski.feigntest.app.TestFeignApplication;
import pl.poznachowski.feigntest.app.TestFeignClient;

@FeignTest(substituteProperties = "test.url")
@ContextConfiguration(classes = TestFeignApplication.class)
class FeignTestIntegrationTest {

    private static final String HELLO = "Hello!";

    @Autowired
    private TestFeignClient testFeignClient;

    @Test
    void test() {
        stubFor(get(urlEqualTo("/test"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withBody(HELLO)));

        assertThat(testFeignClient.getTestData()).isEqualTo(HELLO);
    }
}
