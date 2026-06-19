package com.tuan.hotelbooking.service.interfaces;

import com.tuan.hotelbooking.dto.LoginRequest;
import com.tuan.hotelbooking.dto.Response;
import com.tuan.hotelbooking.entity.User;

public interface IUserService {
    Response register(User user);

    Response login(LoginRequest loginRequest);

    Response getAllUsers();

    Response getUSerBookingHistory(String userId);

    Response deleteUser(String userId);

    Response getUserById(String userId);

    Response getMyInfo(String email);
}
