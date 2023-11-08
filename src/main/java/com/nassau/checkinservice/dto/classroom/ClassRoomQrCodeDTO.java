package com.nassau.checkinservice.dto.classroom;

import com.nassau.checkinservice.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClassRoomQrCodeDTO implements Serializable {
    private Long id;
    private String qrCode;
}
