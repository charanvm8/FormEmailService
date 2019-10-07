package com.email.generic.service.controller;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.email.generic.service.model.EmailModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.env.Environment;

import java.util.Properties;

@RestController
@RequestMapping("/")
public class EmailController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailController.class);

    @Autowired
    Environment env;

    @RequestMapping(value = "/sendFormEmail/{toEmail}", method = RequestMethod.POST)
    public String sendEmail(@ModelAttribute EmailModel emailModel,@PathVariable String toEmail){
        final String username = env.getProperty("USER_EMAIL");
        final String password = env.getProperty("USER_PASSWORD");
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailModel.getEmail()));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(toEmail)
            );
            message.setSubject("Git Website Contact - "+emailModel.getName());
            message.setText(emailModel.getMessage());

            Transport.send(message);

            System.out.println("Done");
            return "Email Sent";

        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error Sending email";
        }

    }
}
