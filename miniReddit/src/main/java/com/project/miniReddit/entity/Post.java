package com.project.miniReddit.entity;

import javax.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.lang.Nullable;
import java.time.Instant;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long postId;
    @NotBlank(message = "Post Name cannot be empty or Null")
    private String postName;
    @Nullable
    private String description;
    private Integer voteCount;
    private Instant createdDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "subreddit_id")
    private Subreddit subreddit;
}