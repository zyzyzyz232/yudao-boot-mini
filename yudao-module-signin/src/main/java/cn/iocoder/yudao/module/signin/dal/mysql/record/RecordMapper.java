package cn.iocoder.yudao.module.signin.dal.mysql.record;

import java.util.*;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.signin.dal.dataobject.record.RecordDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.signin.controller.admin.record.vo.*;

/**
 * 签到记录 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface RecordMapper extends BaseMapperX<RecordDO> {

    default PageResult<RecordDO> selectPage(RecordPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RecordDO>()
                .eqIfPresent(RecordDO::getLessonId, reqVO.getLessonId())
                .eqIfPresent(RecordDO::getPersonId, reqVO.getPersonId())
                .eqIfPresent(RecordDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(RecordDO::getSignTime, reqVO.getSignTime())
                .betweenIfPresent(RecordDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RecordDO::getId));
    }

    default RecordDO selectByLessonIdAndPersonId(Long lessonId, String personId) {
        List<RecordDO> list = selectList(new LambdaQueryWrapperX<RecordDO>()
                .eq(RecordDO::getLessonId, lessonId)
                .eq(RecordDO::getPersonId, personId)
                .orderByDesc(RecordDO::getId)
                .last("LIMIT 1"));
        return CollUtil.getFirst(list);
    }

}