package mastercloud.jh.books.service.impl;

import lombok.extern.slf4j.Slf4j;
import mastercloud.jh.books.dto.CommentDto;
import mastercloud.jh.books.model.Comment;
import mastercloud.jh.books.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    private ConcurrentHashMap<Long, Comment> comments = new ConcurrentHashMap<>();
    private AtomicLong nextId = new AtomicLong();

    @Override
    public void deleteComment(Long id) {
        log.info("Deleting comment with id: {}");
        if (comments.containsKey(id)){
            comments.remove(id);
            nextId.decrementAndGet();
        }
    }

    @Override
    public Long createComment(CommentDto commentCreationDto) {
        log.info("Create new comment: {}", commentCreationDto);
        Long next = nextId.incrementAndGet();

        this.comments.computeIfAbsent(next, key ->
                Comment.builder()
                    .commentary(commentCreationDto.getCommentary())
                    .name(commentCreationDto.getName())
                    .score(commentCreationDto.getScore())
                    .build()
        );

        return next;
    }
}
