package com.nassau.checkinservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_checkins")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Checkin extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "classroom_id", referencedColumnName = "id")
    private ClassRoom classRoom;

    public Checkin(User user, ClassRoom classRoom) {
        this.user = user;
        this.classRoom = classRoom;
    }
}
