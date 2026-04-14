package cn.iocoder.yudao.module.signin.service.signin;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.signin.controller.admin.signin.vo.SigninRecordPageReqVO;
import cn.iocoder.yudao.module.signin.controller.admin.signin.vo.SigninRecordSaveReqVO;
import cn.iocoder.yudao.module.signin.dal.dataobject.signin.SigninRecordDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 签到记录 Service 接口
 *
 * @author yudao
 */
public interface SigninRecordService {

    /**
     * 创建签到记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSigninRecord(@Valid SigninRecordSaveReqVO createReqVO);

    /**
     * 更新签到记录
     *
     * @param updateReqVO 更新信息
     */
    void updateSigninRecord(@Valid SigninRecordSaveReqVO updateReqVO);

    /**
     * 删除签到记录
     *
     * @param id 编号
     */
    void deleteSigninRecord(Long id);

    /**
     * 批量删除签到记录
     *
     * @param ids 编号列表
     */
    void deleteSigninRecordListByIds(List<Long> ids);

    /**
     * 获得签到记录
     *
     * @param id 编号
     * @return 签到记录
     */
    SigninRecordDO getSigninRecord(Long id);

    /**
     * 获得签到记录分页
     *
     * @param pageReqVO 分页查询
     * @return 签到记录分页
     */
    PageResult<SigninRecordDO> getSigninRecordPage(SigninRecordPageReqVO pageReqVO);

}
