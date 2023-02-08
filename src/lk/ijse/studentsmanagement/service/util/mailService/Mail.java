package lk.ijse.studentsmanagement.service.util.mailService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Mail implements Runnable{
    String msg;
    String to;
    String subject;
    List<String> list;

    public Mail(String msg, String to, String subject, List<String> list) {
        this.msg = msg;
        this.to = to;
        this.subject = subject;
        this.list = list;
    }

    private void outMailSingle() throws MessagingException {

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
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("softwareengineeringIJSE@gmail.com", "boetfzbejjithdve");  // have to change some settings in SMTP
            }
        });

        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(new InternetAddress(from));
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        mimeMessage.setSubject(subject);
        mimeMessage.setText(msg);
        Transport.send(mimeMessage);

        System.out.println("Sent... " + to);
    }

    private void outMail() throws MessagingException {
        for (String ele : list) {
            to = ele;
            outMailSingle();
        }
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
       try{
           if(list!=null) {
               outMail();
           }else{
               outMailSingle();
           }
       } catch (MessagingException e) {
           throw new RuntimeException(e);
       }
    }
}
