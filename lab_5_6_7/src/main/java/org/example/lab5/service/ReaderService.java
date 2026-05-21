package org.example.lab5.service;

import java.time.LocalDate;
import java.util.List;

import org.example.lab5.entity.LibraryCard;
import org.example.lab5.entity.Reader;
import org.example.lab5.repository.LibraryCardRepository;
import org.example.lab5.repository.ReaderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReaderService {
    private final ReaderRepository readerRepository;
    private final LibraryCardRepository libraryCardRepository;

    public ReaderService(ReaderRepository readerRepository, LibraryCardRepository libraryCardRepository) {
        this.readerRepository = readerRepository;
        this.libraryCardRepository = libraryCardRepository;
    }

    public List<Reader> findAll() {
        return readerRepository.findAllByOrderByFullNameAsc();
    }

    public Reader getById(Long id) {
        return readerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Читача не знайдено"));
    }

    public java.util.Optional<Reader> findById(Long id) {
        return readerRepository.findById(id);
    }

    @Transactional
    public Reader create(String fullName, String email, String phoneNumber, String cardNumber) {
        String normalizedEmail = require(email, "Email");
        if (readerRepository.findByEmailIgnoreCase(normalizedEmail).isPresent()) {
            throw new IllegalArgumentException("Читач з таким email вже існує");
        }
        Reader reader = new Reader(require(fullName, "ПІБ"), normalizedEmail);
        reader.setPhoneNumber(phoneNumber == null ? "" : phoneNumber.trim());
        reader.assignLibraryCard(createCard(cardNumber));
        return readerRepository.save(reader);
    }

    @Transactional
    public Reader update(Long id, String fullName, String email, String phoneNumber, String cardNumber) {
        Reader reader = getById(id);
        String normalizedEmail = require(email, "Email");
        if (!reader.getEmail().equalsIgnoreCase(normalizedEmail)
                && readerRepository.findByEmailIgnoreCase(normalizedEmail).isPresent()) {
            throw new IllegalArgumentException("Читач з таким email вже існує");
        }

        reader.setFullName(require(fullName, "ПІБ"));
        reader.setEmail(normalizedEmail);
        reader.setPhoneNumber(phoneNumber == null ? "" : phoneNumber.trim());

        if (reader.getLibraryCard() == null) {
            reader.assignLibraryCard(createCard(cardNumber));
        } else if (cardNumber != null && !cardNumber.trim().isEmpty()) {
            String normalizedCard = cardNumber.trim();
            if (!reader.getLibraryCard().getCardNumber().equalsIgnoreCase(normalizedCard)
                    && libraryCardRepository.findByCardNumberIgnoreCase(normalizedCard).isPresent()) {
                throw new IllegalArgumentException("Картка з таким номером вже існує");
            }
            reader.getLibraryCard().setCardNumber(normalizedCard);
        }

        return readerRepository.save(reader);
    }

    @Transactional
    public void delete(Long id) {
        readerRepository.deleteById(id);
    }

    @Transactional
    public Reader save(Reader reader) {
        return readerRepository.save(reader);
    }

    private LibraryCard createCard(String cardNumber) {
        String normalizedCard = cardNumber == null || cardNumber.trim().isEmpty()
                ? "LIB-" + System.currentTimeMillis()
                : cardNumber.trim();
        if (libraryCardRepository.findByCardNumberIgnoreCase(normalizedCard).isPresent()) {
            throw new IllegalArgumentException("Картка з таким номером вже існує");
        }
        LibraryCard card = new LibraryCard(normalizedCard, LocalDate.now());
        card.setExpireDate(LocalDate.now().plusYears(1));
        return card;
    }

    private String require(String value, String label) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(label + " не може бути порожнім");
        }
        return value.trim();
    }
}
