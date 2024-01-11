package com.example.webfluxdemo.repository;

import com.example.webfluxdemo.Entity.TAuthor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.Collection;

@Repository
public interface AuthorRepository extends R2dbcAuthorRepository<TAuthor, Long>{
    Flux<TAuthor> findByIdInAndName(@NonNull Collection<Long> ids, @NonNull String Name);

}
