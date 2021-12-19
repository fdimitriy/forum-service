package telran.b7a.forum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.b7a.forum.dto.DatePeriodDto;
import telran.b7a.forum.dto.MessageDto;
import telran.b7a.forum.dto.NewPostDto;
import telran.b7a.forum.dto.PostDto;
import telran.b7a.forum.service.ForumService;

@RestController
@RequestMapping("/forum") // для сокращения длины строки URN, после этого адреса идет адрес указанный в @PostMapping 
public class ForumController {
	
	ForumService forumService;
	
	@Autowired
	public ForumController(ForumService forumService) {
		super();
		this.forumService = forumService;
	}
	
	@PostMapping("/post/{author}")
	public PostDto addPost(@PathVariable String author, @RequestBody NewPostDto newPostDto) {
		return forumService.addPost(author, newPostDto);
	}	

	@GetMapping("/post/{id}")
	public PostDto findPostById(@PathVariable String id) {
		return forumService.findPostById(id);				
	}
	
	@DeleteMapping("/post/{id}")
	public PostDto deletePost(@PathVariable String id) {
		return forumService.deletePost(id);
	}	
	
	@PutMapping("/post/{id}")
	public PostDto updatePost(@PathVariable String id, @RequestBody NewPostDto newPostDto) {
		return forumService.updatePost(id, newPostDto);
	}
	
	@PutMapping("/post/{id}/like")
	public void addLike(@PathVariable String id) {
		forumService.addLikeToPost(id);
	}
	
	@PutMapping("/post/{id}/comment/{author}")
	public PostDto addCommentToPost(@PathVariable String id, @PathVariable String author, @RequestBody MessageDto messageDto) {
		return forumService.addCommentToPost(id, author, messageDto);
	}	
	
	@GetMapping("/posts/author/{author}")
	public List<PostDto> findPostsByAuthor(@PathVariable String author) {
		return forumService.findPostsByAuthor(author);
	}	
	
	@PostMapping("/posts/tags")
	public List<PostDto> findPostsByTags(@RequestBody List<String> tags) {
		return forumService.findPostByTags(tags);
	}
	
	@PostMapping("/posts/period")
	public List<PostDto> findPostsByDate(@RequestBody DatePeriodDto datePeriodDto) {
	    return forumService.findPostByDates(datePeriodDto);
	}

}
