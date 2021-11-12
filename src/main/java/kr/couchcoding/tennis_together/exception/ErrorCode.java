package kr.couchcoding.tennis_together.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    BAD_REQUEST_PARAM(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

    UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED, "해당 요청은 로그인이 필요합니다."),
    FORBIDDEN_USER(HttpStatus.FORBIDDEN, "해당 요청에 권한이 없습니다."),

    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "해당 USER 계정을 찾을 수 없습니다."),
    NOT_FOUND_REVIEW(HttpStatus.NOT_FOUND, "해당 리뷰를 찾을 수 없습니다."),
    NOT_FOUND_GAME(HttpStatus.NOT_FOUND, "해당 게임을 찾을 수 없습니다."),
    NOT_FOUND_GAME_REPLY(HttpStatus.NOT_FOUND, "해당 댓글을 찾을 수 없습니다."),
    NOT_FOUND_GAME_COURT(HttpStatus.NOT_FOUND, "해당 테니스장을 찾을 수 없습니다."),
    NOT_FOUND_LOCATION(HttpStatus.NOT_FOUND, "해당 위치정보를 찾을 수 없습니다."),
    NOT_FOUND_FRIEND(HttpStatus.NOT_FOUND, "해당 친구를 찾을 수 없습니다."),

    EXIST_USER(HttpStatus.BAD_REQUEST, "이미 등록된 유저입니다."),
    INVALID_AUTHORIZATION(HttpStatus.BAD_REQUEST, "인증 정보가 부정확합니다."),

    EXIST_FRIEND(HttpStatus.BAD_REQUEST, "이미 등록된 친구입니다.");


    private final HttpStatus httpStatus;
    private final String detail;
}
