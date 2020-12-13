package mastercloud.jh.books.dto.comments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentReducedDto {
    private Long id;
    private Integer score;
    private String commentary;
    private String userNick;
    private String userEmail;
}
