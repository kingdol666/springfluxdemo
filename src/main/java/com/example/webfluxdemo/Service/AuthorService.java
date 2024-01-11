package com.example.webfluxdemo.Service;

import com.example.webfluxdemo.Entity.TAuthor;
import com.example.webfluxdemo.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    public Mono<TAuthor> save(TAuthor author){
        Mono<TAuthor> save = authorRepository.save(author);
        return save.filter(tAuthor -> tAuthor.getId() != null);
    }

    public Mono<TAuthor> findById(Long id){
        return authorRepository.findById(id);
    }

    public Flux<TAuthor> findAll() {
//        authorRepository.findAll().subscribe(a-> System.out.println("a = " + a));
        return authorRepository.findAll();
    }

    public Flux<TAuthor> findByIdInAndName(List<Long> Ids,String name){
        return authorRepository.findByIdInAndName(Ids, name);
    }
}

