package com.project.miniReddit.entity;

import javax.persistence.*;

import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.List;


@Data
@Entity
@Transactional
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long userId;
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Password is required")
    private String password;
    @Email
    @NotEmpty(message = "Email is required")
    private String email;
    private Instant created;
    private boolean enabled;

    //posts that are saved by user
//    @ManyToMany(mappedBy = "userswhobookmarked")
    @ManyToMany
    @JoinTable(
            name = "user_savedPosts",
            joinColumns  = {
                    @JoinColumn(name = "user_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "post_id")
            }
    )
    private List<Post> savedPosts;
};
