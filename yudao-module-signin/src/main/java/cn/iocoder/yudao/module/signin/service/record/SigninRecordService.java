package cn.iocoder.yudao.module.signin.service.record;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.signin.controller.admin.record.vo.*;
import cn.iocoder.yudao.module.signin.dal.dataobject.record.RecordDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 签到记录 Service 接口
 *
 * @author 芋道源码
 */
public interface SigninRecordService {

    /**
     * 创建签到记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createRecord(@Valid RecordSaveReqVO createReqVO);

    /**
     * 更新签到记录
     *
     * @param updateReqVO 更新信息
     */
    void updateRecord(@Valid RecordSaveReqVO updateReqVO);

    /**
     * 删除签到记录
     *
     * @param id 编号
     */
    void deleteRecord(Long id);

    /**
    * 批量删除签到记录
    *
    * @param ids 编号
    */
    void deleteRecordListByIds(List<Long> ids);

    /**
     * 获得签到记录
     *
     * @param id 编号
     * @return 签到记录
     */
    RecordDO getRecord(Long id);

    /**
     * 获得签到记录分页
     *
     * @param pageReqVO 分页查询
     * @return 签到记录分页
     */
    PageResult<RecordDO> getRecordPage(RecordPageReqVO pageReqVO);

}