package camelwork.back.kernel.processor;

import camelwork.back.config.ObjectMapperConfig;
import camelwork.back.kernel.model.phonebook.PhoneBook;
import lombok.AllArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@AllArgsConstructor
public class PhoneBookRestProcessor implements Processor {
    @Autowired
    private ObjectMapperConfig objectMapperConfig;


    public void process(Exchange exchange) throws IOException {
        PhoneBook phoneBook = objectMapperConfig.mapper().readValue(exchange.getIn().getBody().toString(), PhoneBook.class);
        if (phoneBook == null)
            phoneBook = new PhoneBook();
        phoneBook.setFirstName(phoneBook.getFirstName());
        phoneBook.setLastName(phoneBook.getFirstName());
        phoneBook.setBirthDate(phoneBook.getBirthDate());
        phoneBook.setMobilePhone(phoneBook.getMobilePhone());
        phoneBook.setEmail(phoneBook.getEmail());
        exchange.getIn().setBody(phoneBook);
    }
}
