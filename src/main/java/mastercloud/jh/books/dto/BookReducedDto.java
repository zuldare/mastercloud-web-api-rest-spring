package mastercloud.jh.books.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookReducedDto {
    private Long id;
    private String title;
}
