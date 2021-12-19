package telran.b7a.forum.service;

import java.util.List;

import telran.b7a.forum.dto.DatePeriodDto;
import telran.b7a.forum.dto.MessageDto;
import telran.b7a.forum.dto.NewPostDto;
import telran.b7a.forum.dto.PostDto;

public interface ForumService {
	
	PostDto addPost(String author, NewPostDto newPostDto);
	
	PostDto findPostById(String id);
	
	PostDto deletePost(String id);
	
	PostDto updatePost(String id, NewPostDto newPostDto);
	
	void addLikeToPost(String id);
	
	PostDto addCommentToPost(String id, String author, MessageDto messageDto);
	
	List<PostDto> findPostsByAuthor(String author);	
	
	List<PostDto> findPostByTags(List<String> tags);
	
	List<PostDto> findPostByDates(DatePeriodDto datePeriodDto);

}
