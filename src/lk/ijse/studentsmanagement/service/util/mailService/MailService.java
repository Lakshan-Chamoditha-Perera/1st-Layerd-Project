package lk.ijse.studentsmanagement.service.util.mailService;

import javax.mail.MessagingException;

public interface MailService extends Runnable{
    void outMail() throws MessagingException;
   // void outMail(String msg, ArrayList<String> to, String subject) throws MessagingException;
}
