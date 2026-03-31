package cn.iocoder.yudao.module.teaching.service.video;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.teaching.controller.admin.video.vo.*;
import cn.iocoder.yudao.module.teaching.dal.dataobject.video.VideoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 教学视频 Service 接口
 *
 * @author 芋道源码
 */
public interface VideoService {

    /**
     * 创建教学视频
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createVideo(@Valid VideoSaveReqVO createReqVO);

    /**
     * 更新教学视频
     *
     * @param updateReqVO 更新信息
     */
    void updateVideo(@Valid VideoSaveReqVO updateReqVO);

    /**
     * 删除教学视频
     *
     * @param id 编号
     */
    void deleteVideo(Long id);

    /**
    * 批量删除教学视频
    *
    * @param ids 编号
    */
    void deleteVideoListByIds(List<Long> ids);

    /**
     * 获得教学视频
     *
     * @param id 编号
     * @return 教学视频
     */
    VideoDO getVideo(Long id);

    /**
     * 获得教学视频分页
     *
     * @param pageReqVO 分页查询
     * @return 教学视频分页
     */
    PageResult<VideoDO> getVideoPage(VideoPageReqVO pageReqVO);

}