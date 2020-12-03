package mastercloud.jh.books.service;

import mastercloud.jh.books.dto.BookCreationDto;
import mastercloud.jh.books.dto.BookDto;
import mastercloud.jh.books.dto.BookReducedDto;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * Interface for book service.
 */
public interface BookService {

    /**
     * Gets a list of books containing just book's title and identification.
     * @return a list of books containing identification and title
     */
    List<BookReducedDto> getBooks();

    /**
     * Gets all the information about a concrete book.
     * @param id book's id
     * @return all the information about a book
     */
    BookDto getBook(Long id);

    /**
     * Creates a book according to a provided information.
     * @param bookCreationDto dto containing the information in order to create the book
     * @return the id of the newly created book
     */
    Long createBook(@Validated BookCreationDto bookCreationDto);
}
