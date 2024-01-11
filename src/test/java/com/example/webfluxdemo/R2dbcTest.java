package com.example.webfluxdemo;

import com.example.webfluxdemo.Entity.TAuthor;
import com.example.webfluxdemo.Entity.TBook;
import com.example.webfluxdemo.Service.AuthorService;
import com.example.webfluxdemo.Service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class R2dbcTest {
    @Autowired
    private R2dbcEntityTemplate r2dbcEntityTemplate;
    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookService bookService;
    @Test
    public void testQueryById() {
        // 根据id查询
        int id = 1;
        // 调用 r2dbcEntityTemplate.query() 方法进行查询
        // 根据需求编写查询逻辑，例如：
        TAuthor user = r2dbcEntityTemplate.selectOne(Query.query(Criteria.where("id").is(id)), TAuthor.class)
                .block();
        System.out.println("user = " + user);
    }

    @Test
    public void testSaveAuthor() {
        Mono<TAuthor> save = authorService.save(new TAuthor("王五"));
        TAuthor user = save.block();
        System.out.println("user = " + user);
    }

    @Test
    public void findAll(){
        Mono<TAuthor> byId = authorService.findById(1L);
        List<TAuthor> all = authorService.findAll().collectList().block();
        System.out.println("all = " + all);
        System.out.println("byId = " + byId.block());
    }

    @Test
    public void findByIdInAndName(){
        Flux<TAuthor> byIdInAndName = authorService.findByIdInAndName(Arrays.asList(1L,2L,3L),"张三");
        List<TAuthor> block = byIdInAndName.collectList().block();
        System.out.println("block = " + block);
    }

    @Test
    public void testSaveBook() {
        Mono<TBook> save = bookService.save(new TBook("Java Study", 1L, Instant.now()));
        TBook user = save.block();
        System.out.println("user = " + user);
    }
}
