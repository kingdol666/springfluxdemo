package com.example.webfluxdemo.repository;

import com.example.webfluxdemo.Entity.TBook;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends R2dbcAuthorRepository<TBook, Long>{
}
