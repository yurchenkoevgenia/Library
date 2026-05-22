package org.example.lab5.controller;

import java.util.List;

import org.example.lab5.entity.Book;
import org.example.lab5.entity.Category;
import org.example.lab5.entity.Reader;
import org.example.lab5.repository.BookRepository;
import org.example.lab5.repository.CategoryRepository;
import org.example.lab5.repository.ReaderRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;

    public HomeController(CategoryRepository categoryRepository,
                          BookRepository bookRepository,
                          ReaderRepository readerRepository) {
        this.categoryRepository = categoryRepository;
        this.bookRepository = bookRepository;
        this.readerRepository = readerRepository;
    }

    @GetMapping({"/", "/index", "/home"})
    public String home(Model model) {
        List<Category> categories = categoryRepository.findAllByOrderByNameAsc();
        List<Book> books = bookRepository.findAllByOrderByTitleAsc();
        List<Reader> readers = readerRepository.findAllByOrderByFullNameAsc();
        long reservedBooks = readers.stream().mapToLong(reader -> reader.getReservedBooks().size()).sum();

        model.addAttribute("categoryCount", categories.size());
        model.addAttribute("bookCount", books.size());
        model.addAttribute("readerCount", readers.size());
        model.addAttribute("reservedBookCount", reservedBooks);
        return "dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboardRedirect() {
        return "redirect:/";
    }
}
