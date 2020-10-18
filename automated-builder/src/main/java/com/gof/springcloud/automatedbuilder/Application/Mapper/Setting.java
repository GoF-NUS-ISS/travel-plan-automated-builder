package com.gof.springcloud.automatedbuilder.Application.Mapper;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "setting")
public class Setting {
    //@Value("${spring.application.setting.dayduration}")
    private int dayDuration;

    //@Value("${spring.application.setting.hrspastmidnight}")
    private int hrsPastMidnight;

    //@Value("${spring.application.setting.timezone}")
    private String timeZone;

    //@Value("${spring.application.setting.dateformat}")
    private String dateFormat;
}
