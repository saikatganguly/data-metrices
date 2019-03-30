package app.config;

import app.sender.OutputStream;
import org.springframework.cloud.stream.annotation.EnableBinding;


@EnableBinding(OutputStream.class)
public class StreamsConfig {
}
