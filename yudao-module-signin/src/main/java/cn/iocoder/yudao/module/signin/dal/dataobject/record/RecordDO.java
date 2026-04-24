package cn.iocoder.yudao.module.signin.dal.dataobject.record;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 签到记录 DO
 *
 * @author 芋道源码
 */
@TableName("signin_record")
@KeySequence("signin_record_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 课堂编号
     */
    private Long lessonId;
    /**
     * 学员编号
     */
    private String personId;
    /**
     * 签到状态（0-未签到，1-已签到，2-迟到，3-缺勤等）
     */
    private Integer status;
    /**
     * 实际签到时间
     */
    private LocalDateTime signTime;


}
