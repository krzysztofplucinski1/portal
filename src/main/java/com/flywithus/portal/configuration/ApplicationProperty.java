package com.flywithus.portal.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "flywithus")
public class ApplicationProperty {
    private Integer refundPercentage;
    private Integer cancelDays;
}
