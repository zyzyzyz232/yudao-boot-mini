package cn.iocoder.yudao.module.signin.dal.dataobject.persons;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 签到系统-人员基本信息 DO
 *
 * @author 芋道源码
 */
@TableName("signin_persons")
@KeySequence("signin_persons_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonsDO extends BaseDO {

    /**
     * 人员唯一标识(可使用UUID)
     */
    @TableId(type = IdType.INPUT)
    private String personId;
    /**
     * 人员姓名
     */
    private String name;
    /**
     * 所属部门/组织
     */
    private String department;
    /**
     * 状态：1-正常, 0-禁用
     */
    private Boolean status;


}