package com.kuki.social_networking.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TagRequest extends PageableRequest {
    String name;
}
