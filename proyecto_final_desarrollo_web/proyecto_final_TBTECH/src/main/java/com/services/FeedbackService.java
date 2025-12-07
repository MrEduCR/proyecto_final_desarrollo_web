package com.services;

import com.domain.Feedback;
import com.domain.FeedbackServicio;
import com.repository.FeedbackRepository;
import com.repository.FeedbackServicioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final FeedbackServicioRepository feedbackServicioRepository;

    public FeedbackService(FeedbackRepository feedbackRepository,
            FeedbackServicioRepository feedbackServicioRepository) {
        this.feedbackRepository = feedbackRepository;
        this.feedbackServicioRepository = feedbackServicioRepository;
    }

    public Feedback saveFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public List<Feedback> findAll() {
        return feedbackRepository.findAll();
    }

    public List<Feedback> findByServicioId(Long servicioId) {
        return feedbackRepository.findByFeedbackServicio_Id(servicioId);
    }

    public Optional<Feedback> findById(Long id) {
        return feedbackRepository.findById(id);
    }

    public void deleteById(Long id) {
        feedbackRepository.deleteById(id);
    }

    public List<FeedbackServicio> findAllFeedbackServicios() {
        return feedbackServicioRepository.findAll();
    }

    public Optional<FeedbackServicio> findFeedbackServicioById(Long id) {
        return feedbackServicioRepository.findById(id);
    }

}
