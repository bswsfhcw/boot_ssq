package com.winning.batjx.core.khgl.khwj.service;

import com.winning.batjx.core.khgl.khwj.domain.KhfaDo;
import com.winning.batjx.core.khgl.khwj.vo.WjmbVo;
import com.winning.batjx.core.common.util.PageRequest;
import com.winning.batjx.core.common.util.PageResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author yxf@winning.com.cn
 * @date 2021/3/9
 * @time 10:48
 * @description
 */

public interface WjkhService {

    PageResult getPageList(PageRequest pageRequest,Map para);

    List<KhfaDo> getKhfas(String khnf, String khpldm);

    List<Map<String,String>> getKhfaKhqj(String faid);

    List<Map<String,String>> getKhfaKhdx(String faid,String khqjdm);

    void saveAnswer(WjmbVo wjmbVo, Map para);

    WjmbVo getResult(Map map);

    void insertFile(MultipartFile fjfile, Map para);

    void removefile(Map para);
}
