package mastercloud.jh.books.repository;

import mastercloud.jh.books.model.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
        Long id = this.nextId.incrementAndGet();
        this.books.put(id, Book.builder()
                .id(id)
                .author("Martin Fowler")
                .publishingHouse("Addison")
                .publishYear(2003)
                .summary("Learn how to use UML")
                .title("UML Distilled")
                .build()
                );

        id= this.nextId.incrementAndGet();
        this.books.put(id, Book.builder()
                .id(id)
                .author("Erich Gamma")
                .publishingHouse("Addison")
                .publishYear(2002)
                .summary("Learn design patterns")
                .title("Design Patterns")
                .build()
        );


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
