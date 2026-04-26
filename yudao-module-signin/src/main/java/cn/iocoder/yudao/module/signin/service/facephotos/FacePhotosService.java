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
     * @param imageUrl    文件上传后的访问路径
     * @param fileName    原始文件名（用于识别格式）
     * @param sizeKb      文件大小（KB）
     * @return 编号
     */
    String createFacePhotos(@Valid FacePhotosSaveReqVO createReqVO,
                            String imageUrl, String fileName, int sizeKb);

    /**
     * 更新签到系统-人脸照片特征
     *
     * @param updateReqVO 更新信息
     * @param imageUrl    新照片的访问路径，为 null 时不更新照片
     * @param fileName    原始文件名，为 null 时不更新
     * @param sizeKb      文件大小（KB），为 null 时不更新
     */
    void updateFacePhotos(@Valid FacePhotosSaveReqVO updateReqVO,
                          String imageUrl, String fileName, Integer sizeKb);

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
     * 按学员编号解析用于「仅 studentNo 更新」接口的目标记录：优先主图，否则取更新时间最新的一条
     *
     * @param studentNo 学员编号
     * @return 人脸记录，不存在则返回 null
     */
    FacePhotosDO getFacePhotoForUpdateByStudentNo(String studentNo);

    /**
     * 按班级 + 学员编号解析用于更新的目标记录：优先主图，否则取更新时间最新的一条
     *
     * @param classId   班级编号
     * @param studentNo 学员编号
     * @return 人脸记录，不存在则返回 null
     */
    FacePhotosDO getFacePhotoForUpdateByStudentNoAndClassId(Long classId, String studentNo);

    /**
     * 获得签到系统-人脸照片特征分页
     *
     * @param pageReqVO 分页查询
     * @return 签到系统-人脸照片特征分页
     */
    PageResult<FacePhotosDO> getFacePhotosPage(FacePhotosPageReqVO pageReqVO);

}