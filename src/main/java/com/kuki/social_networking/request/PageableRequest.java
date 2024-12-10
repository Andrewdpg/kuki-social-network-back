package com.kuki.social_networking.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

import static com.kuki.social_networking.util.EntitySpecs.getPageable;

@Data
@Setter
@NoArgsConstructor
public class PageableRequest {
    private int page = 0;
    private int pageSize = 10;
    private String direction = "asc";
    private String sortBy = "id";

    public Pageable pageable() {
        return getPageable(this);
    }
}
