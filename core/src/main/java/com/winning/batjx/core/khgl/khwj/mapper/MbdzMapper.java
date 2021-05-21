package com.winning.batjx.core.khgl.khwj.mapper;

import com.winning.batjx.core.khgl.khwj.domain.MbzbdzDO;
import com.winning.batjx.core.khgl.khwj.domain.WjmbDO;
import com.winning.batjx.core.khgl.khwj.vo.MbzbdzVo;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * @program: boot-batjx
 * @description
 * @author: yyh
 * @create: 2021-03-02 15:04
 **/
public interface MbdzMapper  {


      /**
       * 查询分页数据
       * **/
      List<MbzbdzVo> queryPageList(Map map);


      /**
       * 根据指标查询对照
       * @param mbzbdz
       * **/
      MbzbdzDO getMbdzByKey(MbzbdzDO mbzbdz);

      /**
       * 新增
       * @param mbzbdz
       * **/
      void insertMbdz(MbzbdzDO mbzbdz);

      /**
       * 修改
       * @param mbzbdz
       * **/
      void updateMbdz(MbzbdzDO mbzbdz);

    /**
     * 获取模板标号
     * @param mbbh
     * **/
     WjmbDO queryMbxx(@Param("mbbh") String mbbh);
}
