package org.linitly.boot.base.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "info")
public class InfoProperties {

    private String author;

    private String version;

    private String title;

    private String description;
}
