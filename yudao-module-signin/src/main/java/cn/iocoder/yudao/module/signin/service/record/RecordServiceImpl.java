package cn.iocoder.yudao.module.signin.service.record;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.signin.controller.admin.record.vo.*;
import cn.iocoder.yudao.module.signin.dal.dataobject.record.RecordDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.signin.dal.mysql.record.RecordMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.signin.enums.ErrorCodeConstants.*;

/**
 * 签到记录 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class RecordServiceImpl implements SigninRecordService {

    @Resource
    private RecordMapper recordMapper;

    @Override
    public Long createRecord(RecordSaveReqVO createReqVO) {
        // 插入
        RecordDO signinRecord = BeanUtils.toBean(createReqVO, RecordDO.class);
        recordMapper.insert(signinRecord);

        // 返回
        return signinRecord.getId();
    }

    @Override
    public void updateRecord(RecordSaveReqVO updateReqVO) {
        // 校验存在
        validateRecordExists(updateReqVO.getId());
        // 更新
        RecordDO updateObj = BeanUtils.toBean(updateReqVO, RecordDO.class);
        recordMapper.updateById(updateObj);
    }

    @Override
    public void deleteRecord(Long id) {
        // 校验存在
        validateRecordExists(id);
        // 删除
        recordMapper.deleteById(id);
    }

    @Override
        public void deleteRecordListByIds(List<Long> ids) {
        // 删除
        recordMapper.deleteByIds(ids);
        }


    private void validateRecordExists(Long id) {
        if (recordMapper.selectById(id) == null) {
            throw exception(RECORD_NOT_EXISTS);
        }
    }

    @Override
    public RecordDO getRecord(Long id) {
        return recordMapper.selectById(id);
    }

    @Override
    public PageResult<RecordDO> getRecordPage(RecordPageReqVO pageReqVO) {
        return recordMapper.selectPage(pageReqVO);
    }

}