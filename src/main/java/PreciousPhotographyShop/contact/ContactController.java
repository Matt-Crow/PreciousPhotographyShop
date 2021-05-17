package PreciousPhotographyShop.contact;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ContactController {

    private EmailCfg emailCfg;
    private JavaMailSender mailSender;


    public ContactController(EmailCfg emailCfg){
        this.emailCfg = emailCfg;
    }

    @GetMapping("/contact")
    public String showContactForm(){
        return "contact_form";
    }

    @PostMapping("/contact")
    public String submitContact(HttpServletRequest request,
        @RequestParam("attachment") MultipartFile multipartFile
        ) throws MessagingException {
        String fullname = request.getParameter("fullName");
        String email = request.getParameter("email");
        String subject = request.getParameter("subject");
        String content = request.getParameter("content");

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.emailCfg.getHost());
        mailSender.setPort(this.emailCfg.getPort());
        mailSender.setUsername(this.emailCfg.getUsername());
        mailSender.setPassword(this.emailCfg.getPassword());


        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);



        String mailSubject = fullname + " sent you a message.";
        String mailContent = "<p><b>Sender Name : </b>"  + fullname + "</p>";
        mailContent += "<p><b>Sender Email</b>: " + email + "</p>";
        mailContent += "<p><b>Subject: </b>" + subject + "</p>";
        mailContent += "<p><b>Content: </b>" +  content + "</p>";
        mailContent += "<hr> <img src = 'cid:logoImage' />";


        helper.setFrom("contact@precious.com");
        helper.setTo("rramsdany@gmail.com");
        helper.setSubject(subject);
        helper.setText(mailContent, true);

        ClassPathResource resource = new ClassPathResource("/static/images/PreciousLogo.jpg");
        helper.addInline("logoImage", resource);

        if(!multipartFile.isEmpty()){
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            InputStreamSource source = new InputStreamSource() {
                @Override
                public InputStream getInputStream() throws IOException {
                    return multipartFile.getInputStream();
                }
            };
            helper.addAttachment(fileName, source);
        }
        mailSender.send(message);
        return "message";
    }

}
