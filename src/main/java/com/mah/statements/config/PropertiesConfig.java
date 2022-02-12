package com.mah.statements.config;

import com.mah.statements.config.properties.DataBaseProperties;
import com.mah.statements.config.properties.HibernateProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "properties")
public class PropertiesConfig {

    private DataBaseProperties dataBaseProperties;

    private HibernateProperties hibernateProperties;
}
