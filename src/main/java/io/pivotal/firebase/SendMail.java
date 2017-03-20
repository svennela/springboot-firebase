package io.pivotal.firebase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by svennela on 3/19/17.
 */
//@PropertySource("classpath:application.properties")
@Component
public class SendMail {

     Properties mailServerProperties;
     Session getMailSession;
     MimeMessage generateMailMessage;



     public void generateAndSendEmail(String email,String message,
                                      String gmailAccount,String gmailPassword) throws AddressException, MessagingException {

          // Step1
          System.out.println("\n 1st ===> setup Mail Server Properties..");
          mailServerProperties = System.getProperties();
          mailServerProperties.put("mail.smtp.port", "587");
          mailServerProperties.put("mail.smtp.auth", "true");
          mailServerProperties.put("mail.smtp.starttls.enable", "true");
          System.out.println("Mail Server Properties have been setup successfully..");

          // Step2
          System.out.println("\n\n 2nd ===> get Mail Session..");
          getMailSession = Session.getDefaultInstance(mailServerProperties, null);
          generateMailMessage = new MimeMessage(getMailSession);
          generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
          generateMailMessage.setSubject("Mail from cloundry foundry app..");
          String emailBody = "<b>"+message+"<b>";
          generateMailMessage.setContent(emailBody, "text/html");
          System.out.println("Mail Session has been created successfully..");

          // Step3
          System.out.println("\n\n 3rd ===> Get Session and Send mail");
          Transport transport = getMailSession.getTransport("smtp");
          System.out.println("--------------");
          System.out.println(gmailAccount);
          System.out.println(gmailPassword);
          System.out.println("--------------");
          // Enter your correct gmail UserID and Password
          // if you have 2FA enabled then provide App Specific Password
          transport.connect("smtp.gmail.com", gmailAccount, gmailPassword);

          transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
          transport.close();
     }
}
