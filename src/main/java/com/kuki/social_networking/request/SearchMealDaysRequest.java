package com.kuki.social_networking.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SearchMealDaysRequest extends PageableRequest {

    private String sortBy = "id.day";
    private Integer fromDay;
    private Integer toDay;

}
