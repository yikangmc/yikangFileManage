package com.yikangyiliao.fileManage.entity;

import java.util.Date;

public class Logs {
    private Long logsId;

    private Long userId;

    private String equipment;

    private Integer operationType;

    private Date openTime;

    private Date closeTime;

    private Long taglibId;

    private Integer homePageBanner;

    private Integer communityPageBanner;

    private Integer pageIdentify;

    private String typeIdentify;

    private Long typeId;

    private Date startTime;

    private Date endTime;

    private Date createTime;

    public Long getLogsId() {
        return logsId;
    }

    public void setLogsId(Long logsId) {
        this.logsId = logsId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public Long getTaglibId() {
        return taglibId;
    }

    public void setTaglibId(Long taglibId) {
        this.taglibId = taglibId;
    }

    public Integer getHomePageBanner() {
        return homePageBanner;
    }

    public void setHomePageBanner(Integer homePageBanner) {
        this.homePageBanner = homePageBanner;
    }

    public Integer getCommunityPageBanner() {
        return communityPageBanner;
    }

    public void setCommunityPageBanner(Integer communityPageBanner) {
        this.communityPageBanner = communityPageBanner;
    }

    public Integer getPageIdentify() {
        return pageIdentify;
    }

    public void setPageIdentify(Integer pageIdentify) {
        this.pageIdentify = pageIdentify;
    }

    public String getTypeIdentify() {
        return typeIdentify;
    }

    public void setTypeIdentify(String typeIdentify) {
        this.typeIdentify = typeIdentify == null ? null : typeIdentify.trim();
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}