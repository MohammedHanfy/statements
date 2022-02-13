package com.mah.statements.service.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccountModel {

    private Long id;

    private String accountType;

    private String accountNumber;
}
