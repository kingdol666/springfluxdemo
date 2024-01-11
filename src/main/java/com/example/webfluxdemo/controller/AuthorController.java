package com.example.webfluxdemo.controller;

import com.example.webfluxdemo.Entity.TAuthor;
import com.example.webfluxdemo.Service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody TAuthor requestBody){
        authorService.save(requestBody);
        return ResponseEntity.ok("success!");
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<TAuthor> findById(@PathVariable Long id){
        return ResponseEntity.ok(authorService.findById(id).block());
    }

    @GetMapping("/findAll")
    public ResponseEntity<Iterable<TAuthor>> findAll(){
        return ResponseEntity.ok(authorService.findAll().collectList().block());
    }
}
