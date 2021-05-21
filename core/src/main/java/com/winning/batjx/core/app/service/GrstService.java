package com.winning.batjx.core.app.service;
import java.util.List;
import java.util.Map;


public interface GrstService {

    List<Map<String,Object>> selectKhhzxx(Map<String, Object> map) throws Exception;
    List<Map<String,Object>> initZbTree(Map<String, Object> map) throws Exception;
    void saveZbImport(Map<String, Object> map) throws Exception;
    List<Map<String,Object>> jxqsGraph(Map<String, Object> map) throws Exception;
    List<Map<String,Object>> jxfxGraph(Map<String, Object> map) throws Exception;
    List<Map<String,Object>> gzlOrgzzlTop10Graph(Map<String, Object> map) throws Exception;
    List<Map<String,Object>> khjgfxGraph(Map<String, Object> map) throws Exception;
    String getKhzqList(Map<String, Object> map) throws Exception;
    List<Map<String,Object>> queryJxkhplList(Map<String, Object> map) throws Exception;
    List<Map<String,Object>> queryJxfaList(Map<String, Object> map) throws Exception;
    List<Map<String,Object>> queryZdzbTable(Map<String, Object> map) throws Exception;

}
