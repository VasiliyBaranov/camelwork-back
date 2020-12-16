package camelwork.back.kernel.route.scheduler;

import camelwork.back.config.ObjectMapperConfig;
import camelwork.back.kernel.model.phonebook.PhoneBookDataFormat;
import camelwork.back.kernel.model.phonebook.PhoneBookService;
import camelwork.back.kernel.model.workbook.WorkBookService;
import camelwork.back.kernel.processor.PhoneBookModifyProcessor;
import lombok.AllArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ScheduleMoveRoute extends RouteBuilder {
    @Autowired
    private ObjectMapperConfig objectMapperConfig;

    @Autowired
    private WorkBookService workBookService;

    @Autowired
    private PhoneBookService phoneBookService;

    public void configure() throws Exception {
        from("file:{{storage.workFolderPath}}?delay={{scheduler.scanWork}}&include=.*.txt&delete=true")
                .routeId("ScheduleRouteOperationMove")
                .log("ScheduleRoute operation move ${header.file}")
                .unmarshal(new PhoneBookDataFormat())
                .process(new PhoneBookModifyProcessor(objectMapperConfig, workBookService, phoneBookService))
                .marshal(new PhoneBookDataFormat())
                .to("file:{{storage.dataFolderPath}}")
                .end();
    }
}
