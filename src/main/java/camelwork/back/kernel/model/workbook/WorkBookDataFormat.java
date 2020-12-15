package camelwork.back.kernel.model.workbook;


//import camelwork.kernel.model.phonebook.PhoneBook;
import camelwork.back.kernel.model.phonebook.PhoneBook;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.NoTypeConversionAvailableException;
import org.apache.camel.spi.DataFormat;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class WorkBookDataFormat implements DataFormat {


    public void marshal(Exchange exchange, Object graph, OutputStream stream) throws NoTypeConversionAvailableException, IOException {
        WorkBook s = exchange.getContext().getTypeConverter().mandatoryConvertTo(WorkBook.class, graph);
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] bytes = objectMapper.writeValueAsString(s).getBytes();
        IOUtils.write(bytes, stream);
    }

    public Object unmarshal(Exchange exchange, InputStream stream) throws Exception {
        byte[] bytes = IOUtils.toByteArray(stream);
        String text = new String(bytes, StandardCharsets.UTF_8);
        Message out = exchange.getOut();
        out.setBody(text);

        return text;
    }

    @Override
    public void start() {}

    @Override
    public void stop() {}
}