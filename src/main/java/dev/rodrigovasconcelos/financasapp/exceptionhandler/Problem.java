package dev.rodrigovasconcelos.financasapp.exceptionhandler;

import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@Builder
public class Problem {

    private Integer status;

    private String title;

    private String detail;

    private String userMessage;

    private OffsetDateTime timestamp;
}
