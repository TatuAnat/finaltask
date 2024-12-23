package org.example.fp.repository;

import org.example.fp.entity.Account;
import org.example.fp.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Integer> {

    List<Operation> findByUserId(Integer userId);  //From == null && To == null

    List<Operation> findByUserIdAndTimestampBefore(Integer userId, LocalDateTime to); //from == null, to != null

    List<Operation> findByUserIdAndTimestampAfter(Integer userId, LocalDateTime from); //to == null && from != null

    List<Operation> findByUserIdAndTimestampBetween(Integer userId, LocalDateTime from, LocalDateTime to);
}
