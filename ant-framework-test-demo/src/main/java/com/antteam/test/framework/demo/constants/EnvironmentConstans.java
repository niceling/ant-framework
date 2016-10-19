package com.antteam.test.framework.demo.constants;


/**
 * ClassName: EnvironmentConstans 
 * @Description: TODO
 * @author Mirai
 * @date 2016年10月17日
 */
public class EnvironmentConstans {
    
    public static final String CHANNEL="wechat";
    public static final String CLIENT_ID="PHONECLIENT";
    public static final String CLIENT_SECRET="!sdfioaflksdxcijvn";
    public static final String VERSION="v1.0";
    
    
    public static final String LOCAL="local";
    public static final String DEV="dev";
    public static final String TEST="test";
    public static final String EMU="emu";
    public static final String PRODUCT="product";
    
    public static final String PROJECT_NAME="home-give-restful/api";
    
    public static String GetHost(String environment){
        String host="http://restful.hg.dev.foodmall.com/";
        switch (environment) {
            case LOCAL:
                host="http://homegive.zml.foodmall.com:8080/";
                break;
            case DEV:
                host="http://restful.hg.dev.foodmall.com/";
                break;
            case TEST:
                host="http://restful.hg.test.foodmall.com/";
                break;
            case EMU:
                host="http://restful.hg.emu.foodmall.com/";
                break;
            case PRODUCT:
                host="http://restful.hg.foodmall.com/";
                break;
        }
        return host;
        
    }
    
  
}
