package lk.zerocode.api.repository;

import lk.zerocode.api.model.BorrowBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowBookRepository extends JpaRepository<BorrowBook, Long> {

}
