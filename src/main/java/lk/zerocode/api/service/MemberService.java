package lk.zerocode.api.service;

import lk.zerocode.api.controller.request.CreateMemberRequestDTO;
import lk.zerocode.api.exception.MemberNotCreatedException;
import lk.zerocode.api.model.Member;

import java.util.List;

public interface MemberService {

    Member create(CreateMemberRequestDTO createMemberRequestDTO) throws MemberNotCreatedException;

    List<Member> findAll();
}
