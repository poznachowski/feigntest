package pl.poznachowski.feigntest;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

import org.junit.jupiter.api.Test;
import org.springframework.cloud.openfeign.FeignLoggerFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import feign.Client;
import feign.Contract;
import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Retryer;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;

class FeignTypeExcludeFilterTest {

    private MetadataReaderFactory metadataReaderFactory = new SimpleMetadataReaderFactory();


    @Test
    void defaultIncludes() throws Exception {
        FeignTypeExcludeFilter filter = new FeignTypeExcludeFilter(DefaultFeignTest.class);
        assertThat(excludes(filter, ExampleController.class)).isTrue();
        assertThat(excludes(filter, ExampleRepository.class)).isTrue();
        assertThat(excludes(filter, ExampleService.class)).isTrue();
        assertThat(excludes(filter, ExampleRequestInterceptor.class)).isFalse();
        assertThat(excludes(filter, ExampleDecoder.class)).isFalse();
        assertThat(excludes(filter, ExampleEncoder.class)).isFalse();
        assertThat(excludes(filter, ExampleLogger.class)).isFalse();
        assertThat(excludes(filter, ExampleFeignLoggerFactory.class)).isFalse();
        assertThat(excludes(filter, ExampleContract.class)).isFalse();
        assertThat(excludes(filter, ExampleErrorDecoder.class)).isFalse();
        assertThat(excludes(filter, ExampleClient.class)).isFalse();
        assertThat(excludes(filter, ExampleRetryer.class)).isFalse();
    }

    @FeignTest
    static class DefaultFeignTest {
    }

    private boolean excludes(FeignTypeExcludeFilter filter, Class<?> type) throws IOException {
        MetadataReader metadataReader = this.metadataReaderFactory.getMetadataReader(type.getName());
        return filter.match(metadataReader, this.metadataReaderFactory);
    }

    @Controller
    static class ExampleController {
    }

    @Repository
    static class ExampleRepository {
    }

    @Service
    static class ExampleService {
    }

    static class ExampleRequestInterceptor implements RequestInterceptor {

        @Override
        public void apply(final RequestTemplate template) {
        }
    }

    static class ExampleDecoder extends Decoder.Default {
    }

    static class ExampleEncoder extends Encoder.Default {
    }

    static class ExampleLogger extends Logger.NoOpLogger {
    }

    static class ExampleFeignLoggerFactory implements FeignLoggerFactory {
        @Override
        public Logger create(final Class<?> type) {
            return null;
        }
    }
    static class ExampleContract extends Contract.Default {
    }

    static class ExampleErrorDecoder extends ErrorDecoder.Default {
    }

    static class ExampleClient extends Client.Default {
        public ExampleClient(
                final SSLSocketFactory sslContextFactory,
                final HostnameVerifier hostnameVerifier
        ) {
            super(sslContextFactory, hostnameVerifier);
        }
    }

    static class ExampleRetryer extends Retryer.Default {
    }
}
