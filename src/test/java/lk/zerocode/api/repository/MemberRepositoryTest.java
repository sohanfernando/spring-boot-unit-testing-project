package lk.zerocode.api.repository;

import lk.zerocode.api.model.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    public void clearDB() {
        memberRepository.deleteAll();
    }

    @Test
    public void testGetMemberById() {

        String name = "Chathuranga";
        String nic = "123456v";

        Member member = new Member();
        member.setNic(nic);
        member.setName(name);
        Member memberCreated = memberRepository.save(member);

        Assertions.assertNotNull(memberCreated);
        Assertions.assertNotNull(memberCreated.getId());

        Member memberFound = memberRepository.findByMemberId(member.getId());
        Assertions.assertNotNull(memberFound);
        Assertions.assertEquals(memberCreated.getId(), memberFound.getId());
        Assertions.assertEquals(memberCreated.getName(), memberFound.getName());
        Assertions.assertEquals(memberCreated.getNic(), memberFound.getNic());
    }


    @Test
    public void testGetMemberByExistingNic() {

        String name = "Chathuranga";
        String nic = "123456v";

        Member member = new Member();
        member.setNic(nic);
        member.setName(name);
        Member memberCreated = memberRepository.save(member);

        Assertions.assertNotNull(memberCreated);
        Assertions.assertNotNull(memberCreated.getId());

        Member memberFound = memberRepository.findByNic(nic).orElse(null);
        Assertions.assertNotNull(memberFound);
        Assertions.assertEquals(memberCreated.getId(), memberFound.getId());
        Assertions.assertEquals(memberCreated.getName(), memberFound.getName());
        Assertions.assertEquals(memberCreated.getNic(), memberFound.getNic());
    }


    @Test
    public void testGetMemberByNonExistingNic() {

        String name = "Chathuranga";
        String nic = "123456v";

        Member member = new Member();
        member.setNic(nic);
        member.setName(name);
        Member memberCreated = memberRepository.save(member);

        Assertions.assertNotNull(memberCreated);
        Assertions.assertNotNull(memberCreated.getId());

        Optional<Member> memberOptional = memberRepository.findByNic("456987");
        Assertions.assertFalse(memberOptional.isPresent());
    }
}