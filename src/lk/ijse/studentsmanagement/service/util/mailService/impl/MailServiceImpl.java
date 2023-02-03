package lk.ijse.studentsmanagement.service.util.mailService.impl;

import lk.ijse.studentsmanagement.service.util.mailService.MailService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Properties;

public class MailServiceImpl implements MailService {
    String msg;
    String to;
    String subject;

    public MailServiceImpl(String msg, String to, String subject) {
        this.msg = msg;
        this.to = to;
        this.subject = subject;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public void outMail() throws MessagingException {
        //String to = "ruvinisubhasinghe200009@gmail.com";
        //String from = "perera.alc2000@gmail.com";

        String from = "softwareengineeringIJSE@gmail.com";
        String host = "localhost";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", 587);

        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("softwareengineeringIJSE@gmail.com", "boetfzbejjithdve");  // have to change some settings in SMTP
            }
        });

        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(new InternetAddress(from));
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        mimeMessage.setSubject(subject);
        mimeMessage.setText(msg);
        Transport.send(mimeMessage);
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
      if(validate()){
          try {
              outMail();
          } catch (MessagingException e) {
              throw new RuntimeException(e);
          }
      }
    }
    private boolean validate() {
        if(!to.isEmpty()){
            if(!subject.isEmpty()){
                if(!msg.isEmpty()){
                    return true;
                }
                throw new RuntimeException("empty message!");
            }
            throw new RuntimeException("empty subject!");
        }
        throw new RuntimeException("empty receiver!");
    }
}
