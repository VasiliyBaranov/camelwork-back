package camelwork.back.rest;

import camelwork.back.kernel.model.phonebook.PhoneBook;
import camelwork.back.kernel.model.workbook.WorkBook;
import lombok.AllArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BookCamelController extends RouteBuilder {


    private final Environment env;


    public void configure() throws Exception {

        restConfiguration()
                .contextPath(env.getProperty("camel.component.servlet.mapping.contextPath", "/rest/*"))
                .apiContextPath("/api-doc")
                .enableCORS(true)
                .corsAllowCredentials(true)
                .corsHeaderProperty("Access-Control-Allow-Origin","*")
                .corsHeaderProperty("Access-Control-Allow-Methods","POST,GET,DELETE")
                .corsHeaderProperty("Access-Control-Allow-Headers","Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization")
                .bindingMode(RestBindingMode.json);

        initPhoneBookEndpoint();
        initWorkBookEndpoint();
    }


    private void initPhoneBookEndpoint(){
        rest("/book")
                .consumes(MediaType.APPLICATION_JSON_VALUE)
                .produces(MediaType.APPLICATION_JSON_VALUE)

                .get("/scanning-store").route()
                .to("direct:scanningStorage")
                .endRest()

                .get("/").route()
                .to("direct:findAllPhoneBooks")
                .endRest()

                .get("/send-data").route()
                .to("direct:sendFileFolderPrepareToWork")
                .endRest()

                .post("/").route()
                .marshal().json()
                .unmarshal(getJacksonDataFormat(PhoneBook.class))
                .to("direct:savePhoneBook")
                .endRest()

                .delete("/{bookId}").route()
                .to("direct:removePhoneBook")
                .endRest();
    }


    private void initWorkBookEndpoint(){
        rest("/work-book")
                .consumes(MediaType.APPLICATION_JSON_VALUE)
                .produces(MediaType.APPLICATION_JSON_VALUE)

                .get("/").route()
                .to("direct:findAllWorkBooks")
                .endRest()

                .post("/").route()
                .marshal().json()
                .unmarshal(getJacksonDataFormat(WorkBook.class))
                .to("direct:saveWorkBook")
                .endRest()

                .delete("/{bookId}").route()
                .to("direct:removeWorkBook")
                .end();
    }

    private JacksonDataFormat getJacksonDataFormat(Class<?> unmarshalType) {
        JacksonDataFormat format = new JacksonDataFormat();
        format.setUnmarshalType(unmarshalType);
        return format;
    }
}
