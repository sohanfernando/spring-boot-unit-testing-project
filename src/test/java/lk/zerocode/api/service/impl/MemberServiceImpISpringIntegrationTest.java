package lk.zerocode.api.service.impl;

import lk.zerocode.api.controller.request.CreateMemberRequestDTO;
import lk.zerocode.api.exception.MemberNotCreatedException;
import lk.zerocode.api.model.Member;
import lk.zerocode.api.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
class MemberServiceImpISpringIntegrationTest {

    @Autowired
    private MemberServiceImpl memberService;

    @Autowired
    @SpyBean
    private MemberRepository memberRepository;

    @BeforeEach
    public void clearDB() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("try to create member with already registered nic")
    public void testCreateMemberWithDuplicateNic() throws Exception {

        String name = "Chathuranga";
        String nic = "123456v";

        var createMemberRequestDTO = new CreateMemberRequestDTO();
        createMemberRequestDTO.setName(name);
        createMemberRequestDTO.setNic(nic);

        Member member = memberService.create(createMemberRequestDTO);
        Assertions.assertNotNull(member);
        Assertions.assertNotNull(member.getId());

        //registering the member again with previously saved nic
        assertThrows(MemberNotCreatedException.class, () -> memberService.create(createMemberRequestDTO));

        //memberRepository.findByNic(nic) invoked two times (we spy the been). we can do this because memberRepository is annotated with @Spy
        Mockito.verify(memberRepository, Mockito.times(2)).findByNic(nic);
    }

    @Test
    public void testCreateMember() throws Exception {

        String name = "Chathuranga";
        String nic = "123456v";

        var createMemberRequestDTO = new CreateMemberRequestDTO();
        createMemberRequestDTO.setName(name);
        createMemberRequestDTO.setNic(nic);

        Member member = memberService.create(createMemberRequestDTO);
        Assertions.assertNotNull(member);
        Assertions.assertNotNull(member.getId());
    }
}