package org.example.lab5.entity;

import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @NotBlank
    @Column(nullable = false)
    private String author;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String isbn;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Valid
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<BookCopy> copies = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "reservedBooks", fetch = FetchType.LAZY)
    private Set<Reader> reservedByReaders = new LinkedHashSet<>();

    public Book() {
    }

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<BookCopy> getCopies() {
        return copies;
    }

    public Set<Reader> getReservedByReaders() {
        return reservedByReaders;
    }

    public void addCopy(BookCopy copy) {
        if (!copies.contains(copy)) {
            copies.add(copy);
            copy.setBook(this);
        }
    }

    public void removeCopy(BookCopy copy) {
        if (copies.remove(copy)) {
            copy.setBook(null);
        }
    }

    public void addReservedReader(Reader reader) {
        if (!reservedByReaders.contains(reader)) {
            reservedByReaders.add(reader);
        }
    }

    public void removeReservedReader(Reader reader) {
        reservedByReaders.remove(reader);
    }
}
