package cn.iocoder.yudao.module.signin.service.persons;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.signin.controller.admin.persons.vo.*;
import cn.iocoder.yudao.module.signin.dal.dataobject.persons.PersonsDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 签到系统-人员基本信息 Service 接口
 *
 * @author 芋道源码
 */
public interface PersonsService {

    /**
     * 创建签到系统-人员基本信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createPersons(@Valid PersonsSaveReqVO createReqVO);

    /**
     * 更新签到系统-人员基本信息
     *
     * @param updateReqVO 更新信息
     */
    void updatePersons(@Valid PersonsSaveReqVO updateReqVO);

    /**
     * 删除签到系统-人员基本信息
     *
     * @param id 编号
     */
    void deletePersons(String id);

    /**
    * 批量删除签到系统-人员基本信息
    *
    * @param ids 编号
    */
    void deletePersonsListByIds(List<String> ids);

    /**
     * 获得签到系统-人员基本信息
     *
     * @param id 编号
     * @return 签到系统-人员基本信息
     */
    PersonsDO getPersons(String id);

    /**
     * 获得签到系统-人员基本信息分页
     *
     * @param pageReqVO 分页查询
     * @return 签到系统-人员基本信息分页
     */
    PageResult<PersonsDO> getPersonsPage(PersonsPageReqVO pageReqVO);

}