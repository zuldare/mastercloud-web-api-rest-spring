package mastercloud.jh.books.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Book {
    private Long id;
    private String title;
    private String author;
    private String summary;
    private String publishingHouse;
    private Integer publishYear;
}
