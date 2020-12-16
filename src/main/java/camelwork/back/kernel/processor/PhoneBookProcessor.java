package camelwork.back.kernel.processor;


import camelwork.back.config.ObjectMapperConfig;
import camelwork.back.kernel.model.phonebook.PhoneBook;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
@AllArgsConstructor
@NoArgsConstructor
public class PhoneBookProcessor implements Processor {
    @Autowired
    private ObjectMapperConfig objectMapperConfig;


    public void process(Exchange exchange) throws IOException {
        PhoneBook phoneBook = objectMapperConfig.mapper().convertValue(exchange.getIn().getBody(), PhoneBook.class);
        if (phoneBook == null)
            phoneBook = new PhoneBook();
        phoneBook.setFirstName(phoneBook.getFirstName());
        phoneBook.setLastName(phoneBook.getLastName());
        phoneBook.setBirthDate(phoneBook.getBirthDate());
        phoneBook.setWorkPhone(phoneBook.getWorkPhone());
        phoneBook.setMobilePhone(phoneBook.getMobilePhone());
        phoneBook.setEmail(phoneBook.getEmail());
        exchange.getIn().setBody(phoneBook);
    }

}