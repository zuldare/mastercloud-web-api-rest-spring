package mastercloud.jh.books.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Comment {
    private Integer score;
    private String commentary;
    private String name;
}
