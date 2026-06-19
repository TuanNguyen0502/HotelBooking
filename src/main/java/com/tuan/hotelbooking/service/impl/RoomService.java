package com.tuan.hotelbooking.service.impl;

import com.tuan.hotelbooking.dto.Response;
import com.tuan.hotelbooking.dto.RoomDTO;
import com.tuan.hotelbooking.entity.Room;
import com.tuan.hotelbooking.exception.OurException;
import com.tuan.hotelbooking.repository.RoomRepository;
import com.tuan.hotelbooking.service.interfaces.ICloudinaryService;
import com.tuan.hotelbooking.service.interfaces.IRoomService;
import com.tuan.hotelbooking.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService implements IRoomService {
    private final RoomRepository roomRepository;
    private final ICloudinaryService cloudinaryService;

    @Override
    public Response addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice, String description) {
        Response response = new Response();

        try {
            String imageUrl = cloudinaryService.uploadImage(photo);

            Room room = new Room();
            room.setRoomPhotoUrl(imageUrl);
            room.setRoomType(roomType);
            room.setRoomPrice(roomPrice);
            room.setDescription(description);
            Room savedRoom = roomRepository.save(room);

            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(savedRoom);
            response.setRoom(roomDTO);
            response.setMessage("successful");
            response.setStatusCode(200);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error saving a room " + e.getMessage());
        }
        return response;
    }

    @Override
    public List<String> getAllRoomTypes() {
        return roomRepository.findDistinctRoomTypes();
    }

    @Override
    public Response getAllRooms() {
        Response response = new Response();

        try {
            List<Room> roomList = roomRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(roomList);
            response.setMessage("successful");
            response.setStatusCode(200);
            response.setRooms(roomDTOList);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting all rooms " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteRoom(Long roomId) {
        Response response = new Response();

        try {
            Room room = roomRepository.findById(roomId).orElseThrow(() -> new OurException("Room Not Found"));
            cloudinaryService.deleteImage(room.getRoomPhotoUrl());
            roomRepository.deleteById(roomId);
            response.setMessage("successful");
            response.setStatusCode(200);
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error deleting a room " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response updateRoom(Long roomId, String description, String roomType, BigDecimal roomPrice, MultipartFile photo) {
        Response response = new Response();

        try {
            Room room = roomRepository.findById(roomId).orElseThrow(() -> new OurException("Room Not Found"));
            if (roomType != null) room.setRoomType(roomType);
            if (roomPrice != null) room.setRoomPrice(roomPrice);
            if (description != null) room.setDescription(description);
            String imageUrl = null;
            if (photo != null && !photo.isEmpty()) {
                imageUrl = cloudinaryService.updateImage(photo, room.getRoomPhotoUrl());
            }
            if (imageUrl != null) room.setRoomPhotoUrl(imageUrl);

            Room updatedRoom = roomRepository.save(room);
            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(updatedRoom);
            response.setMessage("successful");
            response.setStatusCode(200);
            response.setRoom(roomDTO);
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error updating a room " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getRoomById(Long roomId) {
        Response response = new Response();

        try {
            Room room = roomRepository.findById(roomId).orElseThrow(() -> new OurException("Room Not Found"));
            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTOPlusBookings(room);
            response.setMessage("successful");
            response.setStatusCode(200);
            response.setRoom(roomDTO);
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Getting a room By Id " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAvailableRoomsByDateAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {
        Response response = new Response();

        try {
            List<Room> availableRooms = roomRepository.findAvailableRoomsByDateAndTypes(checkInDate, checkOutDate, roomType);
            List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(availableRooms);
            response.setMessage("successful");
            response.setStatusCode(200);
            response.setRooms(roomDTOList);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting available rooms " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllAvailableRooms() {
        Response response = new Response();

        try {
            List<Room> roomList = roomRepository.getAllAvailableRooms();
            List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(roomList);
            response.setMessage("successful");
            response.setStatusCode(200);
            response.setRooms(roomDTOList);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting available rooms " + e.getMessage());
        }
        return response;
    }
}
