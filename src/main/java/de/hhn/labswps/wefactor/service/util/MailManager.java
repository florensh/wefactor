package de.hhn.labswps.wefactor.service.util;

import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MailManager {

    /** Handy constant to have a class name which can easily be refactored. **/
    public static final String MY_NAME = MailManager.class.getName();
    /** Standard class logger **/
    private static Logger logger = Logger.getLogger(MY_NAME);

    // private static final String SMTP_SERVER = "smtp.rz.hs-heilbronn.de";
    private static final String SMTP_SERVER = "smtp.stud.hs-heilbronn.de";

    Session session;

    @Value("${fakeSending}")
    private Boolean fakeSending;

    @Value("${mailusername}")
    private String username;

    @Value("${password}")
    private String password;

    @Value("${replyto}")
    private String replyTo;

    private String disclaimer;
    private String signature;
    private boolean prepared;

    private String completeBody(WefactorMailMessage message) {
        // String result = message.getBody() + "\n\n" + signature + "\n\n"
        // + disclaimer;
        // return result;

        return message.getBody();
    }

    /**
     * @return the disclaimer
     */
    public String getDisclaimer() {
        return disclaimer;
    }

    WefactorMailMessage getMessage() {
        WefactorMailMessage message = new WefactorMailMessage();
        return message;

    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return the replyTo
     */
    public String getReplyTo() {
        return replyTo;
    }

    /**
     * @return the signature
     */
    public String getSignature() {
        return signature;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    @PostConstruct
    public void prepareSystem() {
        logger.info("status " + this);

        Properties props = System.getProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.host", SMTP_SERVER);
        props.put("mail.port", "587");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");

        session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        this.prepared = true;
    }

    public void sendMessage(WefactorMailMessage message)
            throws AddressException, MessagingException {

        Message msg = new MimeMessage(session);

        // Copy all elements to the mime message

        // FROM
        msg.setFrom(new InternetAddress(replyTo));
        // TO and BCC
        msg.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(message.getEmailAddress(), false));
        msg.setRecipients(Message.RecipientType.BCC,
                InternetAddress.parse(replyTo, false));
        // SUBJECT
        msg.setSubject(message.getSubject());
        // BODY
        msg.setText(completeBody(message));
        // SEND DATE
        msg.setSentDate(new Date());
        // HEADER INFO
        msg.setHeader("X-Mailer", "weFactor application");

        // Send the stuff

        if (Boolean.FALSE.equals(fakeSending)) {
            Transport.send(msg);

        }
    }

    /**
     * @param disclaimer
     *            the disclaimer to set
     */
    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @param replyTo
     *            the replyTo to set
     */
    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    /**
     * @param signature
     *            the signature to set
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "MailManager [username=" + username + ", password=" + password
                + ", replyTo=" + replyTo + ", disclaimer=" + disclaimer
                + ", signature=" + signature + ", prepared=" + prepared + "]";
    }

    class WefactorMailMessage {

        public WefactorMailMessage(String subject, String emailAddress,
                String body) {
            this.emailAddress = emailAddress;
            this.body = body;
            this.subject = subject;

        }

        public WefactorMailMessage() {
        }

        private String subject;
        private String emailAddress;

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getEmailAddress() {
            return emailAddress;
        }

        public void setEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        private String body;

    }

    public void sendMessage(String mailAdress, String subject, String body)
            throws AddressException, MessagingException {
        this.sendMessage(new WefactorMailMessage(subject, mailAdress, body));

    }

}
