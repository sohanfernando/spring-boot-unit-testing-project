package lk.zerocode.api.service.impl;

import lk.zerocode.api.controller.request.CreateMemberRequestDTO;
import lk.zerocode.api.exception.MemberNotCreatedException;
import lk.zerocode.api.model.Member;
import lk.zerocode.api.repository.MemberRepository;
import lk.zerocode.api.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {

    private MemberRepository memberRepository;

    @Override
    public Member create(CreateMemberRequestDTO createMemberRequestDTO) throws MemberNotCreatedException {

        Optional<Member> memberOptional = memberRepository.findByNic(createMemberRequestDTO.getNic());
        if (memberOptional.isPresent()) {
            throw new MemberNotCreatedException("Member is already registered with nic " + createMemberRequestDTO.getNic());
        }
        Member member = new Member();
        member.setName(createMemberRequestDTO.getName());
        member.setNic(createMemberRequestDTO.getNic());
        return memberRepository.save(member);
    }

    @Override
    public List<Member> findAll() {
        return memberRepository.findAll();
    }
}
