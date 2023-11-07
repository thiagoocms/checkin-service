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

    @Query(value = "SELECT users.* FROM tb_checkins check \n" +
            "INNER JOIN tb_users users ON check.user_id = users.id \n" +
            "INNER JOIN tb_classroom classroom ON check.classroom_id = classroom.id \n" +
            "WHERE classroom.id = ?1", nativeQuery = true)
    List<User> findAllByCheck(Long classRoomId);
}
