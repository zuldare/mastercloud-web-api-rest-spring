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
@Table(schema = "books", name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String summary;

    @Column(name = "publishing_house", nullable = false)
    private String publishingHouse;

    @Column(name = "publish_year", nullable = false)
    private Integer publishYear;

    @OneToMany(mappedBy = "book")
    private List<Comment> comments;
}
