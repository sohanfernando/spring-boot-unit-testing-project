package lk.zerocode.api.service.impl;

import lk.zerocode.api.controller.request.CreateMemberRequestDTO;
import lk.zerocode.api.exception.MemberNotCreatedException;
import lk.zerocode.api.model.Member;
import lk.zerocode.api.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberServiceImplPureMockitoUnitTest {

    @InjectMocks
    private MemberServiceImpl memberService;

    @Mock
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        //this will initialize the memberService annotated with @InjectMocks
        MockitoAnnotations.openMocks(this);
    }

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

        //check whether number of method invocation is one
        Mockito.verify(memberRepository, Mockito.times(0)).save(Mockito.any(Member.class));
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

        //check whether number of method invocation is one
        Mockito.verify(memberRepository, Mockito.times(1)).save(Mockito.any(Member.class));
    }
}