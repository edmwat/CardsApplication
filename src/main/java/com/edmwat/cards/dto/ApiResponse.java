package com.edmwat.cards.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
@Component
@Data
public class ApiResponse {
    private String statusCode;
    private boolean status;
    private String statusMessage;
    private String statusDescription;
    private Collection<?> model;

    public ApiResponse(){}
    public ApiResponse(String statusCode, boolean status, String statusMessage, String statusDescription, Collection<?> model) {
        this.statusCode = statusCode;
        this.status = status;
        this.statusMessage = statusMessage;
        this.statusDescription = statusDescription;
        this.model = new ArrayList<>(model != null ? model : null);
    }
    public ApiResponse(String statusCode, boolean status, String statusMessage, String statusDescription) {
        this.statusCode = statusCode;
        this.status = status;
        this.statusMessage = statusMessage;
        this.statusDescription = statusDescription;
    }
}