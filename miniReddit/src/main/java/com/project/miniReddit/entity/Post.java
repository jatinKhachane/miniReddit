package com.project.miniReddit.entity;

import javax.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.lang.Nullable;
import java.time.Instant;
import java.util.List;

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
    private Integer commentCount;
    private Instant createdDate;

    //Author of the Post
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    //Subreddit of the post
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "subreddit_id")
    private Subreddit subreddit;

    //Users saved/bookmarked this post
    @ManyToMany(mappedBy = "savedPosts")
    private List<User> userswhobookmarked;
}