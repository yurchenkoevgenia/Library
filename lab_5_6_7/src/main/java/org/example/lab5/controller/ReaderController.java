package org.example.lab5.controller;

import org.example.lab5.entity.Reader;
import org.example.lab5.service.ReaderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/readers")
public class ReaderController {
    private final ReaderService readerService;

    public ReaderController(ReaderService readerService) {
        this.readerService = readerService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("readers", readerService.findAll());
        return "readers/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("reader", new Reader());
        model.addAttribute("cardNumber", "");
        return "readers/form";
    }

    @PostMapping
    public String create(@RequestParam String fullName,
                         @RequestParam String email,
                         @RequestParam(required = false) String phoneNumber,
                         @RequestParam(required = false) String cardNumber,
                         RedirectAttributes redirectAttributes) {
        try {
            readerService.create(fullName, email, phoneNumber, cardNumber);
            redirectAttributes.addFlashAttribute("message", "Читача додано");
        } catch (IllegalArgumentException exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
        }
        return "redirect:/readers";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        model.addAttribute("reader", readerService.getById(id));
        return "readers/detail";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Reader reader = readerService.getById(id);
        model.addAttribute("reader", reader);
        model.addAttribute("cardNumber", reader.getLibraryCard() == null ? "" : reader.getLibraryCard().getCardNumber());
        return "readers/form";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id,
                         @RequestParam String fullName,
                         @RequestParam String email,
                         @RequestParam(required = false) String phoneNumber,
                         @RequestParam(required = false) String cardNumber,
                         RedirectAttributes redirectAttributes) {
        try {
            readerService.update(id, fullName, email, phoneNumber, cardNumber);
            redirectAttributes.addFlashAttribute("message", "Читача оновлено");
        } catch (IllegalArgumentException exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
        }
        return "redirect:/readers/" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        readerService.delete(id);
        redirectAttributes.addFlashAttribute("message", "Читача видалено");
        return "redirect:/readers";
    }
}
