package cn.iocoder.yudao.module.signin.dal.dataobject.signin;

import lombok.*;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 签到记录 DO
 *
 * @author yudao
 */
@TableName("signin_record")
@KeySequence("signin_record_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SigninRecordDO extends BaseDO {

    /**
     * 签到记录编号
     */
    @TableId
    private Long id;

    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 签到日期
     */
    private LocalDate signinDate;

    /**
     * 签到类型（0：正常，1：补签，2：代签）
     */
    private Integer signinType;

    /**
     * 备注
     */
    private String remark;

}
