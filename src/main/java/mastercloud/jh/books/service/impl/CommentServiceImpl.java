package mastercloud.jh.books.service.impl;

import lombok.extern.slf4j.Slf4j;
import mastercloud.jh.books.dto.CommentCreationDto;
import mastercloud.jh.books.dto.CommentDto;
import mastercloud.jh.books.exception.NotFoundException;
import mastercloud.jh.books.model.Comment;
import mastercloud.jh.books.repository.CommentRepository;
import mastercloud.jh.books.service.BookService;
import mastercloud.jh.books.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final BookService bookService;

    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper modelMapper, BookService bookService) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
        this.bookService = bookService;
    }


    @Override
    public CommentDto deleteComment(Long id) {
        log.info("Deleting comment with id: {}", id);
        Comment comment = getCheckedComment(id);
        this.commentRepository.delete(id);
        return modelMapper.map(comment, CommentDto.class);
    }

    private Comment getCheckedComment(Long id){
        Comment comment = this.commentRepository.findById(id);
        if (isNull(comment)){
            log.error("Comment with id: {} was not found", id);
            throw new NotFoundException(String.format("Comment with id: %d was not found", id));
        }
        return comment;
    }

    @Override
    public CommentDto createComment(CommentCreationDto commentCreationDto){
        log.info("Create new comment: {}", commentCreationDto);

        log.info("Getting book with id: {} ", commentCreationDto.getBookId());

        Comment newlyCreatedComment = this.commentRepository.save(modelMapper.map(commentCreationDto, Comment.class));
        log.info("Created comment: {}", newlyCreatedComment);

        CommentDto commentDto = modelMapper.map(newlyCreatedComment, CommentDto.class);
        this.bookService.addComment(commentDto);

        return commentDto;
    }
}
