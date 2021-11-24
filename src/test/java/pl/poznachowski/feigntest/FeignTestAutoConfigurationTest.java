package pl.poznachowski.feigntest;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.poznachowski.feigntest.AutoConfigurationImportedCondition.importedAutoConfiguration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.cloud.openfeign.encoding.FeignAcceptGzipEncodingAutoConfiguration;
import org.springframework.cloud.openfeign.encoding.FeignContentGzipEncodingAutoConfiguration;
import org.springframework.cloud.openfeign.hateoas.FeignHalAutoConfiguration;
import org.springframework.cloud.openfeign.loadbalancer.FeignLoadBalancerAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import pl.poznachowski.feigntest.app.TestFeignApplication;

@FeignTest
@ContextConfiguration(classes = TestFeignApplication.class)
class FeignTestAutoConfigurationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void feignAutoConfigurationWasImported() {
        assertThat(this.applicationContext).has(importedAutoConfiguration(FeignAutoConfiguration.class));
    }

    @Test
    void feignHalAutoConfigurationWasImported() {
        assertThat(this.applicationContext).has(importedAutoConfiguration(FeignHalAutoConfiguration.class));
    }

    @Test
    void feignLoadBalancerAutoConfigurationWasImported() {
        assertThat(this.applicationContext).has(importedAutoConfiguration(FeignLoadBalancerAutoConfiguration.class));
    }

    @Test
    void feignAcceptGzipEncodingAutoConfigurationWasImported() {
        assertThat(this.applicationContext).has(importedAutoConfiguration(FeignAcceptGzipEncodingAutoConfiguration.class));
    }

    @Test
    void feignContentGzipEncodingAutoConfigurationWasImported() {
        assertThat(this.applicationContext).has(importedAutoConfiguration(FeignContentGzipEncodingAutoConfiguration.class));
    }

    @Test
    void httpMessageConvertersAutoConfigurationWasImported() {
        assertThat(this.applicationContext).has(importedAutoConfiguration(HttpMessageConvertersAutoConfiguration.class));
    }
}
