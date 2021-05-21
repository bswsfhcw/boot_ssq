package com.winning.batjx.core.app.controller;

import com.winning.batjx.core.app.service.QyjxService;
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
import java.util.Map;

//import javax.imageio.ImageIO;
//import com.google.code.kaptcha.Producer;
//import com.google.code.kaptcha.impl.DefaultKaptcha;
//import com.google.code.kaptcha.util.Config;
//import com.winning.batjx.core.common.DesLoginUtil;
//import com.winning.batjx.core.common.base.JsonResult;
//import com.winning.batjx.core.token.Jwt;
//import LoginInfo;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.ObjectError;
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.validation.Valid;
//import java.awt.image.BufferedImage;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Properties;

/**
 * @author zhe
 */
@RestController
@Api(value = "BATHIM API", tags = "D.qyjx", description = "区域绩效")
@RequestMapping("/qyjx")
public class QyjxController {

//    public static String randomcode = "";
    @Resource
    private QyjxService qyjxService;


    @ApiOperation(value = "获取登录人员信息", notes = "区域绩效")
    @PostMapping("/queryLoginUser")
    public ResponseEntity<Object> queryLoginUser(@RequestBody Map<String,Object> params)
    {
        ResponseMessage message = new ResponseMessage();
        message.setCode("T")
                .setMessage("success")
                .setData(qyjxService.queryLoginUser(params));
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
//    @ApiOperation(value = "登录", notes = "登录")
//    @PostMapping("/login")
//    public ResponseEntity<Object> login(@RequestBody(required = false) @Valid LoginInfo loginInfo, BindingResult result,
//                                        HttpServletRequest request, HttpServletResponse response) throws Exception
//    {
//
//        String code = "T";//默认成功
//        Map<String,Object> datas = new HashMap<>();
//        DesLoginUtil desLoginUtil = new DesLoginUtil();
//        String account = loginInfo.getUsername();
//        String password =loginInfo.getPassword();
////        account = desLoginUtil.strDec(account,"1","2","3");//用户名加密文翻译成明文
////        password = desLoginUtil.strDec(password,"1","2","3");//用户名加密文翻译成明文
//        String randomcode = loginInfo.getRandomcode();
//        String validateCode = QyjxController.randomcode;
//        boolean flag = true;
//        StringBuffer sb = new StringBuffer();
//        JsonResult jsonResult =  new JsonResult();
//        if(result.hasErrors()){
//            code="F";
//            for (ObjectError error : result.getAllErrors()) {
//                flag = false;
//                sb.append(error.getDefaultMessage()+"<br>");
//            }
//        }else{
////            if(!randomcode.equals(validateCode)){
////                code="F";
////                sb.append("验证码错误！");
////                jsonResult.setMsg(sb.toString());
////                jsonResult.setSuccess(flag);
////            }else {
//                //验证登录
//                loginInfo.setUsername(account);
//                loginInfo.setPassword(password);
//                LoginInfo loginOn = qyjxService.verifyLogin(loginInfo);
//                if(loginOn == null){
//                    code="F";
//                    sb.append("用户名或密码错误！");
//                }else{
//                    loginInfo.setPassword("");
//                    loginInfo.setRyxm(loginOn.getRyxm());
//                    loginInfo.setZclsh(loginOn.getZclsh());
//                    sb.append("登录成功！");
//                    //创建token
//                    datas.put("token",createToken(loginOn));
//                }
//            }
////        }
//        //更新验证码
//        Producer captchaProducer = initRandomProducer();
//        String capText = captchaProducer.createText();
//        QyjxController.randomcode = capText;
//        datas.put("loginInfo",loginInfo);
//
//        ResponseMessage message = new ResponseMessage();;
//        message.setCode(code)
//                .setMessage(sb.toString())
//                .setData(datas);
//        return new ResponseEntity<>(message, HttpStatus.OK);
//    }
//    @RequestMapping(value = "/kaptcha.jpg")
//    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
////        Properties properties = new Properties();
////        properties.setProperty("kaptcha.image.width","100");
////        properties.setProperty("kaptcha.image.height","50");
////        properties.setProperty("kaptcha.noise.impl","com.google.code.kaptcha.impl.NoNoise");
////        properties.setProperty("kaptcha.textproducer.char.string","0123456789abcdefghijklmnopqrstuvwxyz");
////        properties.setProperty("kaptcha.textproducer.char.length","4");
////        Config config = new Config(properties);
////        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
////        defaultKaptcha.setConfig(config);
//        Producer captchaProducer = initRandomProducer();
//
//        // Set to expire far in the past.
//        response.setDateHeader("Expires", 0);
//        // Set standard HTTP/1.1 no-cache headers.
//        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
//        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
//        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
//        // Set standard HTTP/1.0 no-cache header.
//        response.setHeader("Pragma", "no-cache");
//
//        // return a jpeg
//        response.setContentType("image/jpeg");
//
//        // create the text for the image
//        String capText = captchaProducer.createText();
//
//        // store the text in the session
//        this.randomcode = capText;
//
//        // create the image with the text
//        BufferedImage bi = captchaProducer.createImage(capText);
//
//        ServletOutputStream out = response.getOutputStream();
//
//        // write the data out
//        ImageIO.write(bi, "jpg", out);
//        try {
//            out.flush();
//        } finally {
//            out.close();
//        }
//    }
//
//
//    public Producer initRandomProducer(){
//        Properties properties = new Properties();
//        properties.setProperty("kaptcha.image.width","100");
//        properties.setProperty("kaptcha.image.height","50");
//        properties.setProperty("kaptcha.noise.impl","com.google.code.kaptcha.impl.NoNoise");
//        properties.setProperty("kaptcha.textproducer.char.string","0123456789abcdefghijklmnopqrstuvwxyz");
//        properties.setProperty("kaptcha.textproducer.char.length","4");
//        Config config = new Config(properties);
//        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
//        defaultKaptcha.setConfig(config);
//        Producer captchaProducer = defaultKaptcha;
//
//        return captchaProducer;
//    }
//
//
//    /**
//     * 创建token
//     */
//    private String createToken(LoginInfo loginInfo){
//        //生成token
//        Map<String , Object> payload=new HashMap<String, Object>();
//        Date date=new Date();
//        payload.put("uid", loginInfo);//用户ID
//        payload.put("iat", date.getTime());//生成时间
//        payload.put("ext",date.getTime()+12000*60*60);//过期时间1小时
//        String token= Jwt.createToken(payload);
//        return token;
//    }
}
