package com.framework.module.sn.service;

import com.framework.module.sn.domain.SnInfo;
import com.kratos.common.CrudService;
import com.kratos.common.PageResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SnInfoService extends CrudService<SnInfo> {
    /**
     * 批量上传SN
     * @param fileName 文件名称
     * @param profitFile 文件
     * @return 上传文件数量
     */
    int batchImport(String fileName, MultipartFile profitFile) throws Exception;

    /**
     * 管理员划拨SN，使用逗号分隔
     * @param sns
     * @param memberId
     */
    void transSnByAdmin(String sns, String memberId) throws Exception;

    /**
     * 会员划拨SN，使用逗号分隔
     * @param sns
     * @param memberId
     */
    void transSnByMember(String sns, String memberId,String currentMemberId) throws Exception;

    /**
     * 获取可分配的SN列表
     * @return SN列表
     * @param searchSn
     */
    List<String> getAvailableSn(String searchSn);

    /**
     * 查询SN信息
     * @param startSn
     * @param endSn
     * @param status
     * @param bindStatus
     * @param pageSize
     * @param pageNum
     * @param memberId
     * @return
     */
    PageResult<SnInfo> getAllSnInfo(String startSn, String endSn, SnInfo.Status status, SnInfo.BindStatus bindStatus, Integer pageSize, Integer pageNum, String memberId) throws Exception;

}
