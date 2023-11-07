package com.nassau.checkinservice.rest;


import com.nassau.checkinservice.constants.AppConstants;
import com.nassau.checkinservice.dto.classroom.ClassRoomDTO;
import com.nassau.checkinservice.dto.user.UserDTO;
import com.nassau.checkinservice.dto.user.UserFilterDTO;
import com.nassau.checkinservice.service.AbstractMessage;
import com.nassau.checkinservice.service.ClassRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = AppConstants.PATH + AppConstants.API + AppConstants.V1 + "/classrooms")
@RequiredArgsConstructor
public class ClassRoomResource extends AbstractMessage {

    private final ClassRoomService classRoomService;

    @PostMapping("/by-list")
    public ResponseEntity<List<ClassRoomDTO>> createOrUpdateByList(@RequestBody List<ClassRoomDTO> dtoList) throws Throwable {
        List<ClassRoomDTO> list = classRoomService.createOrUpdateByList(dtoList);
        return ResponseEntity
                .ok()
                .body(list);
    }

    @PostMapping
    public ResponseEntity<ClassRoomDTO> create(@RequestBody ClassRoomDTO dto) throws Throwable {
        dto = classRoomService.create(dto);
        return ResponseEntity
                .created(URI.create("/classrooms"))
                .body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassRoomDTO> update(@PathVariable Long id, @RequestBody ClassRoomDTO dto) throws Throwable {
        dto = classRoomService.update(id, dto);
        return ResponseEntity
                .ok()
                .body(dto);
    }

    @GetMapping
    public ResponseEntity<Page<ClassRoomDTO>> findByFilters(Pageable pageable, UserFilterDTO filtersDTO) {
        Page<ClassRoomDTO> page = classRoomService.findByFilters(pageable, filtersDTO);
        return ResponseEntity
                .ok()
                .body(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassRoomDTO> findById(@PathVariable Long id) throws Throwable {
        ClassRoomDTO classRoomDTO = classRoomService.findById(id);
        return ResponseEntity
                .ok()
                .body(classRoomDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws Throwable {
        classRoomService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}/generate")
    public ResponseEntity<byte[]> generateQRCode(@PathVariable Long id) throws Throwable {
        byte[] qrCode = classRoomService.generateQRCode(id);
        return ResponseEntity
                .ok()
                .body(qrCode);
    }

    @GetMapping("/{id}/check")
    public ResponseEntity<List<UserDTO>> findAllByCheck(@PathVariable Long id) {
        List<UserDTO> list = classRoomService.findCheck(id);
        return ResponseEntity
                .ok()
                .body(list);
    }
}