package com.adwork.adworktest.db.services;

import com.adwork.adworktest.db.entities.Response;
import com.adwork.adworktest.db.entities.User;

public interface IUserService {
    User findByIdentification(String identificacion);
    Response saveOrUpdateUser(User user);
}
