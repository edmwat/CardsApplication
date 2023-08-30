package com.edmwat.cards.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private String statusCode;
    private String description;
    private String cause;

    public ErrorResponse(String message, String statusCode, String description) {
        this.message = message;
        this.statusCode = statusCode;
        this.description = description;
    }


}
