package com.repository;

import com.domain.Feedback;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    List<Feedback> findByFeedbackServicio_Id(Long servicioId);
}
