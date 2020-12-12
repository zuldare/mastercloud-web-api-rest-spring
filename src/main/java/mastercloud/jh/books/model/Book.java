package mastercloud.jh.books.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema="books", name="books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String author;

    @Column
    private String summary;

    @Column(name = "publishing_house")
    private String publishingHouse;

    @Column(name = "publish_year")
    private Integer publishYear;

    @Transient
    private List<Comment> comments;
}
