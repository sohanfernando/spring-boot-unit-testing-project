package lk.zerocode.api.controller.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberListItem {

    private Long id;
    private String name;
    private String nic;
}
