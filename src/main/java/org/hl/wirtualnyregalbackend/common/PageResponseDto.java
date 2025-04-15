package org.hl.wirtualnyregalbackend.common;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import org.springframework.data.domain.Page;

import java.util.Map;

public class PageResponseDto<T> {
    public Map<String, Object> jsonData;

    public PageResponseDto(Page<T> page, String contentFieldName) {
        jsonData.put(contentFieldName, page.getContent());
        jsonData.put("totalPages", page.getTotalPages());
        jsonData.put("totalCount", page.getTotalElements());
        jsonData.put("count", page.getSize());
        jsonData.put("page", page.getNumber());
    }


    @JsonAnyGetter
    public Map<String, Object> getJsonData() {
        return jsonData;
    }

}
