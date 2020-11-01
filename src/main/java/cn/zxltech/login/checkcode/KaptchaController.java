package cn.zxltech.login.checkcode;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.code.kaptcha.impl.DefaultKaptcha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.zxltech.login.service.UserService;
import cn.zxltech.login.bean.User;


@Controller
public class KaptchaController {

    @Autowired
    DefaultKaptcha defaultKaptcha;
    
    @Autowired
    private UserService userService;

    @RequestMapping("/defaultKaptcha") //响应验证码图片的路径请求

    // Java Servlet 是运行在 Web 服务器或应用服务器上的程序，它是作为来自 Web 浏览器或其他 HTTP 客户端的请求和 HTTP 服务器上的数据库或应用程序之间的中间层。
    /**
     * 生成验证码
     * 
     * @param request
     * @param response
     * @return
     */
    public void defaultKaptcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        byte[] captcha = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        if(request.getParameter("d") == null){// 是否看不清验证码
            try {
                // 将生成的验证码保存在session中
                String generatedText = defaultKaptcha.createText();
                request.getSession().setAttribute("trueCode", generatedText); //getSession()返回与该请求关联的当前 session 会话，或者如果请求没有 session 会话，则创建一个。
                BufferedImage bi = defaultKaptcha.createImage(generatedText);

                ImageIO.write(bi, "png", out);
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);// 404
                return;
            }
        }else{
            try {
                String generatedText = myText();
                GetImage.outputImage(120, 40, out, generatedText);
                request.getSession().setAttribute("trueCode", generatedText);
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);// 404
                return;
            }
        }
           

        // 当一个 Web 服务器响应一个 HTTP 请求时，响应通常包括一个状态行、一些响应报头、一个空行和文档。
        captcha = out.toByteArray();
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate"); //隐式数据,指示文档不应被缓存
        response.setHeader("Pragma", "no-cache");// Set standard HTTP/1.0 no-cache header.
        response.setDateHeader("Expires", 0); // 指定过期时间为0，即不缓存
        response.setContentType("image/png"); //MIME 类型为 iamge/png
        ServletOutputStream sout = response.getOutputStream(); //向response的缓冲区写入字节
        sout.write(captcha);
        sout.flush();
        sout.close();
    }

    /**
     * 校对验证码
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView imgverifyControllerDefaultKaptcha(HttpServletRequest request, HttpServletResponse response, User user) {
        ModelAndView model = new ModelAndView();
        String rightCode = (String) request.getSession().getAttribute("trueCode");
        String tryCode = request.getParameter("tryCode");
        System.out.println("trueCode:" + rightCode + " ———— tryCode:" + tryCode);
        if (!rightCode.equals(tryCode)) {
            model.addObject("info", "验证码错误,请再输一次!");
            model.setViewName("login");
        } else {
            if( userService.login(user).isSuccess()){
                model.addObject("info", "登陆成功");
                model.setViewName("index");
            }else{
                model.addObject("info", "用户名不存在或密码错误！");
                model.setViewName("login");
            }
            
        }
        return model;
    }

    /**
     * 生成随机字符串
     * 
     * @return 随机字符串
     */
    private String myText(){
        StringBuffer text = new StringBuffer();
        char[] ch = "abcde2345678gfynmnpwx".toCharArray();
        Random r = new Random(); //随机数
        int index;
        for(int i = 0; i < 4; i++){
            index = r.nextInt(ch.length);
            text.append(ch[index]);
        }
        return text.toString();
    }

}
