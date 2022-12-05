package com.project.miniReddit.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.time.Instant;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotEmpty
    private String text;

    @ManyToOne
    @JoinColumn(name = "postId")
    private Post post;
    private Instant createdDate;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}
