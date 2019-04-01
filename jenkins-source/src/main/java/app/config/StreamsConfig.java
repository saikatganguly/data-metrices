package app.config;

import org.springframework.cloud.stream.annotation.EnableBinding;


@EnableBinding(Topics.class)
public class StreamsConfig {
}
