package org.example.lab5.controller;

import java.util.Set;
import java.util.stream.Collectors;

import org.example.lab5.entity.Book;
import org.example.lab5.entity.Category;
import org.example.lab5.service.BookService;
import org.example.lab5.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final CategoryService categoryService;

    public BookController(BookService bookService, CategoryService categoryService) {
        this.bookService = bookService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String list(@RequestParam(required = false) String q, Model model) {
        model.addAttribute("books", bookService.findAll(q));
        model.addAttribute("searchQuery", q == null ? "" : q);
        return "books/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("selectedCategoryId", null);
        model.addAttribute("copyCode", "");
        return "books/form";
    }

    @PostMapping
    public String create(@RequestParam String title,
                         @RequestParam String author,
                         @RequestParam String isbn,
                         @RequestParam Long categoryId,
                         @RequestParam(required = false) String copyCode,
                         RedirectAttributes redirectAttributes) {
        try {
            bookService.create(title, author, isbn, categoryId, copyCode);
            redirectAttributes.addFlashAttribute("message", "Книгу додано");
        } catch (IllegalArgumentException exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
        }
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        Book book = bookService.getById(id);
        model.addAttribute("book", book);
        Set<Long> reservedReaderIds = book.getReservedByReaders().stream()
                .map(reader -> reader.getId())
                .collect(Collectors.toSet());
        model.addAttribute("reservedReaderIds", reservedReaderIds);
        return "books/detail";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Book book = bookService.getById(id);
        model.addAttribute("book", book);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("selectedCategoryId", book.getCategory() == null ? null : book.getCategory().getId());
        model.addAttribute("copyCode", "");
        return "books/form";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id,
                         @RequestParam String title,
                         @RequestParam String author,
                         @RequestParam String isbn,
                         @RequestParam Long categoryId,
                         RedirectAttributes redirectAttributes) {
        try {
            bookService.update(id, title, author, isbn, categoryId);
            redirectAttributes.addFlashAttribute("message", "Книгу оновлено");
        } catch (IllegalArgumentException exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
        }
        return "redirect:/books/" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        bookService.delete(id);
        redirectAttributes.addFlashAttribute("message", "Книгу видалено");
        return "redirect:/books";
    }

    @PostMapping("/{id}/copies")
    public String addCopy(@PathVariable Long id,
                          @RequestParam(required = false) String copyCode,
                          RedirectAttributes redirectAttributes) {
        try {
            bookService.addCopy(id, copyCode);
            redirectAttributes.addFlashAttribute("message", "Примірник додано");
        } catch (IllegalArgumentException exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
        }
        return "redirect:/books/" + id;
    }
}
