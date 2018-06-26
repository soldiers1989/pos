package com.framework.module.shop.domain;

import com.kratos.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@ApiModel("商户")
public class Shop extends BaseEntity {
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "sn码")
    private String sn;
    @ApiModelProperty(value = "手机")
    private String mobile;
    @ApiModelProperty(value = "激活状态")
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private Status status;
    @ApiModelProperty(value = "交易总额")
    @Column(length = 11, precision = 2)
    private Double transactionAmount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public enum Status {
        ACTIVE("已激活"),
        UN_ACTIVE("未激活"),
        DELIVERED("已发货");
        private String displayName;
        Status(String displayName) {
            this.displayName = displayName;
        }
        public String getDisplayName() {
            return displayName;
        }
    }
}
