package com.deskshop.utils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MailUtil {
    public static void sendWelcomeMail(String recipent) {
        String subject = "Bienvenue sur DeskShop";
        String body = getMail("welcome-message");

        sendMail(recipent, subject, body);
    }

    private static void sendMail(String recipent, String subject, String body) {
        // GMail user name (just the part before "@gmail.com")
        String userName = "okya.corp@gmail.com";
        // GMail password
        String password = "azerty.1234";
        String[] to = {recipent};

        sendFromGMail(userName, password, to, subject, body);
    }

    private static void sendFromGMail(String from, String pass, String[] to, String subject, String body) {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for (int i = 0; i < to.length; i++) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for (InternetAddress toAddress1 : toAddress) {
                message.addRecipient(Message.RecipientType.TO, toAddress1);
            }

            message.setSubject(subject);
            message.setContent(body, "text/html");
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            System.out.println("message send");
        } catch (MessagingException ae) {
            ae.printStackTrace();
        }
    }

    private static String getMail(String type) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader;
            reader = new BufferedReader(new FileReader(MailUtil.class.getResource("/"+type + ".html").toExternalForm().substring(6)));
            String line = reader.readLine();
            while (line != null) {
                sb.append(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
