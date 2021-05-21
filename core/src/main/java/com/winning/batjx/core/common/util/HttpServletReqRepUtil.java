package com.winning.batjx.core.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @company:卫宁健康科技集团股份有限公司
 * @team:基层业务研发部-基层产品支持组
 * @program: batjx
 * @description: HttpServletReqRep的工具类
 * @author: liuyanchao
 * @create: 2020/7/7 14:35
 * @History: <author>          <time>          <version>          <desc>
 * 姓名             修改时间           版本号              描述
 */
public class HttpServletReqRepUtil {

    /**
     * 功能描述:〈request的数据转换为response〉
     * @Param:  [request, response]
     * @Return: void
     * @Author: liuyanchao
     * @Date:  2020/7/7  14:37
     * @Despition:
     */
    public static void convertHttpServletReponse(HttpServletRequest request,
                                                 HttpServletResponse response){
        response.setHeader("Version",request.getHeader("Version"));
        response.setHeader("ContentType",request.getHeader("ContentType"));
        response.setHeader("OrgId",request.getHeader("OrgId"));
        response.setHeader("AppId",request.getHeader("AppId"));
        response.setHeader("RecOrgId",request.getHeader("RecOrgId"));
        response.setHeader("RecAppId",request.getHeader("RecAppId"));
        response.setHeader("MessageId",request.getHeader("MessageId"));
        response.setHeader("Timestamp",request.getHeader("Timestamp"));
    }

    /***
     * 获取 request 中 json 字符串的内容
     *
     * @param request
     * @return : <code>byte[]</code>
     * @throws IOException
     */
    public static String getRequestJsonString(HttpServletRequest request)
            throws IOException {
        String submitMehtod = request.getMethod();
        // GET
        if (submitMehtod.equals("GET")) {
            return new String(request.getQueryString().getBytes("iso-8859-1"),"utf-8").replaceAll("%22", "\"");
            // POST
        } else {
            return getRequestPostStr(request);
        }
    }

    /**
     * 描述:获取 post 请求的 byte[] 数组
     * <pre>
     * 举例：
     * </pre>
     * @param request
     * @return
     * @throws IOException
     */
    public static byte[] getRequestPostBytes(HttpServletRequest request)
            throws IOException {
        int contentLength = request.getContentLength();
        if(contentLength<0){
            return null;
        }
        byte buffer[] = new byte[contentLength];
        for (int i = 0; i < contentLength;) {

            int readlen = request.getInputStream().read(buffer, i,
                    contentLength - i);
            if (readlen == -1) {
                break;
            }
            i += readlen;
        }
        return buffer;
    }

    /**
     * 描述:获取 post 请求内容
     * <pre>
     * 举例：
     * </pre>
     * @param request
     * @return
     * @throws IOException
     */
    public static String getRequestPostStr(HttpServletRequest request)
            throws IOException {
        byte buffer[] = getRequestPostBytes(request);
        String charEncoding = request.getCharacterEncoding();
        if (charEncoding == null) {
            charEncoding = "UTF-8";
        }
        return new String(buffer, charEncoding);
    }

    /**
     * 功能描述:〈获取头信息〉
     * @Param:  [jsonObject]
     * @Return: java.util.Map<java.lang.String,java.lang.String>
     * @Author: liuyanchao
     * @Date:  2020/7/10  17:07
     * @Despition:
     */
    public static Map<String,String> mdmHeadJsonToMap(JSONObject jsonObject){
        JSONObject json = jsonObject.getJSONObject("Request").getJSONObject("Head");
        return JSON.parseObject(json.toJSONString(),Map.class);
    }

    /**
     * 功能描述:〈获取数据信息〉
     * @Param:  [jsonObject]
     * @Return: java.util.Map<java.lang.String,java.lang.String>
     * @Author: liuyanchao
     * @Date:  2020/7/10  17:07
     * @Despition:
     */
    public static Map<String,String> mdmItemJsonToMap(JSONObject jsonObject){
        JSONObject json = jsonObject.getJSONObject("Request").getJSONObject("Body").getJSONObject("DataItem");
        return JSON.parseObject(json.toJSONString(),Map.class);
    }

    /**
     * 功能描述:〈〉
     * @Param:  []
     * @Return: java.util.Map<java.lang.String,java.lang.String>
     * @Author: liuyanchao
     * @Date:  2020/7/11  13:07
     * @Despition:
     */
    public static Map<String,Map<String,String>> mdmRepHeadMap(Map<String,String> reqHeadMap, boolean flag){
        Map<String,String> repHeadMap = new HashMap<>();
        Map<String,Map<String,String>> headMap = new HashMap<>();
        //转换返回消息头
        repHeadMap.put("Version",reqHeadMap.get("Version"));
        repHeadMap.put("ContentType",reqHeadMap.get("ContentType"));
        repHeadMap.put("OrgId",reqHeadMap.get("RecOrgId"));
        repHeadMap.put("AppId",reqHeadMap.get("RecAppId"));
        repHeadMap.put("RecOrgId",reqHeadMap.get("OrgId"));
        repHeadMap.put("RecAppId",reqHeadMap.get("AppId"));
        repHeadMap.put("MessageId",reqHeadMap.get("MessageId"));
        repHeadMap.put("Timestamp",reqHeadMap.get("Timestamp"));
        repHeadMap.put("AckCode","100");
        repHeadMap.put("AckMessage","同步成功！");
        if(!flag){
            repHeadMap.put("AckCode","200");
            repHeadMap.put("AckMessage","同步失败！");
        }
        headMap.put("Head",repHeadMap);
        return headMap;
    }
}
