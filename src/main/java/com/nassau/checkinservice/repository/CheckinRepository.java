package com.nassau.checkinservice.repository;

import com.nassau.checkinservice.domain.Checkin;
import com.nassau.checkinservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckinRepository extends JpaRepository<Checkin, Long>{
    List<Checkin> findAllByUserIdAndDeletedIsFalse(Long id);
}
