package com.example.authservice.repo;

import com.example.authservice.dto.MessageCountByDateDTO;
import com.example.authservice.dto.MessageCountByMonthDTO;
import com.example.authservice.dto.MessageCountByYearDTO;
import com.example.authservice.model.CreateMessage;
import com.example.authservice.model.SendMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SendMessageRepository extends JpaRepository<SendMessage, Long> {
    List<SendMessage> findByStatusAndScheduleBefore(String status, LocalDateTime schedule);

    List<SendMessage> findByStatus(String status);

    List<SendMessage> findByReferenceNumberIsNotNull();


    @Query("SELECT new com.example.authservice.dto.MessageCountByDateDTO(DATE(sm.createdAt), COUNT(sm)) " +
            "FROM SendMessage sm " +
            "GROUP BY DATE(sm.createdAt) " +
            "ORDER BY DATE(sm.createdAt)")
    List<MessageCountByDateDTO> findMessageCountByDate();


    @Query("SELECT new com.example.authservice.dto.MessageCountByDateDTO(DATE(sm.createdAt), COUNT(sm)) " +
            "FROM SendMessage sm " +
            "WHERE YEAR(sm.createdAt) = :year AND MONTH(sm.createdAt) = :month " +
            "GROUP BY DATE(sm.createdAt) " +
            "ORDER BY DATE(sm.createdAt)")
    List<MessageCountByDateDTO> findMessageCountByDate(@Param("year") int year, @Param("month") int month);

    @Query("SELECT new com.example.authservice.dto.MessageCountByMonthDTO(MONTH(sm.createdAt), COUNT(sm)) " +
            "FROM SendMessage sm " +
            "WHERE YEAR(sm.createdAt) = :year " +
            "GROUP BY MONTH(sm.createdAt) " +
            "ORDER BY MONTH(sm.createdAt)")
    List<MessageCountByMonthDTO> findMessageCountByMonth(@Param("year") int year);

    @Query("SELECT new com.example.authservice.dto.MessageCountByYearDTO(YEAR(sm.createdAt), COUNT(sm)) " +
            "FROM SendMessage sm " +
            "GROUP BY YEAR(sm.createdAt) " +
            "ORDER BY YEAR(sm.createdAt)")
    List<MessageCountByYearDTO> findMessageCountByYear();

}