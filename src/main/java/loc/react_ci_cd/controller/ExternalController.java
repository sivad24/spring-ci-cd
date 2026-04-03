package loc.react_ci_cd.controller;

import loc.react_ci_cd.model.ExternalUser;
import loc.react_ci_cd.model.UserWithPosts;
import loc.react_ci_cd.service.ExternalApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/external")
public class ExternalController {

    private final ExternalApiService externalApiService;

    public ExternalController(ExternalApiService externalApiService) {
        this.externalApiService = externalApiService;
    }

    @GetMapping("/users")
    public Flux<ExternalUser> getExternalUsers() {
        return externalApiService.fetchAllUsers();
    }

    @GetMapping("/users/{id}/with-posts")
    public Mono<UserWithPosts> getUserWithPosts(@PathVariable Long id) {
        return Mono.zip(externalApiService.fetchUserById(id), externalApiService.fetchPostsByUser(id).collectList())
                .map(tuple -> new UserWithPosts(tuple.getT1(), tuple.getT2()));
    }
}
