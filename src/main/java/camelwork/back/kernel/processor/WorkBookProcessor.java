package camelwork.back.kernel.processor;



import com.fasterxml.jackson.databind.ObjectMapper;
import camelwork.back.kernel.model.workbook.WorkBook;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.io.IOException;

public class WorkBookProcessor implements Processor {
    public void process(Exchange exchange) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        WorkBook workBook = objectMapper.readValue(exchange.getIn().getBody().toString(), WorkBook.class);
        if (workBook == null)
            workBook = new WorkBook();
        workBook.setFirstName("Vasiliy");
        workBook.setLastName("Baranov");
        exchange.getIn().setBody(workBook);
    }

}