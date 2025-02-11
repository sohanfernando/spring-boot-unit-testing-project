package lk.zerocode.api.repository;

import lk.zerocode.api.model.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

//    @Query("FROM  Member m join fetch m.borrowedBooks bb join fetch bb.book where m.id = :member_id")
//    Member findByMemberId(@Param("member_id") Long memberId);

    @EntityGraph(attributePaths = {"borrowedBooks","borrowedBooks.book"})
    @Query("FROM  Member m where m.id = :member_id")
    Member findByMemberId(@Param("member_id") Long memberId);

    Optional<Member> findByNic(String nic);
}
