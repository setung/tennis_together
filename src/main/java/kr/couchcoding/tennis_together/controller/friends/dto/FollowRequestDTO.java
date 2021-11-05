package kr.couchcoding.tennis_together.controller.friends.dto;

import lombok.Data;

@Data //setter getter toString  equals hashcode를 한 번에!
public class FollowRequestDTO {
    private String addedUserUid; // api에 있는 param 이름 똑같이!! {body에들어가는 부분}
}

