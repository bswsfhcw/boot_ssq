package com.winning.batjx.core.khgl.khwj.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/***
 * @Description: 存图片
 * @Param:
 * @return:
 * @Author: huchengwei
 * @Date: 2020/4/23
 */
@Data
public class PicDTO implements Serializable {
    /**
     *
     */
    private List<PicDO> uploadFilesFls;
    private List<PicDO> deleteFilesFls;

    /**
     * 模块Id
     */
    private String jgbm;
    private String scr;
    private String tbid;
    private String zbz;
    private String pfms;
    private Boolean pfFlag;

}
