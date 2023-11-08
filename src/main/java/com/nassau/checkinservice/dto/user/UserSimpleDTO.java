package com.nassau.checkinservice.dto.user;

import com.nassau.checkinservice.enumerated.UserDocumentTypeEnum;
import com.nassau.checkinservice.enumerated.UserProfileEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserSimpleDTO implements Serializable {
    private Long id;
    private String name;
    private String documentNumber;
    private String login;
}
