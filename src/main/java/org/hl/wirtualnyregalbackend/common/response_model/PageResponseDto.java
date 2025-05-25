package org.hl.wirtualnyregalbackend.common.response_model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.Map;

public class PageResponseDto<T> {

    @JsonProperty
    private final Map<String, Object> jsonData;

    public PageResponseDto(Page<T> page, String contentFieldName) {
        jsonData = new HashMap<>();
        jsonData.put(contentFieldName, page.getContent());
        jsonData.put("totalPages", page.getTotalPages());
        jsonData.put("totalCount", page.getTotalElements());
        jsonData.put("count", page.getSize());
        jsonData.put("page", page.getNumber());
    }

}
