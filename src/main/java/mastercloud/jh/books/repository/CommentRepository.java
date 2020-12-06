package mastercloud.jh.books.repository;

import mastercloud.jh.books.model.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class CommentRepository {

    private final ConcurrentHashMap<Long, Comment> comments;
    private final AtomicLong nextId;

    public CommentRepository() {
        this.comments = new ConcurrentHashMap<>();
        this.nextId = new AtomicLong();
        this.populate();
    }

    private void populate(){
        Long commentId = this.nextId.incrementAndGet();
        this.comments.put(commentId, Comment.builder()
                .id(commentId)
                .bookId(1L)
                .author("Jaime")
                .score(5)
                .commentary("Este libro es magnífico, pero debería hacerse una nueva versión actualizada")
                .build());

        commentId = this.nextId.incrementAndGet();
        this.comments.put(commentId, Comment.builder()
                .id(commentId)
                .bookId(1L)
                .score(1)
                .author("Juan")
                .commentary("El UML no se usa")
                .build());
    }


    public Comment delete(Long id){
        return this.comments.remove(id);
    }

    public Comment save(Comment comment){
        Long id = this.nextId.incrementAndGet();
        comment.setId(id);
        this.comments.put(id, comment);
        return comment;
    }

    public List<Comment> findAllByBookId(Long bookId){
        return this.comments.values().stream()
                .filter(comment -> comment.getBookId().longValue() == bookId.longValue())
                .collect(Collectors.toList());
    }

    public Comment findById(Long id){
        return this.comments.get(id);
    }
}
