package com.main.api.service;

import com.base.common.model.Content;
import com.base.common.payload.AddContentRequest;
import com.base.common.payload.EditContentRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContentService {
    Content add(AddContentRequest addConentRequest);
    Content edit(EditContentRequest editContentRequest);
    int delete(Long id);
    Page<Content> find(Pageable pageable);
}
