package camelwork.back.kernel.processor;

import camelwork.back.config.ObjectMapperConfig;
import camelwork.back.kernel.model.phonebook.PhoneBook;
import camelwork.back.kernel.model.phonebook.PhoneBookService;
import camelwork.back.kernel.model.workbook.WorkBook;
import camelwork.back.kernel.model.workbook.WorkBookService;
import lombok.AllArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
public class PhoneBookModifyProcessor implements Processor {
    @Autowired
    private ObjectMapperConfig objectMapperConfig;

    @Autowired
    private WorkBookService workBookService;

    @Autowired
    private PhoneBookService phoneBookService;

    public void process(Exchange exchange) throws IOException {
        PhoneBook phoneBook = objectMapperConfig.mapper().readValue(exchange.getIn().getBody().toString(), PhoneBook.class);
        if (phoneBook != null) {
            WorkBook workBook = workBookService.findWorkBookByFirstNameAndLastName(phoneBook.getFirstName(), phoneBook.getLastName());
            phoneBook.setFirstName(phoneBook.getFirstName());
            phoneBook.setLastName(phoneBook.getFirstName());
            phoneBook.setBirthDate(phoneBook.getBirthDate());
            phoneBook.setMobilePhone(phoneBook.getMobilePhone());
            phoneBook.setEmail(phoneBook.getEmail());
            if (workBook != null && workBook.getPlaceWork() != null && workBook.getAddressWork() != null) {
                phoneBook.setWork(workBook.getPlaceWork() + " " + workBook.getAddressWork());
                phoneBookService.addPhoneBook(phoneBook);
            }
            exchange.getIn().setBody(phoneBook);
        }


    }

}
