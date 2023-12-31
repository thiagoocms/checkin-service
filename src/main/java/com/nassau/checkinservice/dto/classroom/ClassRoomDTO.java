package com.nassau.checkinservice.dto.classroom;

import com.nassau.checkinservice.domain.AbstractAuditingEntity;
import com.nassau.checkinservice.domain.User;
import com.nassau.checkinservice.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.DateTime;

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
    private Long startDate;
    private Long endDate;
    private String period;
    private String shift;
    private String qrCode;
}
