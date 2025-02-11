package lk.zerocode.api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String nic;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<BorrowBook> borrowedBooks = new ArrayList<>();
}
