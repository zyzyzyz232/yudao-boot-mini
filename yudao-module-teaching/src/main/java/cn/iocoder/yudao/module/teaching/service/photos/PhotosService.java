package cn.iocoder.yudao.module.teaching.service.photos;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.teaching.controller.admin.photos.vo.*;
import cn.iocoder.yudao.module.teaching.dal.dataobject.photos.PhotosDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 人脸照片及特征数据 Service 接口
 *
 * @author 芋道源码
 */
public interface PhotosService {

    /**
     * 创建人脸照片及特征数据
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createPhotos(@Valid PhotosSaveReqVO createReqVO);

    /**
     * 更新人脸照片及特征数据
     *
     * @param updateReqVO 更新信息
     */
    void updatePhotos(@Valid PhotosSaveReqVO updateReqVO);

    /**
     * 删除人脸照片及特征数据
     *
     * @param id 编号
     */
    void deletePhotos(String id);

    /**
    * 批量删除人脸照片及特征数据
    *
    * @param ids 编号
    */
    void deletePhotosListByIds(List<String> ids);

    /**
     * 获得人脸照片及特征数据
     *
     * @param id 编号
     * @return 人脸照片及特征数据
     */
    PhotosDO getPhotos(String id);

    /**
     * 获得人脸照片及特征数据分页
     *
     * @param pageReqVO 分页查询
     * @return 人脸照片及特征数据分页
     */
    PageResult<PhotosDO> getPhotosPage(PhotosPageReqVO pageReqVO);

}