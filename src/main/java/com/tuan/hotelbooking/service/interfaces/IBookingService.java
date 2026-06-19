package com.tuan.hotelbooking.service.interfaces;

import com.tuan.hotelbooking.dto.Response;
import com.tuan.hotelbooking.entity.Booking;

public interface IBookingService {
    Response saveBooking(Long rooId, Long userId, Booking bookingRequest);
    Response findBookingByConfirmationCode(String confirmationCode);
    Response getAllBookings();
    Response cancelBooking(Long bookingId);
}
