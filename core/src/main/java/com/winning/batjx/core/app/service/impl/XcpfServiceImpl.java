package com.winning.batjx.core.app.service.impl;

import com.winning.batjx.core.app.mapper.QyjxMapper;
import com.winning.batjx.core.app.mapper.XcpfMapper;
import com.winning.batjx.core.app.service.XcpfService;
import com.winning.batjx.core.khgl.khwj.domain.PicDO;
import com.winning.batjx.core.khgl.khwj.domain.PicDTO;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * @Description:
 * @Param:
 * @return:
 * @Author: huchengwei
 * @Date: 2020/4/21
 */
@Service
public class XcpfServiceImpl implements XcpfService {
    /**
     * roleMapper object
     */
    @Resource
    private XcpfMapper xcpfMapper;

    @Resource
    private QyjxMapper qyjxMapper;

    @Value("${file.upload_root_dir}")
    private String fileDir;
    /**
     * 查询方案列表
     *
     * @return
     */
    @Override
    public List<Map> queryKhfa(Map<String,Object> params) {
        return xcpfMapper.queryKhfa(params);
    }

    @Override
    public List<Map> queryKhdx(Map<String,Object> params) {
        return xcpfMapper.queryKhdx(params);
    }

    @Override
    public List<Map> queryKhzbZtList(Map<String,Object> params) {
        return xcpfMapper.queryKhzbZtList(params);
    }

    @Override
    public List<Map> queryKhzbList(Map<String,Object> params) {
        return xcpfMapper.queryKhzbList(params);
    }

    @Override
    public List<Map> queryKhzbPics(Map<String,Object> params) {
        return xcpfMapper.queryKhzbPics(params);
    }

    @Override
    public boolean uploadPics(PicDTO picDto) {
        try {
            // 更新数据填报的值
            if(picDto.getPfFlag()){
                Map<String,Object> params = new HashMap<>();
                params.put("id",picDto.getTbid());
                params.put("zbz",picDto.getZbz());
                params.put("bz",picDto.getPfms());
                xcpfMapper.updateSjtb(params);
            }
            xcpfMapper.updateWjgl(picDto.getTbid());//先删除文件
            for (PicDO wjgl : picDto.getUploadFilesFls()) {
                String imgFilePath =fileDir+"/zbsjtbFiles";
                File uploadPathFile = new File(imgFilePath);
                //创建父类文件
                if(!uploadPathFile.exists() && !uploadPathFile.isDirectory()){
                    uploadPathFile.mkdirs();
                }
                String pname = String.valueOf(new Date().getTime());
                File imgeFile = new File(imgFilePath,pname+".jpg");
                if(!imgeFile.exists()){
                    imgeFile.createNewFile();
                }
                //对base64进行解码
                byte[] result = decodeBase64(wjgl.getTpzm());
                //使用Apache提供的工具类将图片写到指定路径下
                FileUtils.writeByteArrayToFile(imgeFile,result);
                wjgl.setMkid(Integer.parseInt(picDto.getTbid()));
                wjgl.setYswjmc(pname+".jpg");
                wjgl.setCcwjmc(pname+".jpg");
                wjgl.setCclj(imgFilePath + File.separator + wjgl.getCcwjmc());
                wjgl.setScr(picDto.getScr());
                wjgl.setJgbm(picDto.getJgbm());
//                wjgl.setPath(imgeFile.toString());
                xcpfMapper.insertWjgl(wjgl);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
    private  byte[] decodeBase64(String input) {
        return org.apache.tomcat.util.codec.binary.Base64.decodeBase64(input.getBytes());
    }

}
