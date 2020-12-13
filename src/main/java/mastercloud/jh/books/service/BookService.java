package mastercloud.jh.books.service;

import mastercloud.jh.books.dto.books.BookCreationDto;
import mastercloud.jh.books.dto.books.BookDto;
import mastercloud.jh.books.dto.books.BookReducedDto;
import mastercloud.jh.books.dto.books.BookWithCommentsDto;
import mastercloud.jh.books.dto.comments.CommentCreationDto;
import mastercloud.jh.books.dto.comments.CommentDto;

import java.util.List;

public interface BookService {

    List<BookReducedDto> getBooks();

    BookWithCommentsDto getBook(Long bookId);

    BookDto createBook(BookCreationDto bookCreationDto);

    CommentDto createComment(CommentCreationDto commentCreationDto);

    CommentDto deleteComment(Long commentId);

    List<CommentDto> getBooksComment(Long bookId);
}
