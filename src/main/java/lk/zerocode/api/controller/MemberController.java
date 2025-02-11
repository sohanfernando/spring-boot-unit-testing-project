package lk.zerocode.api.controller;

import lk.zerocode.api.controller.request.CreateMemberRequestDTO;
import lk.zerocode.api.controller.response.MemberListItem;
import lk.zerocode.api.controller.response.MemberListResponse;
import lk.zerocode.api.exception.MemberNotCreatedException;
import lk.zerocode.api.model.Member;
import lk.zerocode.api.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping(value = "/members", headers = "X-Api-Version=v1")
    public void createMember(@RequestBody CreateMemberRequestDTO requestDTO) throws MemberNotCreatedException {
        memberService.create(requestDTO);
    }


    @GetMapping(value = "/members", headers = "X-Api-Version=v1")
    public MemberListResponse getAllMembers() {

        List<Member> memberList = memberService.findAll();
        var members = memberList.stream()
                .map(memember -> MemberListItem.builder()
                        .id(memember.getId())
                        .name(memember.getName())
                        .nic(memember.getNic())
                        .build())
                .toList();

        return MemberListResponse.builder()
                .members(members)
                .build();
    }
}
