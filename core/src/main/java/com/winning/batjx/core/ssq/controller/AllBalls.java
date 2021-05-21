package com.winning.batjx.core.ssq.controller;
/**
 * @Author: huchengwei * @Date: 2021/4/27 14:01 * @Description: *
 */

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.winning.batjx.core.common.BaseController;
import com.winning.batjx.core.common.util.PageResult;
import com.winning.batjx.core.common.util.PageUtils;
import com.winning.batjx.core.ssq.mapper.SsqMapper;
import com.winning.batjx.core.ssq.vo.SsqVo;
import com.winning.jbase.common.domain.ResponseMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

/**
 * @author     ：huchengwei
 * @date       ：Created in 2021/4/27 14:01
 * @description：
 * @modified By：
 * @version: $
 */
@RestController
@RequestMapping("/ssq")
@Api(description = "双色球", tags = "双色球")
public class AllBalls  extends BaseController {
    private static List<SsqVo> listSsqVo = new ArrayList<>();
    private static int pcjd = 0;
    private static int total = 0;
    @Resource
    private SsqMapper ssqMapper;

    @ApiOperation(position = 2,value = "分页获取信息",notes = "分页")
    @PostMapping("/getSsqPageList")
    public ResponseMessage getSsqPageList(@RequestBody Map<String,Object> params){
        String ksqi=(String)params.get("ksqi");
        String jsrq=(String)params.get("jsrq");
        int pageNum =params.get("pageNum")==null?1: (Integer)params.get("pageNum");
        int pageSize =params.get("pageSize")==null?20:(Integer)params.get("pageSize");
        ResponseMessage message = new ResponseMessage();
        try {
            // 开启分页插件,放在查询语句上面 帮助生成分页语句
            PageHelper.startPage(pageNum, pageSize);
            //注意分页工具只对PageHelper下的第一个select语句生效
            List<SsqVo> list = ssqMapper.queryList(params);
            PageResult pageResult= PageUtils.getPageResult(new PageInfo<SsqVo>(list));
            message.setCode("T").setMessage("成功");
            message.setData(pageResult);
        } catch (Exception e){
            logger.error("错误信息：",e);
            message.setCode("F").setMessage("失败");
        }
        return message;
    }

