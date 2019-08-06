package tech.victoryw.okhttp.unexpected.end.stream.fix.assemble;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        packages("tech.victoryw.okhttp.unexpected.end.stream.fix.controller");
    }
}
