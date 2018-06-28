package com.framework.module.member.web;

import com.framework.module.member.domain.Achievement;
import com.framework.module.member.domain.AchievementDetail;
import com.framework.module.member.domain.MemberProfitRecords;
import com.framework.module.member.domain.ProfitMonthDetail;
import com.framework.module.member.service.MemberProfitRecordsService;
import com.kratos.common.AbstractCrudController;
import com.kratos.common.CrudService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Api(value = "会员管理")
@RestController
@RequestMapping("/api/memberprofit")
public class MemberProfitController extends AbstractCrudController<MemberProfitRecords> {
    private final MemberProfitRecordsService memberProfitService;

    @Override
    protected CrudService<MemberProfitRecords> getService() {
        return memberProfitService;
    }

    @Autowired
    public MemberProfitController(
            MemberProfitRecordsService memberProfitService) {
        this.memberProfitService = memberProfitService;
    }

    /**
     * 获取会员优惠卷
     */
    @ApiOperation(value = "导入成员收益")
    @RequestMapping(value = "/import/profit", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResponseEntity<String> userProfit(@RequestParam("profitFile") MultipartFile profitFile) {
        String fileName = profitFile.getOriginalFilename();
        int insertSize = 0;
        try {
            insertSize = memberProfitService.batchImport(fileName, profitFile);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(insertSize + " data has been handled.", HttpStatus.OK);
    }

    /**
     * 获取当月收益详情
     *
     * @param memberId   会员ID
     * @param startMonth 起始月份格式YYYYMM
     * @param size       一次请求长度
     * @return 收益详情结果
     * @throws Exception 异常
     */
    @ApiOperation(value = "获取收益详情")
    @RequestMapping(value = "/getMonthProfit/{memberId}/{startMonth}/{size}", method = RequestMethod.GET)
    public ResponseEntity<List<ProfitMonthDetail>> getMonthProfit(@PathVariable String memberId, @PathVariable String startMonth, @PathVariable Integer size) {
        List<ProfitMonthDetail> result = null;
        try {
            result = memberProfitService.getProfitByMonth(memberId, startMonth, size);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "按照月份获取业绩详情")
    @RequestMapping(value = "/getMonthAchievement/{memberId}/{startMonth}/{size}", method = RequestMethod.GET)
    public ResponseEntity<List<AchievementDetail>> getMonthAchievement(@PathVariable String memberId, @PathVariable String startMonth, @PathVariable Integer size) {
        List<AchievementDetail> result = null;
        try {
            result = memberProfitService.getAchievementByMonth(memberId, startMonth, size);
        } catch (ParseException e) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "按天获取业绩详情")
    @RequestMapping(value = "/getDayAchievement/{memberId}/{startDate}/{size}", method = RequestMethod.GET)
    public ResponseEntity<List<AchievementDetail>> getDayAchievement(@PathVariable String memberId, @PathVariable String startDate, @PathVariable Integer size) {
        List<AchievementDetail> result = null;
        try {
            result = memberProfitService.getAchievementByDate(memberId, startDate, size);
        } catch (ParseException e) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "获取业绩")
    @RequestMapping(value = "/getAchievement/{memberId}", method = RequestMethod.GET)
    public ResponseEntity<List<Achievement>> getAchievement(@PathVariable String memberId) {
        List<Achievement> result = null;
        try {
            result = result = memberProfitService.getAchievement(memberId);
        } catch (ParseException e) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "今日盟友等级商户")
    @RequestMapping(value = "/getAllyNewShopToday/{memberId}", method = RequestMethod.GET)
    public ResponseEntity<Integer> getAllyNewShopToday(@PathVariable String memberId) {
        try {
            return new ResponseEntity<>(memberProfitService.getAllyNewShopToday(memberId), HttpStatus.OK);
        } catch (ParseException e) {
            return new ResponseEntity<>(0, HttpStatus.OK);
        }
    }


}
