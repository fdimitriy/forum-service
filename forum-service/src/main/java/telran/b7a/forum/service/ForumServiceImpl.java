package telran.b7a.forum.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import telran.b7a.forum.dao.ForumMongoRepository;
import telran.b7a.forum.dto.DatePeriodDto;
import telran.b7a.forum.dto.MessageDto;
import telran.b7a.forum.dto.NewPostDto;
import telran.b7a.forum.dto.PostDto;
import telran.b7a.forum.dto.exceptions.PostNotFoundException;
import telran.b7a.forum.model.Comment;
import telran.b7a.forum.model.Post;
import telran.b7a.forum.service.logging.PostLogger;

@Service
//@Slf4j // аннотация для ломбока подключить логгирование slf4j, по умолчанию создаст переменную log
public class ForumServiceImpl implements ForumService {

//	static final Logger log = LoggerFactory.getLogger(ForumServiceImpl.class);  // подключаем систему логгирования slf4j
	ForumMongoRepository forumRepository;
	ModelMapper modelMapper;

	@Autowired
	public ForumServiceImpl(ForumMongoRepository forumRepository, ModelMapper modelMapper) {
		super();
		this.forumRepository = forumRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public PostDto addPost(String author, NewPostDto newPostDto) {
		Post post = modelMapper.map(newPostDto, Post.class);
		post.setAuthor(author);
		forumRepository.save(post);
		return modelMapper.map(post, PostDto.class);

	}

	@Override
	public PostDto findPostById(String id) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostDto deletePost(String id) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		forumRepository.deleteById(id);
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	@PostLogger
	public PostDto updatePost(String id, NewPostDto newPostDto) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		String content = newPostDto.getContent();
		if (content != null) {
			post.setContent(content);
		}
		String title = newPostDto.getTitle();
		if (title != null) {
			post.setTitle(title);
		}
		Set<String> tags = newPostDto.getTags();
		if( tags != null) {
			tags.forEach(post::addTag);
		}		
		forumRepository.save(post);
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	@PostLogger
	public void addLikeToPost(String id) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		post.addLike();
		forumRepository.save(post);
	}

	@Override
	public PostDto addCommentToPost(String id, String author, MessageDto messageDto) {		
		Post post = forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		String message = messageDto.getMessage();
		Comment comment = new Comment(author, message);
		post.addComment(comment);
		forumRepository.save(post);
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> findPostsByAuthor(String author) {   // подключили логгирование с выводом в консоль по умолчанию
//		long t1 = System.currentTimeMillis();
		
		List<PostDto> res = forumRepository.findByAuthor(author)
                .map(p -> modelMapper.map(p, PostDto.class))
                .collect(Collectors.toList());
		
//		long t2 = System.currentTimeMillis();
//		log.info("method - findPostsByAuthor(String author), duration = {}", ( t2-t1 ));
		
		return res;	
	}

	@Override
	public List<PostDto> findPostByTags(List<String> tags) {
		return forumRepository.findByTagsIn(tags)
				                    .map(p-> modelMapper.map(p, PostDto.class))
				                    .collect(Collectors.toList());
	}

	@Override
	public List<PostDto> findPostByDates(DatePeriodDto datePeriodDto) {
		return forumRepository.findByDateCreatedBetween(datePeriodDto.getDateFrom(), datePeriodDto.getDateTo().plusDays(1))
				                            .map(p-> modelMapper.map(p, PostDto.class))
				                            .collect(Collectors.toList());
	}

}
