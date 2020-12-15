package camelwork.back.kernel.route;

import camelwork.back.kernel.model.workbook.WorkBookService;
import org.apache.camel.builder.RouteBuilder;

public class ScheduleRoute extends RouteBuilder {

    public void configure() throws Exception {

//        from("file:{{storage.prepareFolderPath}}?delete=true")
//                .log("removePhoneBook header : ${header.bookId}")
//                .routeId("removePhoneBook")
//                .process(exchange -> {
//                    exchange.getIn().getBody();
//                })
//                .from("file:{{storage.prepareFolderPath}}?delete=true")
//                .stop()
//                ;
    }
}
