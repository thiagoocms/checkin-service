package com.nassau.checkinservice.dto.classroom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClassRoomFilterDTO implements Serializable {
    private Long id;
    private String name;
    private Long userId;
    private Long startDate;
    private Long endDate;
    private String period;
    private String shift;
    private String qrCode;
}
