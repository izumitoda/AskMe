package com.erika.askme.utils;

import com.erika.askme.controller.CommentController;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.util.Map;
import java.util.Properties;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-20 21:23
 **/
@Service
public class SendMails implements InitializingBean {
    private JavaMailSenderImpl mailSender;
    private static final Logger logger= LoggerFactory.getLogger(SendMails.class) ;
    private FreeMarkerConfigurer freeMarkerConfigurer=new FreeMarkerConfigurer();
    //to--发给谁  subject---标题  template---模板  model---模板中变量的替换
    public boolean sendWithHTMLTemplate(String to, String subject, String template, Map<String,Object> model)
    {
        try
        {
            String nick = MimeUtility.encodeText("AskMe系统消息");
            InternetAddress from = new InternetAddress(nick + "<lily_young@126.com>");//加了一个昵称的发件人
            MimeMessage mimeMessage = mailSender.createMimeMessage();//创建邮件文本
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            Configuration configuration=new Configuration();

            freeMarkerConfigurer.setConfiguration(configuration);
            if(freeMarkerConfigurer.getConfiguration()==null)
                System.out.println("here 4");
            Template tpl=freeMarkerConfigurer.getConfiguration().getTemplate(template);
            System.out.println("fff");
            String result = FreeMarkerTemplateUtils.processTemplateIntoString(tpl,model);
            System.out.println("daozhelil1 ");
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(result, true);
            mailSender.send(mimeMessage);
            System.out.println("daozhelil ");
        }
        catch(Exception e)
        {
            logger.error("发送邮件失败"+e);
            return false;
        }
        return true;
    }

    //mailSender初始化
    @Override
    public void afterPropertiesSet() throws Exception {
        mailSender=new JavaMailSenderImpl();
        mailSender.setUsername("lily_young@126.com");//设置发送邮箱名
        mailSender.setPassword("shouquanma960930");//设置Password
        mailSender.setHost("smtp.126.com");//设置邮箱SMTP服务器地址
        mailSender.setPort(465);//设置端口号
        mailSender.setProtocol("smtps");//设置协议
        mailSender.setDefaultEncoding("utf8");//设置默认编码方式
        Properties javaMailProperties=new Properties();
        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties.put("mail.smtp.ssl.enable", "true");
        javaMailProperties.put("mail.smtp.socketFactory.fallback", "true");
        mailSender.setJavaMailProperties(javaMailProperties);
    }
}
