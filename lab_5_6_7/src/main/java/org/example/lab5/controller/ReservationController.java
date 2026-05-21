package org.example.lab5.controller;

import java.util.Set;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpSession;

import org.example.lab5.entity.Book;
import org.example.lab5.entity.Reader;
import org.example.lab5.service.BookService;
import org.example.lab5.service.ReaderService;
import org.example.lab5.service.ReservationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/reservations")
public class ReservationController {
    private static final String CURRENT_READER_ID = "currentReaderId";

    private final ReaderService readerService;
    private final BookService bookService;
    private final ReservationService reservationService;

    public ReservationController(ReaderService readerService,
                                 BookService bookService,
                                 ReservationService reservationService) {
        this.readerService = readerService;
        this.bookService = bookService;
        this.reservationService = reservationService;
    }

    @GetMapping
    public String page(HttpSession session, Model model) {
        model.addAttribute("readers", readerService.findAll());
        model.addAttribute("books", bookService.findAll(null));
        Long selectedReaderId = (Long) session.getAttribute(CURRENT_READER_ID);
        Reader selectedReader = selectedReaderId == null ? null : readerService.findById(selectedReaderId).orElse(null);
        model.addAttribute("selectedReader", selectedReader);
        if (selectedReader != null) {
            Set<Long> reservedBookIds = selectedReader.getReservedBooks().stream()
                    .map(Book::getId)
                    .collect(Collectors.toSet());
            model.addAttribute("reservedBookIds", reservedBookIds);
        }
        return "reservations/index";
    }

    @PostMapping("/select-reader")
    public String selectReader(@RequestParam Long readerId, HttpSession session, RedirectAttributes redirectAttributes) {
        session.setAttribute(CURRENT_READER_ID, readerId);
        redirectAttributes.addFlashAttribute("message", "Читача вибрано");
        return "redirect:/reservations";
    }

    @PostMapping("/{bookId}/reserve")
    public String reserve(@PathVariable Long bookId, HttpSession session, RedirectAttributes redirectAttributes) {
        Long readerId = (Long) session.getAttribute(CURRENT_READER_ID);
        if (readerId == null) {
            redirectAttributes.addFlashAttribute("error", "Спочатку вибери читача");
            return "redirect:/reservations";
        }

        reservationService.reserveBook(readerId, bookId);
        redirectAttributes.addFlashAttribute("message", "Книгу зарезервовано");
        return "redirect:/reservations";
    }

    @PostMapping("/{bookId}/cancel")
    public String cancel(@PathVariable Long bookId, HttpSession session, RedirectAttributes redirectAttributes) {
        Long readerId = (Long) session.getAttribute(CURRENT_READER_ID);
        if (readerId == null) {
            redirectAttributes.addFlashAttribute("error", "Спочатку вибери читача");
            return "redirect:/reservations";
        }

        reservationService.cancelReservation(readerId, bookId);
        redirectAttributes.addFlashAttribute("message", "Резервування скасовано");
        return "redirect:/reservations";
    }
}
