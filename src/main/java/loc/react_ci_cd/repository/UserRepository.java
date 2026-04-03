package loc.react_ci_cd.repository;

import loc.react_ci_cd.exception.UserNotFoundException;
import loc.react_ci_cd.model.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, String> {

    Flux<User> findByActiveTrue();
    Flux<User> findByAgeLessThan(int age);
    Mono<User> findByName(String name);

    @Query("INSERT INTO users (id, name, age, active) VALUES (:id, :name, :age, :active) RETURNING *")
    Mono<User> insert(String id, String name, int age, boolean active);

}