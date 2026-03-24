package loc.react_ci_cd.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class ReactiveController {

    @GetMapping(value = "/test", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<String> testMethod() {
        return Mono.just("Test controller");
    }

}
