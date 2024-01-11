package com.example.webfluxdemo.Service;

import com.example.webfluxdemo.Entity.TAuthor;
import com.example.webfluxdemo.Entity.TBook;
import com.example.webfluxdemo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.ForkJoinPool;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public Mono<TBook> save(TBook book) {
        return bookRepository.save(book);
    }

    public TBook findById(Long id) {
        return bookRepository.findById(id).block();
    }

    public Flux<TBook> findAll() {
        return bookRepository.findAll();
    }
}
