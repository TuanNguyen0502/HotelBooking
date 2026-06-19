package com.tuan.hotelbooking.common.utils;

import com.tuan.hotelbooking.dto.BookingDTO;
import com.tuan.hotelbooking.dto.RoomDTO;
import com.tuan.hotelbooking.dto.UserDTO;
import com.tuan.hotelbooking.entity.Booking;
import com.tuan.hotelbooking.entity.Room;
import com.tuan.hotelbooking.entity.User;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    private static final String ALPHANUMERIC_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom secureRandom = new SecureRandom();

    public static String generateRandomConfirmationCode(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(ALPHANUMERIC_CHARACTERS.length());
            sb.append(ALPHANUMERIC_CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }

    public static UserDTO mapUserEntityToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    public static RoomDTO mapRoomEntityToRoomDTO(Room room) {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(room.getId());
        roomDTO.setRoomType(room.getRoomType());
        roomDTO.setRoomPrice(room.getRoomPrice());
        roomDTO.setRoomPhotoUrl(room.getRoomPhotoUrl());
        roomDTO.setRoomDescription(room.getDescription());
        return roomDTO;
    }

    public static BookingDTO mapBookingEntityToBookingDTO(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(booking.getId());
        bookingDTO.setCheckInDate(booking.getCheckInDate());
        bookingDTO.setCheckOutDate(booking.getCheckOutDate());
        bookingDTO.setNumberOfChildren(booking.getNumberOfChildren());
        bookingDTO.setNumberOfAdults(booking.getNumberOfAdults());
        bookingDTO.setTotalNumberOfGuest(booking.getTotalNumberOfGuest());
        bookingDTO.setBookingConfirmationCode(booking.getBookingConfirmationCode());
        return bookingDTO;
    }

    public static RoomDTO mapRoomEntityToRoomDTOPlusBookings(Room room) {
        RoomDTO roomDTO = mapRoomEntityToRoomDTO(room);
        if (room.getBookings() != null) {
            roomDTO.setBookings(room.getBookings().stream()
                    .map(Utils::mapBookingEntityToBookingDTO)
                    .collect(Collectors.toList()));
        }
        return roomDTO;
    }

    public static BookingDTO mapBookingEntityToBookingDTOPlusBookedRooms(Booking booking, boolean mapUser) {
        BookingDTO bookingDTO = mapBookingEntityToBookingDTO(booking);
        if (mapUser) {
            bookingDTO.setUser(Utils.mapUserEntityToUserDTO(booking.getUser()));
        }
        if (booking.getRoom() != null) {
            RoomDTO roomDTO = new RoomDTO();
            roomDTO.setId(booking.getRoom().getId());
            roomDTO.setRoomType(booking.getRoom().getRoomType());
            roomDTO.setRoomPrice(booking.getRoom().getRoomPrice());
            roomDTO.setRoomPhotoUrl(booking.getRoom().getRoomPhotoUrl());
            roomDTO.setRoomDescription(booking.getRoom().getDescription());
            bookingDTO.setRoom(roomDTO);
        }
        return bookingDTO;
    }

    public static UserDTO mapUserEntityToUserDTOPlusUserBookingsAndRoom(User user) {
        UserDTO userDTO = mapUserEntityToUserDTO(user);

        if (!user.getBookings().isEmpty()) {
            userDTO.setBookings(user.getBookings().stream()
                    .map(booking -> mapBookingEntityToBookingDTOPlusBookedRooms(booking, false))
                    .collect(Collectors.toList()));
        }
        return userDTO;
    }

    public static List<UserDTO> mapUserListEntityToUserListDTO(List<User> userList) {
        return userList.stream().map(Utils::mapUserEntityToUserDTO).collect(Collectors.toList());
    }

    public static List<RoomDTO> mapRoomListEntityToRoomListDTO(List<Room> roomList) {
        return roomList.stream().map(Utils::mapRoomEntityToRoomDTO).collect(Collectors.toList());
    }

    public static List<BookingDTO> mapBookingListEntityToBookingListDTO(List<Booking> bookingList) {
        return bookingList.stream().map(Utils::mapBookingEntityToBookingDTO).collect(Collectors.toList());
    }
}
