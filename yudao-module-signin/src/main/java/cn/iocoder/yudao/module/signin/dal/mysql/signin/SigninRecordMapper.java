package cn.iocoder.yudao.module.signin.dal.mysql.signin;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.signin.controller.admin.signin.vo.SigninRecordPageReqVO;
import cn.iocoder.yudao.module.signin.dal.dataobject.signin.SigninRecordDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 签到记录 Mapper
 *
 * @author yudao
 */
@Mapper
public interface SigninRecordMapper extends BaseMapperX<SigninRecordDO> {

    default PageResult<SigninRecordDO> selectPage(SigninRecordPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SigninRecordDO>()
                .eqIfPresent(SigninRecordDO::getUserId, reqVO.getUserId())
                .eqIfPresent(SigninRecordDO::getSigninDate, reqVO.getSigninDate())
                .eqIfPresent(SigninRecordDO::getSigninType, reqVO.getSigninType())
                .betweenIfPresent(SigninRecordDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SigninRecordDO::getId));
    }

}
