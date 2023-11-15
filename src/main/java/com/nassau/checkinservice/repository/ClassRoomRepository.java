package com.nassau.checkinservice.repository;

import com.nassau.checkinservice.domain.ClassRoom;
import com.nassau.checkinservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRoomRepository extends JpaRepository<ClassRoom, Long>, JpaSpecificationExecutor<ClassRoom> {
    ClassRoom findFirstById(Long id);

    @Query(value = "SELECT cr.* FROM tb_checkins c \n" +
            "INNER JOIN tb_classrooms cr ON c.classroom_id = cr.id \n" +
            "WHERE c.user_id = :userId", nativeQuery = true)
    List<ClassRoom> findAllByCheckinUserId(Long userId);
}
