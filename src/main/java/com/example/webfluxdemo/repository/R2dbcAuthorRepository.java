package com.example.webfluxdemo.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;


@NoRepositoryBean
public interface R2dbcAuthorRepository<T,ID> extends ReactiveCrudRepository<T,ID> , ReactiveSortingRepository<T,ID> {
}
