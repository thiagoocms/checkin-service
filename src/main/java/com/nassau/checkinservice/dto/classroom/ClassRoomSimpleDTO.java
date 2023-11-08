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
public class ClassRoomSimpleDTO implements Serializable {
    private Long id;
    private String name;
    private Long userId;
    private String userName;
    private Long startDate;
    private Long endDate;
    private String period;
    private String shift;
}
