package com.winning.batjx.core.khgl.khwj.mapper;

import com.winning.batjx.core.khgl.khwj.domain.*;
import com.winning.batjx.core.khgl.khwj.vo.*;

import java.util.List;
import java.util.Map;

/**
 * @author yxf@winning.com.cn
 * @date 2021/3/9
 * @time 10:51
 * @description
 */

public interface WjkhMapper {

    List<MbzbdzVo> queryPageList(Map map);

    List<KhfaDo> queryKhfa(Map para);

    List<Map<String, String>> queryKhfaKhqj(Map para);

    List<Map<String, String>> queryKhfaKhdx(Map para);

    MbjgDo queryMbjg(MbjgDo mbjgDo);

    WjjgDo queryWjjg(WjjgDo wjjgDo);

    void insertWjjg(WjjgDo wjjgDo);

    void updateWjjg(WjjgDo wjjgDo);

    void insertMbjg(MbjgDo mbjgDo);

    List<WjxxPageDo> getWjxxPageInfo(Map para);

    List<MbflVo> getMbfl(Map para);

    List<MbtmVo> getMbtm(MbflVo fl);

    void insertDtjg(DtjgDo dtjgDo);

    List<WjxxPageDo> getSelectXx(Map map);

    void updateTmdf(Map para);

    List<TmxxVo> getTmxx(Map map);

    WjmbVo getWjmb(Map para);

    void insertDtjgxx(DtjgxxDo jgxx);

    void deleteDtjgxx(WjjgDo wjjgVo);

    List<TkxxVo> getTkxx(Map tmMap);

    void insertFile(Map para);

    List<FileVo> getFlFile(Map fl);

    void removeFile(Map para);

    List<TktgsxxVo> getTktgsxx(TkxxVo tkxxVo);

    void updateSjtbKhdf(WjjgDo wjjgDo);
}
