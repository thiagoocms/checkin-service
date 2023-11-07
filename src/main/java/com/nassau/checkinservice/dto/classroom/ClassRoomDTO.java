package com.nassau.checkinservice.dto.classroom;

import com.nassau.checkinservice.domain.AbstractAuditingEntity;
import com.nassau.checkinservice.domain.User;
import com.nassau.checkinservice.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClassRoomDTO implements Serializable {
    private Long id;
    private String name;
    private UserDTO user;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String period;
    private String shift;
    private  byte[] qrCode;
}
