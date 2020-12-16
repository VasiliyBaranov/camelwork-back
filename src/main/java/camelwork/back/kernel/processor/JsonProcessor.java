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
public class JsonProcessor implements Processor {
    @Autowired
    private ObjectMapperConfig objectMapperConfig;


    public void process(Exchange exchange) throws IOException {
        String s = objectMapperConfig.mapper().writeValueAsString(exchange.getIn().getBody());
        exchange.getIn().setBody(s);
    }

}