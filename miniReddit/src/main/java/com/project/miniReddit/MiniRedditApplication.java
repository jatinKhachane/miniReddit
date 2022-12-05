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

import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;

@SpringBootApplication
public class MiniRedditApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MiniRedditApplication.class, args);
	}

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private SubredditRepository subredditRepository;
	@Autowired
	private PostRepository postRepository;

	@Override
	@Transactional
	public void run(String... arg0) {

		/*User user = User.builder()
				.email("abc@gmail.com")
				.created(Instant.now())
				.username("abc")
				.password("abc")
				.enabled(false)
				.build();

		Subreddit subreddit = Subreddit.builder()
				.createdDate(Instant.now())
				.description("--")
				.name("r/java")
				.user(user)
				.posts(new ArrayList<Post>())
				.build();

		Post post = Post.builder()
				.postName("newPost")
				.description("--")
				.user(user)
				.subreddit(subreddit)
				.build();

		subreddit.getPosts().add(post);

		postRepository.save(post);
		subredditRepository.save(subreddit);
		userRepository.save(user);

		Optional<Subreddit> sub = subredditRepository.findById(subreddit.getSubredditId());
		System.out.println(sub.get()); */

	}
}
