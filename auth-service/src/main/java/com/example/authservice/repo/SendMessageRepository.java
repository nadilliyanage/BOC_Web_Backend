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

        List<SendMessage> findByStatusNotIn(List<String> statuses);

        @Query("SELECT sm FROM SendMessage sm WHERE sm.created_by_id = :userId")
        List<SendMessage> findByCreated_by_id(@Param("userId") Integer userId);

        @Query("SELECT sm FROM SendMessage sm WHERE sm.status = :status AND sm.created_by_id = :userId")
        List<SendMessage> findByStatusAndCreated_by_id(@Param("status") String status, @Param("userId") Integer userId);

        @Query("SELECT sm FROM SendMessage sm WHERE sm.referenceNumber IS NOT NULL AND sm.created_by_id = :userId")
        List<SendMessage> findByReferenceNumberIsNotNullAndCreated_by_id(@Param("userId") Integer userId);

        @Query("SELECT sm FROM SendMessage sm WHERE sm.status NOT IN :statuses AND sm.created_by_id = :userId")
        List<SendMessage> findByStatusNotInAndCreated_by_id(@Param("statuses") List<String> statuses,
                        @Param("userId") Integer userId);

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

        @Query("SELECT new com.example.authservice.dto.MessageCountByDateDTO(DATE(sm.createdAt), COUNT(sm)) " +
                        "FROM SendMessage sm " +
                        "WHERE YEAR(sm.createdAt) = :year AND MONTH(sm.createdAt) = :month AND sm.created_by_id = :userId "
                        +
                        "GROUP BY DATE(sm.createdAt) " +
                        "ORDER BY DATE(sm.createdAt)")
        List<MessageCountByDateDTO> findMessageCountByDateAndUserId(@Param("year") int year, @Param("month") int month,
                        @Param("userId") Integer userId);

        @Query("SELECT new com.example.authservice.dto.MessageCountByMonthDTO(MONTH(sm.createdAt), COUNT(sm)) " +
                        "FROM SendMessage sm " +
                        "WHERE YEAR(sm.createdAt) = :year " +
                        "GROUP BY MONTH(sm.createdAt) " +
                        "ORDER BY MONTH(sm.createdAt)")
        List<MessageCountByMonthDTO> findMessageCountByMonth(@Param("year") int year);

        @Query("SELECT new com.example.authservice.dto.MessageCountByMonthDTO(MONTH(sm.createdAt), COUNT(sm)) " +
                        "FROM SendMessage sm " +
                        "WHERE YEAR(sm.createdAt) = :year AND sm.created_by_id = :userId " +
                        "GROUP BY MONTH(sm.createdAt) " +
                        "ORDER BY MONTH(sm.createdAt)")
        List<MessageCountByMonthDTO> findMessageCountByMonthAndUserId(@Param("year") int year,
                        @Param("userId") Integer userId);

        @Query("SELECT new com.example.authservice.dto.MessageCountByYearDTO(YEAR(sm.createdAt), COUNT(sm)) " +
                        "FROM SendMessage sm " +
                        "GROUP BY YEAR(sm.createdAt) " +
                        "ORDER BY YEAR(sm.createdAt)")
        List<MessageCountByYearDTO> findMessageCountByYear();

        @Query("SELECT new com.example.authservice.dto.MessageCountByYearDTO(YEAR(sm.createdAt), COUNT(sm)) " +
                        "FROM SendMessage sm " +
                        "WHERE sm.created_by_id = :userId " +
                        "GROUP BY YEAR(sm.createdAt) " +
                        "ORDER BY YEAR(sm.createdAt)")
        List<MessageCountByYearDTO> findMessageCountByYearAndUserId(@Param("userId") Integer userId);
}