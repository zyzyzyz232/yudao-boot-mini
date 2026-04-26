package cn.iocoder.yudao.module.signin.controller.admin.facephotos.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "管理后台 - 人脸特征同步结果")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacePhotoSyncFeatureRespVO {

    @Schema(description = "人脸照片记录 ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String photoId;

}
