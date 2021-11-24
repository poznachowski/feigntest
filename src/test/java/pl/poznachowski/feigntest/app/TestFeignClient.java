package pl.poznachowski.feigntest.app;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "test-client", url = "${test.url}")
public interface TestFeignClient {

    @GetMapping("/test")
    String getTestData();
}
