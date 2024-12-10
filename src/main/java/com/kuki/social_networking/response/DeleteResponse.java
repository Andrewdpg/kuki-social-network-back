package com.kuki.social_networking.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteResponse {
    private String message;
    private boolean deleted;
}
