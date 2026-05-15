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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "readers")
public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String fullName;

    @NotBlank
    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String phoneNumber;

    @Valid
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "library_card_id", unique = true)
    private LibraryCard libraryCard;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "reader_book_reservations",
            joinColumns = @JoinColumn(name = "reader_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Book> reservedBooks = new LinkedHashSet<>();

    public Reader() {
    }

    public Reader(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LibraryCard getLibraryCard() {
        return libraryCard;
    }

    public void assignLibraryCard(LibraryCard libraryCard) {
        this.libraryCard = libraryCard;
    }

    public Set<Book> getReservedBooks() {
        return reservedBooks;
    }

    public void reserveBook(Book book) {
        if (!reservedBooks.contains(book)) {
            reservedBooks.add(book);
            book.addReservedReader(this);
        }
    }

    public void cancelReservation(Book book) {
        if (reservedBooks.remove(book)) {
            book.removeReservedReader(this);
        }
    }
}
