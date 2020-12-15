package camelwork.back.kernel.model.phonebook;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PhoneBookRepository extends JpaRepository<PhoneBook, Integer> {

    PhoneBook findPhoneBookByFirstName(String name);
}


