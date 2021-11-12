package kr.couchcoding.tennis_together.controller.game.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RequestGameDTO {

    String title;
    String content;
    String genderType;
    Integer ageType;
    Integer historyType;
    LocalDate strDt;
    LocalDate endDt;
    Long courtNo;
}
