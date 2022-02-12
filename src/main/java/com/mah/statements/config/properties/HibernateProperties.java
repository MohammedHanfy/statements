package com.mah.statements.config.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HibernateProperties {

    private String dialect;

    private String hbm2ddlAuto;

    private String ddlAuto;

    private String showSql;

    private String formatSql;
}
