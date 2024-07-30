package com.wasin.backend.repository;

import com.wasin.backend.domain.entity.Email;
import org.springframework.data.repository.CrudRepository;

public interface MailRepository extends CrudRepository<Email, String> {

}
