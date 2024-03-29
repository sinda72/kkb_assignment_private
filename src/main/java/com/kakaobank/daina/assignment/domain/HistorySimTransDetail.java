package com.kakaobank.daina.assignment.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class HistorySimTransDetail {
    private Long tId;
    private Long serialNum;
    private String accId;
    private String ctmId;
    private String rName;
    private String rAccId;
    private String reKkoUid;
    private Long tAmount;
    private Long commission;
    private LocalDate nDate;
    private LocalTime nTime;
    private String tCode;
    private String rCode;

    public HistorySimTransDetail() {
    }

    public HistorySimTransDetail(Long tId,
                                 Long serialNum,
                                 String accId,
                                 String ctmId,
                                 String rName,
                                 String rAccId,
                                 String reKkoUid,
                                 Long tAmount,
                                 Long commission,
                                 LocalDate nDate,
                                 LocalTime nTime,
                                 String tCode,
                                 String rCode) {
        this.tId = tId;
        this.serialNum = serialNum;
        this.accId = accId;
        this.ctmId = ctmId;
        this.rName = rName;
        this.reKkoUid = reKkoUid;
        this.tAmount = tAmount;
        this.commission = commission;
        this.nDate = nDate;
        this.nTime = nTime;
        this.tCode = tCode;
        this.rCode = rCode;
        this.rAccId = rAccId;
    }

    public String getrAccId() {
        return rAccId;
    }

    public void setrAccId(String rAccId) {
        this.rAccId = rAccId;
    }

    public Long gettId() {
        return tId;
    }

    public void settId(Long tId) {
        this.tId = tId;
    }

    public Long getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(Long serialNum) {
        this.serialNum = serialNum;
    }

    public String getAccId() {
        return accId;
    }

    public void setAccId(String accId) {
        this.accId = accId;
    }

    public String getCtmId() {
        return ctmId;
    }

    public void setCtmId(String ctmId) {
        this.ctmId = ctmId;
    }

    public String getrName() {
        return rName;
    }

    public void setrName(String rName) {
        this.rName = rName;
    }

    public String getReKkoUid() {
        return reKkoUid;
    }

    public void setReKkoUid(String reKkoUid) {
        this.reKkoUid = reKkoUid;
    }

    public Long gettAmount() {
        return tAmount;
    }

    public void settAmount(Long tAmount) {
        this.tAmount = tAmount;
    }

    public Long getCommission() {
        return commission;
    }

    public void setCommission(Long commission) {
        this.commission = commission;
    }

    public LocalDate getnDate() {
        return nDate;
    }

    public void setnDate(LocalDate nDate) {
        this.nDate = nDate;
    }

    public LocalTime getnTime() {
        return nTime;
    }

    public void setnTime(LocalTime nTime) {
        this.nTime = nTime;
    }

    public String gettCode() {
        return tCode;
    }

    public void settCode(String tCode) {
        this.tCode = tCode;
    }

    public String getrCode() {
        return rCode;
    }

    public void setrCode(String rCode) {
        this.rCode = rCode;
    }

    //생성자로 만들지 않고, static 함수로 만들었는지
    //함수 명칭으로 기능에 대한 직관적인 이해 +
    //생성자로 만들 경우 service에서 이 로직을 구현해야함
    public static HistorySimTransDetail createNew(Long tId, String accId, String ctmId, String rName, String rAccId, String reKkoUid, Long tAmount, Long commission, LocalDate nDate, LocalTime nTime, String tCode) {
        HistorySimTransDetail historySimTransDetail = new HistorySimTransDetail(
                tId,
                null,
                accId,
                ctmId,
                rName,
                rAccId,
                reKkoUid,
                tAmount,
                commission,
                nDate,
                nTime,
                tCode,
                "N");

        return historySimTransDetail;
    }//static을 사용하면 new를 하지 않아도 메모리에 올리고 사용 가능하다.
}