    @ApiOperation(position = 2,value = "getPcjd",notes = "")
    @PostMapping("/getPcjd")
    public ResponseMessage getPcjd(@RequestBody Map<String,Object> params){
        ResponseMessage message = new ResponseMessage();
        try {
            message.setCode("T").setMessage("成功");
            message.setData(pcjd);
        } catch (Exception e){
            logger.error("错误信息：",e);
            message.setCode("F").setMessage("失败");
        }
        return message;
    }
    @ApiOperation(value = "不分页获取信息",notes = "不分页")
    @PostMapping("/lstj")
    public ResponseMessage lstj(@RequestBody Map<String,Object> params){
        ResponseMessage message = new ResponseMessage();
        message.setCode("T").setMessage("成功");
        try {
            // 开启分页插件,放在查询语句上面 帮助生成分页语句
            //注意分页工具只对PageHelper下的第一个select语句生效
            List<Map> list = ssqMapper.lstj(params);
            Map[] hH = new Map[33];//红号最多33
            Map[] lH = new Map[16];
            int indexhH=0,indexlH=0;//实际红号数组内下标
            for (int i = 0; i < list.size(); i++) {
                Map maph = list.get(i);
                if("H".equalsIgnoreCase((String)maph.get("hl"))){
                    hH[indexhH] = maph;
                    indexhH++;
                }else if("L".equalsIgnoreCase((String)maph.get("hl"))){
                    lH[indexlH] = maph;
                    indexlH++;
                }
            }
            //indexhH:红号个数
            //排序 数量少到多
            for (int i = 0; i < indexhH-1; i++) {
                //外层循环，遍历次数
                for (int j = 0; j < indexhH - i - 1; j++) {
                    //内层循环，升序（如果前一个值比后一个值大，则交换）
                    //内层循环一次，获取一个最大值
                    if ((Long)(hH[j].get("cc")) >(Long)(hH[j + 1].get("cc"))) {
                        Map temp = hH[j + 1];
                        hH[j + 1] = hH[j];
                        hH[j] = temp;
                    }
                }
            }
            for (int i = 0; i < indexlH-1; i++) {
                //外层循环，遍历次数
                for (int j = 0; j < indexlH- i - 1; j++) {
                    //内层循环，升序（如果前一个值比后一个值大，则交换）
                    //内层循环一次，获取一个最大值
                    if ((Long)(lH[j].get("cc")) >(Long)(lH[j + 1].get("cc"))) {
                        Map temp = lH[j + 1];
                        lH[j + 1] = lH[j];
                        lH[j] = temp;
                    }
                }
            }
            //柱状图 号码 和 号码对应的数量
            Map chart1 = new HashMap();
            String[] hhs = new String[indexhH];
            Long[] hhsl = new Long[indexhH];
            String[] lhs = new String[indexlH];
            Long[] lhsl = new Long[indexlH];
            for (int i = 0; i < indexhH; i++) {
                hhs[i] =(String)hH[i].get("hh");
                hhsl[i] =(Long)hH[i].get("cc");
            }
            for (int i = 0; i < indexlH; i++) {
                lhs[i] =(String)lH[i].get("hh");
                lhsl[i] =(Long)lH[i].get("cc");
            }
            chart1.put("hhs",hhs);
            chart1.put("lhs",lhs);
            chart1.put("hhsl",hhsl);
            chart1.put("lhsl",lhsl);
            params.put("chart1",chart1);
            if(indexhH<6 || lH.length<1){//正常来说不存在
                return message;
            }
            Map[] hHZd = new Map[6];//从最后一个取得到出现次数最多的
            hHZd[0] = hH[indexhH-1];
            hHZd[1] = hH[indexhH-2];
            hHZd[2] = hH[indexhH-3];
            hHZd[3] = hH[indexhH-4];
            hHZd[4] = hH[indexhH-5];
            hHZd[5] = hH[indexhH-6];
            Map[] hHZs = new Map[6];
            hHZs[0] = hH[0];
            hHZs[1] = hH[1];
            hHZs[2] = hH[2];
            hHZs[3] = hH[3];
            hHZs[4] = hH[4];
            hHZs[5] = hH[5];
            //遍历之后 数字从小到大排序
            for (int i = 0; i < hHZd.length; i++) {
                //外层循环，遍历次数
                for (int j = 0; j < hHZd.length - i - 1; j++) {
                    //内层循环，升序（如果前一个值比后一个值大，则交换）
                    //内层循环一次，获取一个最大值
                    if (Integer.valueOf((String)(hHZd[j].get("hh"))) >Integer.valueOf((String)(hHZd[j + 1].get("hh")))) {
                        Map temp = hHZd[j + 1];
                        hHZd[j + 1] = hHZd[j];
                        hHZd[j] = temp;
                    }
                }
            }
            for (int i = 0; i < hHZs.length; i++) {
                //外层循环，遍历次数
                for (int j = 0; j < hHZs.length - i - 1; j++) {
                    //内层循环，升序（如果前一个值比后一个值大，则交换）
                    //内层循环一次，获取一个最大值
                    if (Integer.valueOf((String)(hHZs[j].get("hh"))) >Integer.valueOf((String)(hHZs[j + 1].get("hh")))) {
                        Map temp = hHZs[j + 1];
                        hHZs[j + 1] = hHZs[j];
                        hHZs[j] = temp;
                    }
                }
            }
            List<Map> listResult = new ArrayList<>();

            Map<String,Object> maphZdlZd = new HashMap<>();
            maphZdlZd.put("lx","hdld");
            Map<String,Object> maphZslZs = new HashMap<>();
            maphZslZs.put("lx","hsls");
            Map<String,Object> maphZdlZs = new HashMap<>();
            maphZdlZs.put("lx","hdls");
            Map<String,Object> maphZslZd = new HashMap<>();
            maphZslZd.put("lx","hsld");
            //红号
            for (int i = 0; i <6 ; i++) {
                maphZdlZd.put("hh"+(i+1),hHZd[i].get("hh"));
                maphZdlZs.put("hh"+(i+1),hHZd[i].get("hh"));
            }
            for (int i = 0; i <6 ; i++) {
                maphZslZs.put("hh"+(i+1),hHZs[i].get("hh"));
                maphZslZd.put("hh"+(i+1),hHZs[i].get("hh"));
            }
            //栏号
            maphZdlZd.put("lh",lH[indexlH-1].get("hh"));
            maphZslZd.put("lh",lH[indexlH-1].get("hh"));
            maphZslZs.put("lh",lH[0].get("hh"));
            maphZdlZs.put("lh",lH[0].get("hh"));
            listResult.add(maphZdlZd);
            listResult.add(maphZslZs);
            listResult.add(maphZdlZs);
            listResult.add(maphZslZd);
            params.put("lszh",listResult);
            message.setData(params);
        } catch (Exception e){
            logger.error("错误信息：",e);
            message.setCode("F").setMessage("失败");
        }
        return message;
    }
    @PostMapping("/pcSsq")
    public ResponseEntity<Object> pcSsq(@RequestBody Map<String,Object> params){
        ResponseMessage message = new ResponseMessage();
        Map<String,Object>  result = new HashMap<>();
        if(pcjd>0 &&pcjd<100){
            message.setCode("F")
                    .setMessage("上一次爬取未结束")
                    .setData(result);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        listSsqVo.clear();
        String ksqi=(String)params.get("ksqi");
        String jsrq=(String)params.get("jsrq");
        int lastqs=(Integer) params.get("lastqs");
        try {
            result = pc(ksqi,jsrq,lastqs);
            message.setCode("T")
                    .setMessage("success")
                    .setData(result);
        } catch (Exception e){
            message.setCode("F").setMessage("失败:"+e.getMessage());

        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    public static void main(String[] args) {
        String ksqi="";
        String jsrq="";
        int lastqs=100;
        AllBalls allBalls = new AllBalls();
        allBalls.pc(ksqi,jsrq,lastqs);
//        Map<String,Object>  result =pc(ksqi,jsrq,lastqs);
    }
    public  Map<String, Object> pc(String ksqi, String jsrq,int lastqs) {
        pcjd =0;
        Map<String, Object>  result = new HashMap<>();
        int count=0;
        System.out.println("正在获取...");
        String baseUrlPrefix = "http://kaijiang.zhcw.com/zhcw/html/ssq/list_";
        String baseUrlSuffix = ".html";
        String homeUrl = "http://kaijiang.zhcw.com/zhcw/html/ssq/list_1.html";
        String pageCountContent = getHtmlString(homeUrl);
        int pageCount = getPageCount(pageCountContent);
        total = getTotal(pageCountContent);
        if(lastqs>0 && lastqs <total){
            total = lastqs;
        }
        System.out.println("pageCount：" + pageCount);
        if (pageCount > 0) {
            for (int i = 1; i <= pageCount; i++) {
                if(lastqs>0 && listSsqVo.size() >=lastqs){
                    System.out.println("目标期数："+lastqs+" 已采集期数："+listSsqVo.size());
                    break;
                }
                String url = baseUrlPrefix + i + baseUrlSuffix;
                String pageContent = getHtmlString(url);
                if (pageContent != null && !pageContent.equals("")) {
                    getOneTermContent(pageContent);
                } else {
                    System.out.println("第" + i + "页丢失");
                }
                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            if(lastqs>0 && listSsqVo.size() >=lastqs){
                listSsqVo=listSsqVo.subList(0,lastqs);
            }
            //入库
            int listSize=listSsqVo.size();
            int toIndex=50;
            Map map = new HashMap();     //用map存起来新的分组后数据
            int keyToken = 0;
            int dbjd=0;//入库进度
            for(int i = 0;i<listSsqVo.size();i+=toIndex){
                if(i+toIndex>listSize){        //作用为toIndex最后没有100条数据则剩余几条newList中就装几条
                    toIndex=listSize-i;
                }
                List<SsqVo> listSsqVoNew = listSsqVo.subList(i,i+toIndex);
                ssqMapper.deleteList(listSsqVoNew);
                ssqMapper.saveList(listSsqVoNew);
                dbjd = (i+toIndex)*100/listSsqVo.size();
                pcjd= 50 +dbjd/2+1;//入库前已经50%
                if(pcjd >=99){
                    pcjd =99;
                }
            }
        } else {
            System.out.println("结果页数为0");
        }
        result.put("count",listSsqVo.size());
        System.out.println("完成！");
        pcjd=100;
        return result;
    }

    /**
     * 获取总页数
     *
     * @param result
     */
    private static int getPageCount(String result) {
        String regexPageCount = "\\d+\">末页";
        Pattern pattern = Pattern.compile(regexPageCount);
        Matcher matcher = pattern.matcher(result);
        String[] splits = null;
        while (matcher.find()) {
            String content = matcher.group();
            splits = content.split("\"");
            break;
        }
        if (splits != null && splits.length == 2) {
            String countString = splits[0];
            if (countString != null && !countString.equals("")) {
                return Integer.parseInt(countString);
            }

        }
        return 0;
    }
    private static int getTotal(String result) {
        String regexTotal = "<strong>([\\S]+?[\\s]+?)</strong>条记录";
        Pattern pattern = Pattern.compile(regexTotal);
        Matcher matcher = pattern.matcher(result);
        String[] splits = null;
        while (matcher.find()) {
            String content = matcher.group(1);
            return Integer.parseInt(content.trim());
        }
        return 0;
    }

    /**
     * 获取网页源码
     *
     * @return
     */
    private static String getHtmlString(String targetUrl) {
        String content = null;

        HttpURLConnection connection = null;
        try {
            URL url = new URL(targetUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            connection.setRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 7.0; Windows 7)");
            connection
                    .setRequestProperty(
                            "Accept",
                            "image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/x-shockwave-flash, application/vnd.ms-powerpoint, application/vnd.ms-excel, application/msword, */*");
            connection.setRequestProperty("Accept-Language", "zh-cn");
            connection.setRequestProperty("UA-CPU", "x86");
            // 为什么没有deflate呢
            connection.setRequestProperty("Accept-Encoding", "gzip");
            connection.setRequestProperty("Content-type", "text/html");
            // keep-Alive，有什么用呢，你不是在访问网站，你是在采集。嘿嘿。减轻别人的压力，也是减轻自己。
            connection.setRequestProperty("Connection", "close");
            // 不要用cache，用了也没有什么用，因为我们不会经常对一个链接频繁访问。（针对程序）
            connection.setUseCaches(false);
            connection.setConnectTimeout(6 * 1000);
            connection.setReadTimeout(6 * 1000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Charset", "utf-8");

            connection.connect();

            if (200 == connection.getResponseCode()) {
                InputStream inputStream = null;
                if (connection.getContentEncoding() != null
                        && !connection.getContentEncoding().equals("")) {
                    String encode = connection.getContentEncoding()
                            .toLowerCase();
                    if (encode != null && !encode.equals("")
                            && encode.indexOf("gzip") >= 0) {
                        inputStream = new GZIPInputStream(
                                connection.getInputStream());
                    }
                }

                if (null == inputStream) {
                    inputStream = connection.getInputStream();
                }

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream, "utf-8"));
                StringBuilder builder = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    builder.append(line).append("\n");
                }
                content = builder.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return content;
    }

    private static void getOneTermContent(String pageContent) {
//        String regex = "<td align=\"center\" style=\"padding-left:10px;\">[\\s\\S]+?</em></td>";
        String regex = "<td align=\"center\">([\\S]+?)</td>[\\s]+?<td align=\"center\">([\\S]+?)</td>[\\s]+?<td align=\"center\" style=\"padding-left:10px;\">([\\s\\S]+?</em></td>)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pageContent);
        while (matcher.find()) {
//            System.out.println("开奖日期:" + matcher.group(1));
//            System.out.println("期号:" + matcher.group(2));
            String oneTermContent = matcher.group(3);
            getOneTermNumbers(matcher.group(1),matcher.group(2),oneTermContent);
        }
    }

    private static void getOneTermNumbers(String kjri,String qh,String oneTermContent) {

        String regex = ">\\d+<";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(oneTermContent);
        StringBuffer ballNumber_=new StringBuffer();;
        while (matcher.find()) {
            String content = matcher.group();
            String ballNumber = content.substring(1, content.length() - 1);
            ballNumber_.append(ballNumber).append(" ");
        }
        String [] ballNumber_s= ballNumber_.toString().split(" ");
        SsqVo ssqVo = new SsqVo(kjri,qh,ballNumber_s[0],ballNumber_s[1],ballNumber_s[2],ballNumber_s[3],ballNumber_s[4],ballNumber_s[5],ballNumber_s[6]);
        System.out.println("开奖日期:"+kjri +" 期号:"+qh+" :中奖号码:" +ballNumber_.toString());
        listSsqVo.add(ssqVo);
        pcjd=listSsqVo.size()*100/total/2;
        if(pcjd >=50){
            pcjd=50;
        }
    }
}
