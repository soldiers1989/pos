package com.framework.module.member.domain;

import java.util.List;

/**
 * <p>Description: </p>
 *
 * @Auther: 张庆贺
 * @Date: 2018/6/29 13:24
 */
public class AllyMembers {
    List<String> sonList;
    List<String> grandSonList;
    Integer totalNum;

    public List<String> getSonList() {
        return sonList;
    }

    public void setSonList(List<String> sonList) {
        this.sonList = sonList;
    }

    public List<String> getGrandSonList() {
        return grandSonList;
    }

    public void setGrandSonList(List<String> grandSonList) {
        this.grandSonList = grandSonList;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }
}
