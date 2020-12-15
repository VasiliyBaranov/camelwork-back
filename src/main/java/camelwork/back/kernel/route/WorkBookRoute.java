package camelwork.back.kernel.route;

import camelwork.back.kernel.model.workbook.WorkBookService;
import lombok.AllArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class WorkBookRoute extends RouteBuilder {

    public void configure() throws Exception {
        from("direct:findAllWorkBooks")
                .log("Received header : ${header.name}")
                .bean(WorkBookService.class, "findAllWorkBooks()");


        from("direct:saveWorkBook")
                .log("Received header : ${header.name}")
                .bean(WorkBookService.class, "addWorkBook(${body})");
    }
}
