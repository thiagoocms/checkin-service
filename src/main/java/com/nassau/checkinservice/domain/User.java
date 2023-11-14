package com.nassau.checkinservice.domain;

import com.nassau.checkinservice.enumerated.UserDocumentTypeEnum;
import com.nassau.checkinservice.enumerated.UserProfileEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "document_number", length = 50, nullable = false)
    private String documentNumber;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "document_type", nullable = false)
    private UserDocumentTypeEnum documentType;

    @Column(name = "login", length = 255, nullable = false)
    private String login;

    @Column(name = "password", length = 20, nullable = false)
    private String password;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "profile", nullable = false)
    private UserProfileEnum profile;
}
