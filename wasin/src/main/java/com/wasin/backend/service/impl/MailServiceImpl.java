package com.wasin.backend.service.impl;

import com.wasin.backend._core.exception.BaseException;
import com.wasin.backend._core.exception.error.NotFoundException;
import com.wasin.backend.domain.dto.UserRequest;
import com.wasin.backend.domain.entity.Email;
import com.wasin.backend.domain.validation.MailValidation;
import com.wasin.backend.domain.validation.UserValidation;
import com.wasin.backend.repository.MailRepository;
import com.wasin.backend.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;
    private final UserValidation userValidation;
    private final MailRepository mailRepository;
    private final MailValidation mailValidation;

    @Value("${spring.mail.email}")
    private String fromMail;

    public void sendMail(UserRequest.EmailDTO requestDTO) {
        String toMail = requestDTO.email();
        userValidation.checkEmailAlreadyExist(toMail);

        // 메일 코드 생성(6자리 수), 전송
        String mailCode = createMailCode();
        MimeMessage message = createMessage(toMail, mailCode);
        javaMailSender.send(message);

        // 레디스에 저장
        Email email = Email.builder().email(toMail).code(mailCode).isVerified(false).build();
        mailRepository.deleteById(toMail);
        mailRepository.save(email);
    }

    public void checkMailCode(UserRequest.EmailCheckDTO requestDTO) {
        Email email = findByEmail(requestDTO.email());
        mailValidation.checkCodeValid(email, requestDTO.code());

        email.updateVerified();
        mailRepository.save(email);
    }

    public void checkVerified(String email) {
        Email email2 = findByEmail(email);
        mailValidation.checkVerified(email2);
    }

    private Email findByEmail(String email) {
        return mailRepository.findById(email).orElseThrow(
                () -> new NotFoundException(BaseException.EMAIL_NOT_VERIFIED)
        );
    }

    private MimeMessage createMessage(String email, String mailCode) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            message.setFrom(fromMail);
            message.setRecipients(MimeMessage.RecipientType.TO, email);
            message.setSubject("[와신상담] 인증 코드");
            message.setText(getBody(mailCode),"UTF-8", "html");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return message;
    }

    private String getBody(String mailCode) {
        String body = "<h3 style='color:#3A7DFF'> 안녕하세요. 와신상담 서비스입니다. </h3>";
        body += "<p>" + "인증코드 확인 후 회원가입을 진행해주세요. 아래 인증 모드를 복사하여 입력해 주시기 바랍니다." + "</p> <br>";
        body += "<h3 style='color:#3A7DFF'> *이메일 인증 코드: " + mailCode + "</h3>";
        return body;
    }

    private String createMailCode() {
        int code = (int)(Math.random() * (90000)) + 100000;
        return Integer.toString(code);
    }
}
