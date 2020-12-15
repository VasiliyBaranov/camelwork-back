package camelwork.back.kernel.model.phonebook;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class PhoneBookService {
    @Autowired
    private final PhoneBookRepository bookRepository;

    public PhoneBookService(PhoneBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    public List<PhoneBook> findAllPhoneBooks() {
        return bookRepository.findAll();
    }

    public PhoneBook findPhoneBookByName(String name) {
        return bookRepository.findPhoneBookByFirstName(name);
    }

    public PhoneBook addPhoneBook(PhoneBook book) {
        return bookRepository.save(book);
    }

    public void removePhoneBook(int bookId) {
        log.info("removePhoneBook {}", bookId);
        //bookRepository.deleteById(bookId);
    }
}
