package com.wasin.backend.service;

import com.wasin.backend.domain.dto.RouterRequest;
import com.wasin.backend.domain.dto.RouterResponse;
import com.wasin.backend.domain.entity.User;

public interface RouterService {

    RouterResponse.FindALl findAll(User user);

    RouterResponse.FindByRouterId findByRouterId(User user, Long routerId);

    void create(User user, RouterRequest.CreateDTO requestDTO);

    void update(User user, RouterRequest.UpdateDTO requestDTO, Long routerId);

    void delete(User user, Long routerId);

}
