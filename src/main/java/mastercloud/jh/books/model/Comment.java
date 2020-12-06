package mastercloud.jh.books.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Comment {
    private Long id;
    private Integer score;
    private String commentary;
    private String author;
    private Long bookId;

    public boolean belongsToBook(Long bookId){
        return bookId.longValue() == this.id.longValue();
    }
}
