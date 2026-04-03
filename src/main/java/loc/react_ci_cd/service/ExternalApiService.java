package loc.react_ci_cd.service;

import loc.react_ci_cd.exception.UserNotFoundException;
import loc.react_ci_cd.model.ExternalPost;
import loc.react_ci_cd.model.ExternalUser;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ExternalApiService {

    private final WebClient webClient;

    public ExternalApiService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<ExternalUser> fetchAllUsers() {
        return webClient.get()
                .uri("/users")
                .retrieve()
                .bodyToFlux(ExternalUser.class);
    }

    public Mono<ExternalUser> fetchUserById(Long id) {
        return webClient.get()
                .uri("/users/{id}", id)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        response -> Mono.error(new UserNotFoundException(id.toString()))
                )
                .bodyToMono(ExternalUser.class);
    }

    public Flux<ExternalPost> fetchPostsByUser(Long userId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/posts").queryParam("userId", userId).build())
                .retrieve()
                .bodyToFlux(ExternalPost.class);
    }
}
