package camelwork.back.kernel.model.phonebook;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.spi.DataFormat;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class JSONDataFormat implements DataFormat {


    @Override
    public void marshal(Exchange exchange, Object graph, OutputStream stream) throws Exception {

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