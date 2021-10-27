package kr.couchcoding.tennis_togeter.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.Getter;

@Getter
@Entity
@IdClass(LocCdKey.class)
public class LocCd {
    @Id
    @Column(length = 2)
    private String locSd;
    @Id
    @Column(length = 5)
    private String locSsk;
    @Column(length = 100)
    private String locSdName;
    @Column(length = 100)
    private String locSskName;
    @Column(length = 1)
    @Convert(converter = BooleanConverter.class)
    private Boolean regStCd;
}
