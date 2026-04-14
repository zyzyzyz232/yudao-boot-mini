package cn.iocoder.yudao.module.signin.service.facephotos;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.signin.controller.admin.facephotos.vo.*;
import cn.iocoder.yudao.module.signin.dal.dataobject.facephotos.FacePhotosDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 签到系统-人脸照片特征 Service 接口
 *
 * @author 芋道源码
 */
public interface FacePhotosService {

    /**
     * 创建签到系统-人脸照片特征
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createFacePhotos(@Valid FacePhotosSaveReqVO createReqVO);

    /**
     * 更新签到系统-人脸照片特征
     *
     * @param updateReqVO 更新信息
     */
    void updateFacePhotos(@Valid FacePhotosSaveReqVO updateReqVO);

    /**
     * 删除签到系统-人脸照片特征
     *
     * @param id 编号
     */
    void deleteFacePhotos(String id);

    /**
    * 批量删除签到系统-人脸照片特征
    *
    * @param ids 编号
    */
    void deleteFacePhotosListByIds(List<String> ids);

    /**
     * 获得签到系统-人脸照片特征
     *
     * @param id 编号
     * @return 签到系统-人脸照片特征
     */
    FacePhotosDO getFacePhotos(String id);

    /**
     * 获得签到系统-人脸照片特征分页
     *
     * @param pageReqVO 分页查询
     * @return 签到系统-人脸照片特征分页
     */
    PageResult<FacePhotosDO> getFacePhotosPage(FacePhotosPageReqVO pageReqVO);

}