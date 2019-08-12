package com.telecomyt.item.bus;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author zhoupengbing
 * @packageName com.telecomyt.forum.conf
 * @email zhoupengbing@telecomyt.com.cn
 * @description 总线推送配置信息
 * @createTime 2019年07月17日 15:35:00
 * @Version v1.0
 */
@Component
@ConfigurationProperties(prefix = "dxyt.bus.push")
public class DxytBusPushConfig implements Serializable {

    private static final long serialVersionUID = -7848670723277171870L;

    /**
     *统一推送的地址
     */
    private String BASE_API_URL;

    /**
     * 报文来源ip地址
     */
    private String BWLYIPDZ;

    /**
     * 报文来源端口
     */
    private String BWLYDKH;

    /**
     * 服务请求者_注册信息
     */
    private String FWQQZ_ZCXX;

    /**
     * 服务标识
     */
    private String FWBS;

    /**
     * 方法标识
     */
    private String FFBS;

    /**
     * 方法名称-根据用户名来进行推送
     */
    private String FFMC;

    /**
     * 应用包名
     */
    private String PACKAGE_NAME;

    /**
     * 服务请求审计事件类型
     */
    private String FWQQ_SJSJLX;

    /**
     * 跳转页面的url
     */
    private String URL;

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getBASE_API_URL() {
        return BASE_API_URL;
    }

    public void setBASE_API_URL(String BASE_API_URL) {
        this.BASE_API_URL = BASE_API_URL;
    }

    public String getBWLYIPDZ() {
        return BWLYIPDZ;
    }

    public void setBWLYIPDZ(String BWLYIPDZ) {
        this.BWLYIPDZ = BWLYIPDZ;
    }

    public String getBWLYDKH() {
        return BWLYDKH;
    }

    public void setBWLYDKH(String BWLYDKH) {
        this.BWLYDKH = BWLYDKH;
    }

    public String getFWQQZ_ZCXX() {
        return FWQQZ_ZCXX;
    }

    public void setFWQQZ_ZCXX(String FWQQZ_ZCXX) {
        this.FWQQZ_ZCXX = FWQQZ_ZCXX;
    }

    public String getFWBS() {
        return FWBS;
    }

    public void setFWBS(String FWBS) {
        this.FWBS = FWBS;
    }

    public String getFFBS() {
        return FFBS;
    }

    public void setFFBS(String FFBS) {
        this.FFBS = FFBS;
    }

    public String getFFMC() {
        return FFMC;
    }

    public void setFFMC(String FFMC) {
        this.FFMC = FFMC;
    }

    public String getFWQQ_SJSJLX() {
        return FWQQ_SJSJLX;
    }

    public void setFWQQ_SJSJLX(String FWQQ_SJSJLX) {
        this.FWQQ_SJSJLX = FWQQ_SJSJLX;
    }

    public String getPACKAGE_NAME() {
        return PACKAGE_NAME;
    }

    public void setPACKAGE_NAME(String PACKAGE_NAME) {
        this.PACKAGE_NAME = PACKAGE_NAME;
    }
}
