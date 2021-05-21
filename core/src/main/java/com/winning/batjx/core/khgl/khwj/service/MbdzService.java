package com.winning.batjx.core.khgl.khwj.service;

import com.winning.batjx.core.khgl.khwj.domain.WjmbDO;
import com.winning.batjx.core.khgl.khwj.domain.MbzbdzDO;
import com.winning.batjx.core.common.util.PageResult;

import java.util.Map;

/**
 * @program: boot-batjx
 * @description
 * @author: yyh
 * @create: 2021-03-02 11:15
 **/
public interface MbdzService {

    PageResult getZbdzList(Map<String, Object> params);

    void saveMbdz(MbzbdzDO mbzbdzDO);

    WjmbDO queryWjmbBymbbh(String mbbh);
}
