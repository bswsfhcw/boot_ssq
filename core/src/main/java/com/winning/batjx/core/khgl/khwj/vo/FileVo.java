package com.winning.batjx.core.khgl.khwj.vo;

/**
 * @author yxf@winning.com.cn
 * @date 2021/4/1
 * @time 10:32
 * @description
 */

public class FileVo {

    private String name;

    private String url;

    private Integer mkid;

    private String mkbm;

    private String ljname; // 逻辑名称

    private String path; // 存储路径

    public Integer getMkid() {
        return mkid;
    }

    public void setMkid(Integer mkid) {
        this.mkid = mkid;
    }

    public String getMkbm() {
        return mkbm;
    }

    public void setMkbm(String mkbm) {
        this.mkbm = mkbm;
    }

    public String getLjname() {
        return ljname;
    }

    public void setLjname(String ljname) {
        this.ljname = ljname;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
