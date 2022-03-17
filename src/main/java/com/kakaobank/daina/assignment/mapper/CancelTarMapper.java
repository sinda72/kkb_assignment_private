package com.kakaobank.daina.assignment.mapper;

import com.kakaobank.daina.assignment.domain.AccInfo;
import com.kakaobank.daina.assignment.domain.Account;
import com.kakaobank.daina.assignment.domain.CancelTar;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CancelTarMapper {
    CancelTar findById(Long tId);
    List<CancelTar> findOverDate();
    void updateRcode(CancelTar cancelTar);
    void insert(CancelTar cancelTar);
}
