package com.project.miniReddit;

import com.project.miniReddit.entity.Post;
import com.project.miniReddit.entity.Subreddit;
import com.project.miniReddit.entity.User;
import com.project.miniReddit.repository.PostRepository;
import com.project.miniReddit.repository.SubredditRepository;
import com.project.miniReddit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;

@SpringBootApplication
public class MiniRedditApplication {
	public static void main(String[] args) {
		SpringApplication.run(MiniRedditApplication.class, args);
	}
}
