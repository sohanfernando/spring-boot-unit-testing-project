package lk.zerocode.api.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "borrow_books")
public class BorrowBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;
}
