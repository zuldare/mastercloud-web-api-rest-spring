package mastercloud.jh.books.repository;

import mastercloud.jh.books.model.Book;
import mastercloud.jh.books.model.Comment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class BookRepository {

    private final ConcurrentHashMap<Long, Book> books;
    private AtomicLong nextId;

    public BookRepository(){
        this.books = new ConcurrentHashMap<>();
        this.nextId = new AtomicLong();
        this.populate();
    }

    private void populate() {
        Long bookId = this.nextId.incrementAndGet();

        this.books.put(bookId, Book.builder()
                .id(bookId)
                .author("Martin Fowler")
                .publishingHouse("Addison")
                .publishYear(2003)
                .summary("Learn how to use UML")
                .title("UML Distilled")
                .comments(new ArrayList<>(createCommentsForBookId(bookId)))
                .build()
                );

        bookId = this.nextId.incrementAndGet();
        this.books.put(bookId, Book.builder()
                .id(bookId)
                .author("Erich Gamma")
                .publishingHouse("Addison")
                .publishYear(2002)
                .summary("Learn design patterns")
                .title("Design Patterns")
                .build()
        );
    }

    private List<Comment> createCommentsForBookId(Long bookId) {
        return Arrays.asList(Comment.builder()
                    .id(1L)
                    .bookId(bookId)
                    .author("Jaime")
                    .score(5)
                    .commentary("Este libro es magnífico, pero debería hacerse una nueva versión actualizada")
                    .build(),
           Comment.builder()
                    .id(2L)
                    .bookId(bookId)
                    .score(1)
                    .author("Juan")
                    .commentary("El UML no se usa")
                    .build());
    }

    public List<Book> findAll(){
        return new ArrayList<>(books.values());
    }

    public Book find(Long id){
        if (!this.books.containsKey(id)){
            return null;
        }
        return this.books.get(id);
    }

    public Book delete(Long id){
        return books.remove(id);
    }

    public Book save(Book book){
        Long next = this.nextId.incrementAndGet();
        book.setId(next);
        this.books.put(next, book);
        return book;
    }
}
