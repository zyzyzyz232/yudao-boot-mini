package cn.iocoder.yudao.module.signin.service.signin;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.signin.controller.admin.signin.vo.SigninRecordPageReqVO;
import cn.iocoder.yudao.module.signin.controller.admin.signin.vo.SigninRecordSaveReqVO;
import cn.iocoder.yudao.module.signin.dal.dataobject.signin.SigninRecordDO;
import cn.iocoder.yudao.module.signin.dal.mysql.signin.SigninRecordMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.signin.enums.ErrorCodeConstants.SIGNIN_NOT_EXISTS;

/**
 * 签到记录 Service 实现类
 *
 * @author yudao
 */
@Service
@Validated
public class SigninRecordServiceImpl implements SigninRecordService {

    @Resource
    private SigninRecordMapper signinRecordMapper;

    @Override
    public Long createSigninRecord(SigninRecordSaveReqVO createReqVO) {
        SigninRecordDO signinRecord = BeanUtils.toBean(createReqVO, SigninRecordDO.class);
        signinRecordMapper.insert(signinRecord);
        return signinRecord.getId();
    }

    @Override
    public void updateSigninRecord(SigninRecordSaveReqVO updateReqVO) {
        validateSigninRecordExists(updateReqVO.getId());
        SigninRecordDO updateObj = BeanUtils.toBean(updateReqVO, SigninRecordDO.class);
        signinRecordMapper.updateById(updateObj);
    }

    @Override
    public void deleteSigninRecord(Long id) {
        validateSigninRecordExists(id);
        signinRecordMapper.deleteById(id);
    }

    @Override
    public void deleteSigninRecordListByIds(List<Long> ids) {
        signinRecordMapper.deleteByIds(ids);
    }

    private void validateSigninRecordExists(Long id) {
        if (signinRecordMapper.selectById(id) == null) {
            throw exception(SIGNIN_NOT_EXISTS);
        }
    }

    @Override
    public SigninRecordDO getSigninRecord(Long id) {
        return signinRecordMapper.selectById(id);
    }

    @Override
    public PageResult<SigninRecordDO> getSigninRecordPage(SigninRecordPageReqVO pageReqVO) {
        return signinRecordMapper.selectPage(pageReqVO);
    }

}
