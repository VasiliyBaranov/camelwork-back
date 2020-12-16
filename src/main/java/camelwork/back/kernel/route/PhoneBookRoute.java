package camelwork.back.kernel.route;


import camelwork.back.config.ObjectMapperConfig;
import camelwork.back.kernel.model.phonebook.PhoneBookDataFormat;
import camelwork.back.kernel.model.phonebook.PhoneBookService;
import camelwork.back.kernel.processor.PhoneBookProcessor;
import lombok.AllArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
@AllArgsConstructor
public class PhoneBookRoute extends RouteBuilder {

    @Autowired
    private ObjectMapperConfig objectMapperConfig;

    public void configure() throws Exception {

        scanningStorage();
        savePhoneBook();
        removePhoneBook();
        sendFileFolderPrepareToWork();

        from("direct:findAllPhoneBooks")
                .log("Received header : ${header.name}")
                .bean(PhoneBookService.class, "findAllPhoneBooks()");
    }


    private void sendFileFolderPrepareToWork() {
        String fileNameTxt = "/?fileName=json_${date:now:ddmmyy_hhmm_ss}" + ".txt";
        from("direct:sendFileFolderPrepareToWork")
                .log("Received Body ${body}")
                .pollEnrich("file:{{storage.prepareFolderPath}}?delete=true")
                .routeId("sendData")
                .unmarshal(new PhoneBookDataFormat())
                .to("file:{{storage.workFolderPath}}" + fileNameTxt)
                .stop();

        from("file:{{storage.workFolderPath}}?noop=true")
                .setBody(constant(""))
                .to("file:{{storage.workFolderPath}}?fileName=${file:onlyname.noext}.ready");
    }


    private void removePhoneBook() {
        from("direct:removePhoneBook")
                .log("removePhoneBook header : ${header.bookId}")
                .pollEnrich("file:{{storage.prepareFolderPath}}?delete=true")
                .routeId("removePhoneBook")
                .unmarshal(new PhoneBookDataFormat())
                .stop();
    }


    private void scanningStorage() {
        from("direct:scanningStorage")
                .log("Received Body  ${header.name}")
                .pollEnrich("file:{{storage.prepareFolderPath}}?idempotent=true&noop=true")
                .routeId("scanningStorage")
                .unmarshal(new PhoneBookDataFormat());
    }


    private void savePhoneBook() {
        String fileNameJson = "/?fileName=data_${date:now:ddmmyy_hhmm_ss}" + ".json";
        from("direct:savePhoneBook")
                .routeId("savePhoneBook")
                .log("Received Body savePhoneBook ${body}")
                //.to("json-validator:{{validate.jsonSchema}}")
                .process(new PhoneBookProcessor(objectMapperConfig))
                .marshal(new PhoneBookDataFormat())
                .to("file:{{storage.prepareFolderPath}}" + fileNameJson);
    }

}
