package com.example.webfluxdemo.Entity;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("t_book")
public class TBook {
    @Id
    @Nullable
    private Long id;
    private String title;
    private Long authorId;
    private Instant publishTime;

    public TBook(String title, long authorId, Instant now) {
        this.title = title;
        this.authorId = authorId;
        this.publishTime = now;
    }
}
