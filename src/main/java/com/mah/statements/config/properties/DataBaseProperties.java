package com.mah.statements.config.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DataBaseProperties {

    private String url;

    private String driverClassName;

    private String username;

    private String password;
}
