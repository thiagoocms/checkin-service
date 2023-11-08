package com.nassau.checkinservice.repository;

import com.nassau.checkinservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    User findFirstById(Long id);
    User findFirstByLoginIgnoreCaseAndPasswordAndDeletedIsFalse(String login, String password);

    @Query(value = "SELECT u.* FROM tb_checkins c \n" +
            "INNER JOIN tb_users u ON c.user_id = u.id \n" +
            "WHERE c.classroom_id = :classRoomId", nativeQuery = true)
    List<User> findAllByCheck(Long classRoomId);
}
