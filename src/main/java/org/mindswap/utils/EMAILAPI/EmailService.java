package org.mindswap.utils.EMAILAPI;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.mindswap.model.Invoice;
import org.mindswap.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailService {
    JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmailTo(User user) {

        String emailBody = user.getFirstName() + " " + user.getLastName() + ",\n" +
                "Your rental details: \n" +
                "RentalId: " + user.getRentalList();
        //TODO

        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(user.getEmail());
        mail.setFrom("mindswap5backenproject@gmail.com");
        mail.setReplyTo("mindswap5backenproject@gmail.com");
        mail.setSubject("MOVIE SWAP BUSTER | Rental details");
        mail.setText(emailBody + "\n\n" +
                "Hello! " + user.getFirstName() + ",\n\n" +
                "Here are the details for your movie rental:\n" +
                "- Rental ID: " + user.getRentalList() + "\n\n" +
                "Here are the details for your movie rental:\n"+
                "We hope you enjoy your movie!\n\n" +
                "Best regards,\n" +
                "The Movie Swap Buster team"
        );
        javaMailSender.send(mail);
    }


    public void sendEmailToSpecificEmail(String email) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(email);
        mail.setFrom("mindswap5backenproject@gmail.com");
        mail.setReplyTo("mindswap5backenproject@gmail.com");
        mail.setSubject("MOVIE SWAP BUSTER | Rental details");
        mail.setText("emailBody" + "\n\n" +
                        "Hello! " + "Rui" + ",\n\n" +
                        "Here are the details for your movie rental:\n" +
                        "- Rental ID: " + "Uno, Duos, Tres, Quatroo" + "\n\n" +
                        "Here are the details for your movie rental:\n"+
                "We hope you enjoy your movie!\n\n" +
                        "Best regards,\n" +
                        "The Movie Swap Buster team"
        );
        javaMailSender.send(mail);
    }
    @Transactional
    public void sendEmailWithAttachment(String email, Invoice invoice) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        message.setFrom("mindswap5backenproject@gmail.com");
        helper.setTo(email);
        helper.setSubject("MOVIE SWAP BUSTER | Rental details");
        helper.setText("Hi, " + invoice.getRental().getUser().getFirstName()  + ",\n\n" +
                "We've sent you the invoice as an attachment.\n" +
                "We hope you enjoy your movie!\n\n" +
                "Best regards,\n" +
                "The Movie Swap Buster team");

        FileSystemResource file = new FileSystemResource(new File("src/main/resources/invoices/invoicePDF".concat(invoice.getId().toString()).concat(".pdf")));
        helper.addAttachment("invoicePDF".concat(invoice.getId().toString()).concat(".pdf"), file);

        javaMailSender.send(message);
    }


}
