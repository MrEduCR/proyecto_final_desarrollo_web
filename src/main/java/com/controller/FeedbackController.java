package com.controller;

import com.domain.Feedback;
import com.services.FeedbackService;
import com.services.FeedbackServicioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final FeedbackServicioService feedbackServicioService;

    public FeedbackController(FeedbackService feedbackService, FeedbackServicioService feedbackServicioService) {
        this.feedbackService = feedbackService;
        this.feedbackServicioService = feedbackServicioService;
    }

    @GetMapping("/lista")
    public String listaFeedback(Model model) {
        model.addAttribute("feedbacks", feedbackService.findAll());
        return "feedback/feedback_list";
    }

    @GetMapping("/nuevo")
    public String nuevoFeedbackForm(@RequestParam(required = false) Long servicioId, Model model) {
        Feedback feedback = new Feedback();
        if (servicioId != null) {
            feedbackServicioService.findById(servicioId).ifPresent(feedback::setFeedbackServicio);
        }
        model.addAttribute("feedback", feedback);
        model.addAttribute("servicios", feedbackServicioService.findAll());
        return "feedback/feedback_form";
    }

    @PostMapping("/guardar")
    public String guardarFeedback(@Valid @ModelAttribute("feedback") Feedback feedback,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("servicios", feedbackServicioService.findAll());
            return "feedback/feedback_form";
        }

        if (feedback.getFeedbackServicio() != null && feedback.getFeedbackServicio().getId() != null) {
            feedbackServicioService.findById(feedback.getFeedbackServicio().getId())
                    .ifPresent(feedback::setFeedbackServicio);
        } else {
            feedback.setFeedbackServicio(null);
        }

        feedbackService.saveFeedback(feedback);
        return "redirect:/feedback/lista";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        feedbackService.deleteById(id);
        return "redirect:/feedback/lista";
    }
}
