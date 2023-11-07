package com.nassau.checkinservice.dto.checkin;

import com.nassau.checkinservice.domain.AbstractAuditingEntity;
import com.nassau.checkinservice.dto.classroom.ClassRoomDTO;
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
public class CheckinDTO extends AbstractAuditingEntity implements Serializable {
    private Long id;
    private UserDTO user;
    private ClassRoomDTO classRoom;
}
