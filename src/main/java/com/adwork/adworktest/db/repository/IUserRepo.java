package com.adwork.adworktest.db.repository;

import com.adwork.adworktest.db.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepo extends JpaRepository<User, String> {
    User findByIdentificacion(String identificacion);
}
