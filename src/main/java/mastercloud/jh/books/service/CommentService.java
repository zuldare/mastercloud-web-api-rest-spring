package mastercloud.jh.books.service;

import mastercloud.jh.books.dto.CommentCreationDto;
import mastercloud.jh.books.dto.CommentDto;

/**
 * Interface for the comment service.
 */
public interface CommentService {

    /**
     * Delete the comment according to an id.
     *
     * @param id identification of the comment to delete.
     * @return the deleted comment.
     */
    CommentDto deleteComment(Long id);

    /**
     * Creates a new comment.
     *
     * @param commentCreationDto creation dto.
     * @return the the newly created comment.
     */
    CommentDto createComment(CommentCreationDto commentCreationDto);
}
