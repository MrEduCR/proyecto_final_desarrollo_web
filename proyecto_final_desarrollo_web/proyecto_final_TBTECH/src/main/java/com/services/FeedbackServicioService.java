package com.services;

import com.domain.FeedbackServicio;
import com.repository.FeedbackServicioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class FeedbackServicioService {

    private final FeedbackServicioRepository repo;

    public FeedbackServicioService(FeedbackServicioRepository repo) {
        this.repo = repo;
    }

    public List<FeedbackServicio> findAll() {
        return repo.findAll();
    }

    public Optional<FeedbackServicio> findById(Long id) {
        return repo.findById(id);
    }

    public FeedbackServicio save(FeedbackServicio s) {
        return repo.save(s);
    }
}
