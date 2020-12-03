package mastercloud.jh.books.service.impl;

import lombok.extern.slf4j.Slf4j;
import mastercloud.jh.books.dto.BookDto;
import mastercloud.jh.books.dto.BookReducedDto;
import mastercloud.jh.books.service.BookService;
import mastercloud.jh.books.dto.BookCreationDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    @Override
    public List<BookReducedDto> getBooks() {
        return null;
    }

    @Override
    public BookDto getBook(Long id) {
        return null;
    }

    @Override
    public Long createBook(BookCreationDto bookCreationDto) {
        return null;
    }
}
