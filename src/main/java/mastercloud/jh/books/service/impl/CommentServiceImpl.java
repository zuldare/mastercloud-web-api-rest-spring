package mastercloud.jh.books.service.impl;

import lombok.extern.slf4j.Slf4j;
import mastercloud.jh.books.dto.CommentCreationDto;
import mastercloud.jh.books.dto.CommentDto;
import mastercloud.jh.books.exception.NotFoundException;
import mastercloud.jh.books.model.Comment;
import mastercloud.jh.books.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    private ConcurrentHashMap<Long, Comment> comments = new ConcurrentHashMap<>();
    private AtomicLong nextId = new AtomicLong();
    private final ModelMapper modelMapper;

    public CommentServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public void deleteComment(Long id) {
        log.info("Deleting comment with id: {}");
        if (comments.containsKey(id)){
            comments.remove(id);
            nextId.decrementAndGet();
        } else {
            log.error("Comment with id: {} was not found", id);
            throw new NotFoundException(String.format("Comment with id: %d was not found", id));
        }
    }

    @Override
    public CommentDto createComment(CommentCreationDto commentCreationDto){
        log.info("Create new comment: {}", commentCreationDto);
        Long next = nextId.incrementAndGet();

        this.comments.computeIfAbsent(next, key ->
                Comment.builder()
                        .id(next)
                        .author(commentCreationDto.getAuthor())
                        .score(commentCreationDto.getScore())
                        .commentary(commentCreationDto.getCommentary())
                        .bookId(commentCreationDto.getBookId())
                    .build()
        );

        return modelMapper.map(this.comments.get(next), CommentDto.class);
    }
}
