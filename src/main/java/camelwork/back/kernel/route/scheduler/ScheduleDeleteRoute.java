package camelwork.back.kernel.route.scheduler;

import lombok.AllArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ScheduleDeleteRoute extends RouteBuilder {

    public void configure() throws Exception {
        from("file:{{storage.workFolderPath}}?delay={{scheduler.scanWork}}&include=.*.ready&delete=true")
                .routeId("ScheduleRouteOperationDelete")
                .log("ScheduleRoute operation delete ${header.file}")
                .end();
    }
}
