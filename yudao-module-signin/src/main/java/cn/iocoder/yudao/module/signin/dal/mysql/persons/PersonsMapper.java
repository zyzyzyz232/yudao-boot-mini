package cn.iocoder.yudao.module.signin.dal.mysql.persons;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.signin.dal.dataobject.persons.PersonsDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.signin.controller.admin.persons.vo.*;

/**
 * 签到系统-人员基本信息 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface PersonsMapper extends BaseMapperX<PersonsDO> {

    default PageResult<PersonsDO> selectPage(PersonsPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PersonsDO>()
                .likeIfPresent(PersonsDO::getName, reqVO.getName())
                .eqIfPresent(PersonsDO::getDepartment, reqVO.getDepartment())
                .eqIfPresent(PersonsDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(PersonsDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PersonsDO::getId));
    }

}