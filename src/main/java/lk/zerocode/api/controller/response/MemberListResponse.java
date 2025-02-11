package lk.zerocode.api.controller.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MemberListResponse {

   private List<MemberListItem> members;
}
