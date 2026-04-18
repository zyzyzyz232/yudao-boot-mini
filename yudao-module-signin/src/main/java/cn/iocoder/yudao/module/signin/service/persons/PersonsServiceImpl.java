package cn.iocoder.yudao.module.signin.service.persons;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.signin.controller.admin.persons.vo.*;
import cn.iocoder.yudao.module.signin.dal.dataobject.persons.PersonsDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.signin.dal.mysql.persons.PersonsMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.signin.enums.ErrorCodeConstants.*;

/**
 * 签到系统-人员基本信息 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class PersonsServiceImpl implements PersonsService {

    @Resource
    private PersonsMapper personsMapper;

    @Override
    public String createPersons(PersonsSaveReqVO createReqVO) {
        // 若请求中未传 personId，自动生成 UUID
        if (StrUtil.isBlank(createReqVO.getPersonId())) {
            createReqVO.setPersonId(IdUtil.fastSimpleUUID());
        }
        // 插入
        PersonsDO persons = BeanUtils.toBean(createReqVO, PersonsDO.class);
        personsMapper.insert(persons);

        // 返回
        return persons.getPersonId();
    }

    @Override
    public void updatePersons(PersonsSaveReqVO updateReqVO) {
        // 校验存在
        validatePersonsExists(updateReqVO.getPersonId());
        // 更新
        PersonsDO updateObj = BeanUtils.toBean(updateReqVO, PersonsDO.class);
        personsMapper.updateById(updateObj);
    }

    @Override
    public void deletePersons(String id) {
        // 校验存在
        validatePersonsExists(id);
        // 删除
        personsMapper.deleteById(id);
    }

    @Override
        public void deletePersonsListByIds(List<String> ids) {
        // 删除
        personsMapper.deleteByIds(ids);
        }


    private void validatePersonsExists(String id) {
        if (personsMapper.selectById(id) == null) {
            throw exception(PERSONS_NOT_EXISTS);
        }
    }

    @Override
    public PersonsDO getPersons(String id) {
        return personsMapper.selectById(id);
    }

    @Override
    public PageResult<PersonsDO> getPersonsPage(PersonsPageReqVO pageReqVO) {
        return personsMapper.selectPage(pageReqVO);
    }

}