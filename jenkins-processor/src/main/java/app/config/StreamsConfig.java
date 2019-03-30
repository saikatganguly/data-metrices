package app.config;

import org.springframework.cloud.stream.annotation.EnableBinding;


@EnableBinding(SourceDestination.class)
public class StreamsConfig {
}
