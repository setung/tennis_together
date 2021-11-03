package kr.couchcoding.tennis_together.domain.location.model;
import javax.persistence.*;
import lombok.*;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "loc_cd")
public class LocCd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loc_cd_no")
    private long locCdNo; // 복합키 관계 설정이 어려워 대체키 생성

    @Column
    private String locSd;

    @Column
    private String locSkk;

    @Column(nullable = false, length = 100, name = "loc_sd_name")
    private String locSdName;

    @Column(nullable = false, length = 100, name = "loc_skk_name")
    private String locSkkName;

    @Column(nullable = false, name = "reg_st_cd")
    private char regStCd = '1';

    @Builder
    public LocCd(String locSd, String locSkk, String locSdName, String locSkkName){
        this.locSd = locSd;
        this.locSkk = locSkk;
        this.locSdName = locSdName;
        this.locSkkName = locSkkName;
    }
}

