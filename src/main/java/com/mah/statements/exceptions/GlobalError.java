package com.mah.statements.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GlobalError {

    private String errorMessage;

    private List<String> errorMessages;

    private LocalDate timestamp;
}
