package com.kakaobank.daina.assignment.service;

import com.kakaobank.daina.assignment.domain.AccInfo;
import com.kakaobank.daina.assignment.domain.HistorySimTransDetail;
import com.kakaobank.daina.assignment.domain.SimTransDetail;
import com.kakaobank.daina.assignment.dto.ReceiveMoneyIn;
import com.kakaobank.daina.assignment.exception.BizException;
import com.kakaobank.daina.assignment.mapper.AccInfoMapper;
import com.kakaobank.daina.assignment.mapper.CancelTarMapper;
import com.kakaobank.daina.assignment.mapper.HistorySimTransDetailMapper;
import com.kakaobank.daina.assignment.mapper.SimTransDetailMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReceiverMoneyService {

    private final SimTransDetailMapper simTransDetailMapper;
    private final VerificationService verificationService;
    private final AccountingService accountingService;
    private final HistorySimTransDetailMapper historySimTransDetailMapper;
    private final CancelTarMapper cancelTarMapper;
    private final AccInfoMapper accInfoMapper;

    private final Logger logger = LoggerFactory.getLogger(ReceiverMoneyService.class);

    public ReceiverMoneyService(AccInfoMapper accInfoMapper, CancelTarMapper cancelTarMapper, SimTransDetailMapper simTransDetailMapper, VerificationService verificationService, AccountingService accountingService, HistorySimTransDetailMapper historySimTransDetailMapper) {
        this.simTransDetailMapper = simTransDetailMapper;
        this.verificationService = verificationService;
        this.accountingService = accountingService;
        this.historySimTransDetailMapper = historySimTransDetailMapper;
        this.cancelTarMapper = cancelTarMapper;
        this.accInfoMapper = accInfoMapper;
    }

    public List<SimTransDetail> findTransactions(String reKkoUid) {
        List<SimTransDetail> simTransDetails = simTransDetailMapper.findSend(reKkoUid);

        return simTransDetails;
    }
    public SimTransDetail findInformation(Long tId) {
        SimTransDetail simTransDetail = simTransDetailMapper.findById(tId);

        return simTransDetail;
    }

    @Transactional
    public void receiveMoney(ReceiveMoneyIn receiveMoneyIn) {
        //간편이체내역 검증
        SimTransDetail byId = simTransDetailMapper.findById(receiveMoneyIn.gettId());

        //거래존재여부, 이체구분코드 확인
        boolean checkCode  = verificationService.checkCode(byId, "C1");

        //입력한 실명 일치 여부
        AccInfo accInfo = accInfoMapper.findBaccAll(receiveMoneyIn.getrAccId());
        if(accInfo == null){
            throw new BizException("계좌정보가 존재하지 않습니다.");
        }
        if(!accInfo.getCtmName().equals(byId.getrName())){
            throw new BizException("실명이 일치하지 않습니다.");
        }

        //취소대상관리여부 확인
        verificationService.checkCancelCode(byId, "이체");

        //계좌잔액업데이트
        accInfo.editMoney(accInfo.getBaccBalance()+byId.gettAmount());
        accInfoMapper.update(accInfo);

        HistorySimTransDetail historySimTransDetail = updateTransfer(receiveMoneyIn, byId);

        accountingService.accountingTransfer(byId, historySimTransDetail, "C3");
    }
    private HistorySimTransDetail updateTransfer(ReceiveMoneyIn receiveMoneyIn, SimTransDetail byId) {
        //간편이체거래내역 업데이트
        byId.editTcode("C3");
        simTransDetailMapper.updatetCode(byId);

        //상세내역 insert
        HistorySimTransDetail historySimTransDetail = HistorySimTransDetail.createNew(byId.gettId(),
                byId.getAccId(),
                byId.getCtmId(),
                byId.getrName(),
                receiveMoneyIn.getrAccId(),
                byId.getReKkoUid(),
                byId.gettAmount(),
                byId.getCommission(),
                LocalDate.now(),
                LocalTime.now(),
                byId.gettCode());
        historySimTransDetailMapper.insert(historySimTransDetail);

        return historySimTransDetail;
    }

}
