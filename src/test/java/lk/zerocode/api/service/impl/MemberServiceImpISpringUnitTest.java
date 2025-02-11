
package lk.zerocode.api.service.impl;

import lk.zerocode.api.controller.request.CreateMemberRequestDTO;
import lk.zerocode.api.exception.MemberNotCreatedException;
import lk.zerocode.api.model.Member;
import lk.zerocode.api.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class MemberServiceImpISpringUnitTest {

    @Autowired
    private MemberServiceImpl memberService;

    @MockBean
    private MemberRepository memberRepository;

    @Test
    @DisplayName("try to create member with already registered nic")
    public void testCreateMemberWithDuplicateNic() throws Exception {

        String name = "Chathuranga";
        String nic = "123456v";

        var member = new Member();
        member.setId(1L);
        member.setName(name);
        member.setNic(nic);

        //mock
        Mockito.when(memberRepository.findByNic(nic)).thenReturn(Optional.of(member));

        var createMemberRequestDTO = new CreateMemberRequestDTO();
        createMemberRequestDTO.setName(name);
        createMemberRequestDTO.setNic(nic);

        assertThrows(MemberNotCreatedException.class, () -> memberService.create(createMemberRequestDTO));
    }

    @Test
    public void testCreateMember() throws Exception {

        String name = "Chathuranga";

        var member = new Member();
        member.setId(1L);
        member.setName(name);

        //mock
        Mockito.when(memberRepository.save(Mockito.any(Member.class))).thenReturn(member);

        var request = new CreateMemberRequestDTO();
        request.setName(name);
        var createdMember = memberService.create(request);

        assertEquals(member.getId(), createdMember.getId());
        assertEquals(member.getName(), createdMember.getName());
    }
}