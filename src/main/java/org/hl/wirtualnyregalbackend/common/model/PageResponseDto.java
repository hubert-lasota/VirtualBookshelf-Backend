package org.hl.wirtualnyregalbackend.common.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.Map;

public class PageResponseDto<T> {
    private final Map<String, Object> jsonData;

    public PageResponseDto(Page<T> page, String contentFieldName) {
        jsonData = new HashMap<>();
        jsonData.put(contentFieldName, page.getContent());
        jsonData.put("totalPages", page.getTotalPages());
        jsonData.put("totalCount", page.getTotalElements());
        jsonData.put("count", page.getNumberOfElements());
        jsonData.put("page", page.getNumber());
    }


    @JsonAnyGetter
    public Map<String, Object> getJsonData() {
        return jsonData;
    }

}
