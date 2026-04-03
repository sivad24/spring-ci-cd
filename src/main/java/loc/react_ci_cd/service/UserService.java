package loc.react_ci_cd.service;

import loc.react_ci_cd.exception.UserNotFoundException;
import loc.react_ci_cd.model.User;
import loc.react_ci_cd.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Mono<User> getUserById(String id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException(id)));
    }

    public Flux<User> getActiveUsers() {
        return userRepository.findByActiveTrue();
    }

    public Mono<User> createUser(User user) {
        return userRepository.insert(user.id(), user.name(), user.age(), user.active());
    }

    public Mono<Void> deleteUser(String id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException(id)))
                .flatMap(user -> userRepository.deleteById(user.id()));
    }

    public Mono<String> getUserSummary(String id) {
        return getUserById(id)
                .map(u -> u.name() + " (age " + u.age() + ") — " + (u.active() ? "active" : "inactive"));
    }
}