package com.winning.batjx.core.khgl.khwj.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.jdbc.StringUtils;
import com.winning.batjx.core.common.util.*;
import com.winning.batjx.core.khgl.khwj.domain.*;
import com.winning.batjx.core.khgl.khwj.vo.*;
import com.winning.batjx.core.khgl.khwj.mapper.WjkhMapper;
import com.winning.batjx.core.khgl.khwj.service.WjkhService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author yxf@winning.com.cn
 * @date 2021/3/9
 * @time 10:48
 * @description
 */
@Service
public class WjkhServiceImpl implements WjkhService {

    @Resource
    private WjkhMapper wjkhMapper;

    @Value("${file.upload_root_dir}")
    private String fileDir;

    @Override
    public PageResult getPageList(PageRequest pageRequest, Map map) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        // 开启分页插件,放在查询语句上面 帮助生成分页语句
        PageHelper.startPage(pageNum, pageSize);
        //注意分页工具只对PageHelper下的第一个select语句生效
        List<MbzbdzVo> list = wjkhMapper.queryPageList(map);
        return PageUtils.getPageResult(new PageInfo<MbzbdzVo>(list));
    }

    @Override
    public List<KhfaDo> getKhfas(String khnf, String khpldm) {
        Map para = new HashMap();
        para.put("khnf",khnf);
        para.put("khpldm",khpldm);
        return wjkhMapper.queryKhfa(para);
    }

    @Override
    public List<Map<String, String>> getKhfaKhqj(String faid) {
        Map para = new HashMap();
        para.put("faid",faid);
        return wjkhMapper.queryKhfaKhqj(para);
    }


    @Override
    public List<Map<String, String>> getKhfaKhdx(String faid,String khqjdm) {
        Map para = new HashMap();
        para.put("faid",faid);
        para.put("khqjdm",khqjdm);
        return wjkhMapper.queryKhfaKhdx(para);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAnswer(WjmbVo wjmbVo, Map para) {
        // qyjx_wjkh_jg
        WjjgDo wjjgDoPara = new WjjgDo();
        wjjgDoPara.setFaid(Integer.valueOf((String)para.get("faid")));
        wjjgDoPara.setKhpl((String) para.get("khpl"));
        wjjgDoPara.setKhnf((String) para.get("khnf"));
        wjjgDoPara.setKhqj((String) para.get("khqj"));
        wjjgDoPara.setKhdxbm((String) para.get("khdxbm"));
        wjjgDoPara.setZbbm((String) para.get("zbbm"));
        wjjgDoPara.setCjr("测试人员");
        wjjgDoPara.setBz("未填报");

        // 判断是否首次保存
        WjjgDo wjjgVo = wjkhMapper.queryWjjg(wjjgDoPara);

        if(wjjgVo == null){ // insert
            // insert qyjx_wjkh_jg
            wjkhMapper.insertWjjg(wjjgDoPara);
        }else{// update
            // 先删除字表相关数据  qyjx_khwj_mbjg qyjx_khwj_dtjg qyjx_khwj_dajgxx
            wjkhMapper.deleteDtjgxx(wjjgVo);
            wjkhMapper.updateWjjg(wjjgDoPara);
        }

        // insert qyjx_wjkh_mbjg
        MbjgDo mbjgDo = new MbjgDo();
        mbjgDo.setWjjgid(wjjgVo == null ? wjjgDoPara.getId() : wjjgVo.getId());
        mbjgDo.setWjmbid(wjmbVo.getMbid());
        mbjgDo.setWjbt(wjmbVo.getMbmc());
        mbjgDo.setWjfbt(wjmbVo.getMbfbt());
        mbjgDo.setWjxsfs(wjmbVo.getMbxsfs());
        wjkhMapper.insertMbjg(mbjgDo);
        // 循环插入题目 insert qyjx_wjkh_dtjg 答题结果
        List<MbflVo> fls = wjmbVo.getFls();
        if(fls == null || fls.size() == 0)return;
        // 公式题目
        List<MbtmVo> gstms = new ArrayList<MbtmVo>();
        Map<String,BigDecimal> khdfCacheMap = new HashMap<String,BigDecimal>();
        Map<String,BigDecimal> tkttmdaCacheMap = new HashMap<String,BigDecimal>();
        Map<String,BigDecimal> tkttmdfCacheMap = new HashMap<String,BigDecimal>();
        Map<String,BigDecimal> tmdaCacheMap = new HashMap<String,BigDecimal>(); // 用于公式解析
        BigDecimal result = BigDecimal.ZERO; // 问卷总得分
        // 模板下分类循环
        for (MbflVo fl : fls) {
            List<MbtmVo> tms = fl.getTms();
            if(tms == null || tms.size() == 0)continue;
            // 分类下题目循环
            for (MbtmVo tm : tms) {
                DtjgDo dtjgDo = new DtjgDo();
                dtjgDo.setMbjgid(mbjgDo.getId());
                dtjgDo.setFlid(fl.getFlid());
                dtjgDo.setFlmc(fl.getFlmc());
                dtjgDo.setFlsx(fl.getFlsx());
                dtjgDo.setTmlx(tm.getTmlx());
                dtjgDo.setTmbm(tm.getTmbm());
                dtjgDo.setTmmc(tm.getTmbt());
                dtjgDo.setTmfbt(tm.getTmfbt());
                dtjgDo.setTmsx(tm.getTmsx());
                dtjgDo.setTmbz(tm.getTmbz());
                // 题目类型(1单选2多选3填空)  题目答案来源(0无1默认填写2关联计算)
                if("3".equals(tm.getTmlx()) && ("0".equals(tm.getTmdaly()) || "1".equals(tm.getTmdaly()))){
                    dtjgDo.setTmda(tm.getTmda());
                    if("1".equals(tm.getTktsfwdf())){
                        dtjgDo.setTmdf(StringUtils.isNullOrEmpty(tm.getTmda()) ? BigDecimal.ZERO : new BigDecimal(tm.getTmda()));
                        tmdaCacheMap.put(tm.getTmbm(),new BigDecimal(dtjgDo.getTmda())); // 将填空题目中直接填写的答案存进map 作为因子使用
                        tkttmdaCacheMap.put(tm.getTmbm(),new BigDecimal(dtjgDo.getTmda()));
//                        tkttmdfCacheMap.put(tm.getDtjgid()+"",dtjgDo.getTmdf());
                    }else{
                        dtjgDo.setTmdf(BigDecimal.ZERO);
                    }
//                    tkttmdaCacheMap.put(tm.getTmbm()+"",new BigDecimal(tm.getTmda()));
                }else{
                    dtjgDo.setTmda("-");
                    dtjgDo.setTmdf(BigDecimal.ZERO); // 先设置考核得分是0  最后再update
                }
                // insert qyjx_khwj_dtjg
                wjkhMapper.insertDtjg(dtjgDo);
                tm.setDtjgid(dtjgDo.getId());
                result = result.add(dtjgDo.getTmdf());
                // 题目答案来源(0无1默认填写2关联计算)
                if("3".equals(tm.getTmlx()) && "2".equals(tm.getTmdaly())){
                    gstms.add(tm);
                    continue;
                }else if("3".equals(tm.getTmlx())){
                    continue;
                }

                List<TmxxVo> tmxxs = tm.getTmxxs();
                if(tmxxs == null || tmxxs.size() == 0){continue;}

                // 如果是填空题 且 答案来源是综合计算的题目 另外计算 存入gstms
                BigDecimal khdf = BigDecimal.ZERO; // 考核得分
                for (TmxxVo tmxx : tmxxs) {
                    DtjgxxDo jgxx = new DtjgxxDo();
                    jgxx.setDtjgid(dtjgDo.getId());
                    jgxx.setXxnr(tmxx.getXxnr());
                    Integer singleID = tm.getSelectId();
                    jgxx.setSfgx("0");
                    // 单选
                    if("1".equals(tm.getTmlx()) && tmxx.getXxid().equals(singleID)){
                        jgxx.setSfgx("1");
                        khdf = khdf.add(tmxx.getXxfz() == null ? BigDecimal.ZERO : tmxx.getXxfz());// 题目综合分
                        result = result.add(tmxx.getXxfz() == null ? BigDecimal.ZERO : tmxx.getXxfz()); // 问卷总得分
                    }else if("2".equals(tm.getTmlx()) && tm.getSelectIds() != null && tm.getSelectIds().length > 0){
                        if(Arrays.asList(tm.getSelectIds()).contains(tmxx.getXxid())){
                            jgxx.setSfgx("1");
                            khdf = khdf.add(tmxx.getXxfz() == null ? BigDecimal.ZERO : tmxx.getXxfz());// 题目综合分
                            result = result.add(tmxx.getXxfz() == null ? BigDecimal.ZERO : tmxx.getXxfz()); // 问卷总得分
                        }
                    }
                    wjkhMapper.insertDtjgxx(jgxx);
                }
                // khdf是单选或者多选题目的分值
                khdfCacheMap.put(dtjgDo.getId()+"",khdf); // 将算出的考核得分（选中的分值相加）存入缓存
            }
        }

        Map<String,BigDecimal> gsbmTmdaCacheMap = new HashMap<String,BigDecimal>();
        Map<String,BigDecimal> gsbmTmdfCacheMap = new HashMap<String,BigDecimal>();
        // 存在有填空题 且 填空题的答案来源不是手工是关联计算即公式
        if(gstms != null && gstms.size() > 0){
            for (MbtmVo gstm : gstms) {
                TkxxVo tkxxVo = gstm.getTkxxs().get(0); // 填空题的明细只有1个
                String gsbm = tkxxVo.getTmdagsbm();
                if(!StringUtils.isNullOrEmpty(gsbm)){
                    // 提取公式编码中的题目编码 并 将编码替换成对应因子的答案
                    List<String> tmbms = RegexHtmlUtil.getElementsGsbm(gsbm);
                    if(tmbms != null && tmbms.size() > 0){
                        for (String tmbm : tmbms) {
                            gsbm = gsbm.replaceAll(tmbm,tmdaCacheMap.get(tmbm) == null ? "0" : String.valueOf(tmdaCacheMap.get(tmbm)));
                        }
                    }
                    String tmda = JexlUtils.calculate(gsbm.replaceAll("\\{","").replaceAll("\\}",""));
                    tkttmdaCacheMap.put(gstm.getTmbm(),new BigDecimal(tmda));
                    gsbmTmdaCacheMap.put(gstm.getDtjgid()+"",new BigDecimal(tmda));
                    if("1".equals(tkxxVo.getTkzsfwdf())){
                        tkttmdfCacheMap.put(gstm.getDtjgid()+"",new BigDecimal(tmda));
                        continue;
                    }
                    // 以上是算答案 以下是算得分
                    List<TktgsxxVo> tktgsxxVo = tkxxVo.getGsxxs();
                    if(tktgsxxVo != null && tktgsxxVo.size() > 0){
                        if("1".equals(tkxxVo.getTmsffffl())){ // 自定义公式
                            String zdygsbm = tktgsxxVo.get(0).getSfgsbm();
                            String zdygsmc = tktgsxxVo.get(0).getSfgsmc();
                            // 提取公式编码因子 算值
                            if(!StringUtils.isNullOrEmpty(zdygsbm)){
                                List<String> tkttmbms = RegexHtmlUtil.getElementsGsbm(gsbm);
                                if(tkttmbms != null && tkttmbms.size() > 0){
                                    for (String tmbm : tkttmbms) {
                                        zdygsbm = zdygsbm.replaceAll(tmbm,tkttmdaCacheMap.get(tmbm) == null ? "0" : String.valueOf(tkttmdaCacheMap.get(tmbm)));
                                        zdygsbm = zdygsbm.replaceAll(tmbm,tmdaCacheMap.get(tmbm) == null ? "0" : String.valueOf(tmdaCacheMap.get(tmbm)));
                                    }
                                }
                            }
                            String tktdf = JexlUtils.calculate(zdygsbm.replaceAll("\\{","").replaceAll("\\}",""));
                            tkttmdfCacheMap.put(gstm.getDtjgid()+"",new BigDecimal(tktdf));
                        }else if("2".equals(tkxxVo.getTmsffffl())){ // 分段
                            for (TktgsxxVo vo : tktgsxxVo) {
                                BigDecimal beginV = vo.getFdksz() == null ? BigDecimal.ZERO : vo.getFdksz();
                                BigDecimal endV = vo.getFdjsz() == null ? BigDecimal.ZERO : vo.getFdjsz();
                                // 满足分段区间
                                if(new BigDecimal(tmda).compareTo(beginV) >= 0 && new BigDecimal(tmda).compareTo(endV) < 0){
                                    tkttmdfCacheMap.put(gstm.getDtjgid()+"",vo.getDfz());
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        // 题目考核得分的update
        if(khdfCacheMap != null && khdfCacheMap.size() > 0){
            for (Map.Entry<String, BigDecimal> integerBigDecimalEntry : khdfCacheMap.entrySet()) {
                String dtjgid = integerBigDecimalEntry.getKey();
                BigDecimal df = integerBigDecimalEntry.getValue();
                Map temp = new HashMap();
                temp.put("dtjgid",dtjgid);
                temp.put("df",df);
                temp.put("tmda",df);
                wjkhMapper.updateTmdf(temp);
            }
        }

        // 填空题题目考核得分的update 填空题这里只能算出为题目答案
        if(gsbmTmdaCacheMap != null && tkttmdaCacheMap.size() > 0){
            for (Map.Entry<String, BigDecimal> integerBigDecimalEntry : gsbmTmdaCacheMap.entrySet()) {
                String dtjgid = integerBigDecimalEntry.getKey();
                BigDecimal tmda = integerBigDecimalEntry.getValue();
                BigDecimal tmdf = tkttmdfCacheMap.get(dtjgid);
                Map temp = new HashMap();
                temp.put("dtjgid",dtjgid);
                temp.put("tmda",tmda);
                temp.put("df",tmdf == null ? BigDecimal.ZERO : tmdf);
                result = result.add(tmdf == null ? BigDecimal.ZERO : tmdf);
                wjkhMapper.updateTmdf(temp);
            }
        }

        String czlx = (String) para.get("czlx"); // 0保存 1 提交
        if("1".equals(czlx)){
            // 维护总得分 qyjx_khwj_jg
            WjjgDo wjjgDo = new WjjgDo();
            wjjgDo.setKhdf(result);
            wjjgDo.setFaid(Integer.valueOf((String) para.get("faid")));
            wjjgDo.setKhqj((String)para.get("khqj"));
            wjjgDo.setKhnf((String)para.get("khnf"));
            wjjgDo.setKhdxbm((String)para.get("khdxbm"));
            wjjgDo.setZbbm((String)para.get("zbbm"));
            wjjgDo.setBz("已填报");
            wjjgDo.setGxr("测试人员");
            wjkhMapper.updateWjjg(wjjgDo);

            // 将以上算出来的指标结果维护进qyjx_sjtb表
            wjkhMapper.updateSjtbKhdf(wjjgDo);

        }
    }

    @Override
    public WjmbVo getResult(Map para) {
        String faid = (String) para.get("faid");
        String khpl = (String) para.get("khpl");
        String khqj = (String) para.get("khqj");
        String khdxbm = (String) para.get("khdxbm");
        String khnf = (String) para.get("khnf");
        String zbbm = (String) para.get("zbbm");

        String bz = faid + "-" + khqj + "-" + khdxbm + "-" + zbbm;
        // 问卷模板
        WjmbVo wjmbVo = wjkhMapper.getWjmb(para);
        if(wjmbVo == null){return null;}
        Map fileMap = new HashMap();
        fileMap.put("mkbm","qyjx_khwj_mb");
        fileMap.put("mkid",wjmbVo.getMbid());
        fileMap.put("bz",bz);
        wjmbVo.setFiles(wjkhMapper.getFlFile(fileMap));

        // 首先获取分类
        List<MbflVo> fs = wjkhMapper.getMbfl(para);
        if(fs != null && fs.size() > 0){
            // 其次获取分类下的题目
            fileMap.put("mkbm","qyjx_khwj_mbfl");
            for (MbflVo fl : fs) {
                fileMap.put("mkid",fl.getFlid());
                // 获取分类的附件
                fl.setFiles(wjkhMapper.getFlFile(fileMap));

                List<MbtmVo> mbtms = wjkhMapper.getMbtm(fl);
                if(mbtms != null && mbtms.size() > 0){
                    for (MbtmVo mbtm : mbtms) {
                        Map tmMap = new HashMap();
                        tmMap.put("flid",fl.getFlid());
                        tmMap.put("tmbm",mbtm.getTmbm());
                        tmMap.put("mbid",wjmbVo.getMbid());
                        tmMap.put("tmid",mbtm.getTmid());
                        tmMap.put("khnf",khnf);
                        tmMap.put("faid",faid);
                        tmMap.put("khpl",khpl);
                        tmMap.put("khqj",khqj);
                        tmMap.put("khdxbm",khdxbm);
                        tmMap.put("zbbm",zbbm);
                        // 选择题结果
                        if("1".equals(mbtm.getTmlx()) || "2".equals(mbtm.getTmlx())){
                            List<TmxxVo> tmxxs = wjkhMapper.getTmxx(tmMap);
                            if(tmxxs == null || tmxxs.size() == 0)continue;
                            // 获取题目选项 1 单选题 2多选题
                            Integer selectid = 0;
                            List<Integer> selects = new ArrayList<Integer>();
                            for (TmxxVo tmxx : tmxxs) {
                                if("1".equals(tmxx.getSfgx())){
                                    mbtm.setTmbz(tmxx.getTmbz());
                                    if("1".equals(mbtm.getTmlx())){
                                        selectid = tmxx.getXxid();
                                    }else if("2".equals(mbtm.getTmlx())){
                                        selects.add(tmxx.getXxid());
                                    }
                                }
                            }
                            // 选中项设置
                            if("1".equals(mbtm.getTmlx())){
                                mbtm.setSelectId(selectid);
                                mbtm.setSelectIds(new Integer[]{});
                            }else if("2".equals(mbtm.getTmlx())){
                                mbtm.setSelectId(0);
                                if(selects == null || selects.size() == 0){
                                    mbtm.setSelectIds(new Integer[]{});
                                }else{
                                    mbtm.setSelectIds(selects.toArray(new Integer[selects.size()]));
                                }
                            }
                            mbtm.setTmxxs(tmxxs);
                        }else if("3".equals(mbtm.getTmlx())){
                            // 获取填空题
                            List<TkxxVo> tkxxs = wjkhMapper.getTkxx(tmMap);
                            if(tkxxs == null || tkxxs.size() == 0)continue;
                            mbtm.setTmbz(tkxxs.get(0).getTmbz());
                            mbtm.setTmda(tkxxs.get(0).getTmda());
                            mbtm.setTmdaly(tkxxs.get(0).getTmdaly());
                            mbtm.setTktsfwdf(tkxxs.get(0).getTkzsfwdf());
                            // 获取综合计算填空题的公式信息 1自定义公式2固定分段)
                            if((!"1".equals(tkxxs.get(0).getTkzsfwdf())) && !StringUtils.isNullOrEmpty(tkxxs.get(0).getTmsffffl())){
                                tkxxs.get(0).setGsxxs(wjkhMapper.getTktgsxx(tkxxs.get(0)));
                            }
                            mbtm.setTkxxs(tkxxs);
                        }
                    }
                }
                fl.setTms(mbtms);
            }
        }
        wjmbVo.setFls(fs);
        return wjmbVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertFile(MultipartFile fjfile, Map para) {
        String refileName = fjfile.getOriginalFilename();
        String suffix = RegexHtmlUtil.lastName(refileName);
        // 生成文件名称
        String uuid = UUID.randomUUID().toString();
        String filename = uuid + suffix;
        if(StringUtils.isNullOrEmpty(fileDir)){
            fileDir = "d://wjkh";
        }
        File filepath = new File(fileDir);
        if (!filepath.exists()) {
            filepath.mkdirs();
        }
        File imgFile = new File(  fileDir + "\\" + filename);
        try{
            fjfile.transferTo(imgFile);
        }catch (Exception e){
            System.out.println("上传文件报错！");
        }

        // 构建qyjx_wjgl bz字段 区别不同方案不同期间 不同 考核对象 不同指标下的分类
        String bz = (String) para.get("faid") + "-" + (String) para.get("khqj") + "-" + (String) para.get("khdxbm") + "-" + (String) para.get("zbbm");
        para.put("bz",bz);
        if(para.get("flid") != null){
            para.put("mkid",para.get("flid"));
            para.put("mkbm","qyjx_khwj_mbfl");
        }else{
            para.put("mkid",para.get("mbid"));
            para.put("mkbm","qyjx_khwj_mb");
        }
        para.put("yswjmc",refileName);
        para.put("ccwjmc",filename);
        para.put("wjlx","1");
        para.put("cclj",imgFile.getAbsolutePath());
        para.put("scr","测试人");
        para.put("jgbm","wjkhjgbm");
        wjkhMapper.removeFile(para);
        wjkhMapper.insertFile(para);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removefile(Map para) {
        String bz = (String) para.get("faid") + "-" + (String) para.get("khqj") + "-" + (String) para.get("khdxbm") + "-" + (String) para.get("zbbm");
        para.put("bz",bz);
        wjkhMapper.removeFile(para);
    }


}
