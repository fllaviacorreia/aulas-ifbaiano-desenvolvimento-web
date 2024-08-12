package com.project.payment.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseAuth {
    private String token;
    private ProfileDTO profile;
}
