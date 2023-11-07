package com.nassau.checkinservice.rest;


import com.nassau.checkinservice.constants.AppConstants;
import com.nassau.checkinservice.dto.checkin.CheckinDTO;
import com.nassau.checkinservice.dto.user.UserDTO;
import com.nassau.checkinservice.dto.user.UserFilterDTO;
import com.nassau.checkinservice.dto.user.UserLoginDTO;
import com.nassau.checkinservice.service.AbstractMessage;
import com.nassau.checkinservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = AppConstants.PATH + AppConstants.API + AppConstants.V1 + "/users")
@RequiredArgsConstructor
public class UserResource extends AbstractMessage {

    private final UserService userService;

    @PostMapping("/by-list")
    public ResponseEntity<List<UserDTO>> createOrUpdateByList(@RequestBody List<UserDTO> dtoList) throws Throwable {
        List<UserDTO> list = userService.createOrUpdateByList(dtoList);
        return ResponseEntity
                .ok()
                .body(list);
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO dto) throws IOException, IllegalAccessException {
        dto = userService.create(dto);
        return ResponseEntity
                .created(URI.create("/users"))
                .body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody UserDTO dto) throws Throwable {
        dto = userService.update(id, dto);
        return ResponseEntity
                .ok()
                .body(dto);
    }

    @GetMapping
    public ResponseEntity<Page<UserDTO>> findByFilters(Pageable pageable, UserFilterDTO filtersDTO) {
        Page<UserDTO> page = userService.findByFilters(pageable, filtersDTO);
        return ResponseEntity
                .ok()
                .body(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) throws Throwable {
        UserDTO userDTO = userService.findById(id);
        return ResponseEntity
                .ok()
                .body(userDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws Throwable {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody UserLoginDTO dto) throws Throwable {
        UserDTO userDTO = userService.login(dto);
        return ResponseEntity
                .ok()
                .body(userDTO);
    }

    @PutMapping("/{id}/check/{classRoomId}")
    public ResponseEntity<CheckinDTO> check(@PathVariable Long id, @PathVariable Long classRoomId) throws Throwable {
        CheckinDTO dto = userService.check(id, classRoomId);
        return ResponseEntity
                .ok()
                .body(dto);
    }
}