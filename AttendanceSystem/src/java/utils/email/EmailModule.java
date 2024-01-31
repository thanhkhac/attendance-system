/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils.email;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author nguye
 */
public class EmailModule {
    
    // NguyenManhDuong - Send otp to email
    public String sendOTP(String title , String mess ,String receiveEmail) {
//        final String title = "Demo Send Mail";    // Title mail
        final String from = "demojavamail268@gmail.com"; // Email gửi tbao
        final String password = "vfvh zchc ptsj fkgr";  // Password của email gửi tbao
        String capcha = generateRandomString();

//         Nội dung gửi mail
        String content = "\n** This is an automated message -- please do not reply as you will not receive a response. ** "
                + "\nYour " + mess +" is: " + capcha
                + "\nThank you.";

        // Đối tượng trong java để lưu trữ cặp key-value được sử dụng để cấu hình 
        Properties pros = new Properties();
        // SMTP (Simple Mail Transfer Protocol)
        pros.put("mail.smtp.host", "smtp.gmail.com");   // SMTP HOST - địa chỉ của máy chủ SMTP của gmail sử dụng để gửi mail
        pros.put("mail.smtp.port", "587");  // TLS 587 (Transport Layer Security)
        pros.put("mail.smtp.auth", "true");
        pros.put("mail.smtp.starttls.enable", "true");

        // create authenticator - 
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        };
        // Phiên làm việc
        Session session = Session.getInstance(pros, auth);
        // Send email
        MimeMessage msg = new MimeMessage(session);
        // generate messages
        try {
            // Kiểu nội dung
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            // Người gửi
            msg.setFrom(from);
            // Người nhận
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiveEmail, false));
            // tiêu đề mail
            msg.setSubject(title);
            // Content mail
            msg.setText(content, "UTF-8");
            // Gửi mail
            Transport.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return capcha;
    }

    // NguyenManhDuong - Generate random String with length 8
    public String generateRandomString() {
        String regex = "[a-zA-Z0-9]{8}"; // Regex để tạo chuỗi 8 ký tự bất kỳ
        Pattern pattern = Pattern.compile(regex);
        StringBuilder stringBuilder = new StringBuilder();

        // Khởi tạo chuỗi ngẫu nhiên ban đầu
        String randomInitialString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        while (stringBuilder.length() < 8) {
            int index = (int) (randomInitialString.length() * Math.random());
            stringBuilder.append(randomInitialString.charAt(index));
        }
        String randomString = stringBuilder.toString();

        // Kiểm tra xem chuỗi ngẫu nhiên có phù hợp với regex không
        Matcher matcher = pattern.matcher(randomString);
        if (matcher.matches()) {
            return randomString;
        } else {
            // Nếu không phù hợp, thử tạo chuỗi mới
            return generateRandomString();
        }
    }
    
}
