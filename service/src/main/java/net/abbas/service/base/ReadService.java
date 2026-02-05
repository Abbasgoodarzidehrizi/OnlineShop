package net.abbas.service.base;

import org.springframework.data.domain.Page;


public interface ReadService <Dto>{
Page<Dto> readAll(Integer page, Integer size);
}
