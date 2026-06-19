package com.tuan.hotelbooking.dto;

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
    private UserDTO user;
    private RoomDTO room;
    private BookingDTO booking;
    private List<UserDTO> users;
    private List<RoomDTO> rooms;
    private List<BookingDTO> bookings;
}
