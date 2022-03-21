package com.kakaobank.daina.assignment.service;

import com.kakaobank.daina.assignment.domain.CancelTar;
import com.kakaobank.daina.assignment.domain.SimTransDetail;
import com.kakaobank.daina.assignment.mapper.CancelTarMapper;
import com.kakaobank.daina.assignment.mapper.SimTransDetailMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CheckTimeService {
    private final VerificationService verificationService;
    private final CancelService cancelService;
    private final SimTransDetailMapper simTransDetailMapper;
    private final CancelTarMapper cancelTarMapper;

    public CheckTimeService(VerificationService verificationService, CancelService cancelService, SimTransDetailMapper simTransDetailMapper, CancelTarMapper cancelTarMapper) {
        this.verificationService = verificationService;
        this.cancelService = cancelService;
        this.simTransDetailMapper = simTransDetailMapper;
        this.cancelTarMapper = cancelTarMapper;
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void cancelProcess(CancelTar cancelTar){

        Boolean check = verificationService.checkAccState(cancelTar.gettId(), cancelTar.getAccId());
        if(check == true){
            //이체취소 호출
            cancelService.cancelMoney(cancelTar.gettId(), "C4");
        }

    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void cancelTarget(SimTransDetail simTransDetail){
        simTransDetail.editCancelCode();
        simTransDetailMapper.updateCancelCode(simTransDetail);
        cancelTarMapper.insert(CancelTar.createNew(simTransDetail));
    }

}
