package com.tuan.hotelbooking.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    private int statusCode;
    private String message;
    private String token;
    private String role;
    private String expirationTime;
    private String bookingConfirmationCode;
    private UserResponse user;
    private RoomResponse room;
    private BookingResponse booking;
    private List<UserResponse> users;
    private List<RoomResponse> rooms;
    private List<BookingResponse> bookings;
}
