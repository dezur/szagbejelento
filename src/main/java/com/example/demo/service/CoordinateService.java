package com.example.demo.service;

import com.example.demo.dto.CoordinateDto;
import com.example.demo.dto.converter.CoordinateConverter;
import com.example.demo.model.Coordinate;
import com.example.demo.repository.CoordinateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;

@Service
@Transactional
public class CoordinateService {

    @Autowired
    private JavaMailSender mailSender;

    @Qualifier("messageSource")
    @Autowired
    private MessageSource messages;

    @Autowired
    private CoordinateRepository coordinateRepository;
    @Autowired
    private CoordinateConverter converter;

    public List<Coordinate> listAllCoordinates() {
        return coordinateRepository.findAll();
    }

    public void saveCoordinate(CoordinateDto coordinateDto) {
        sendEmail(coordinateDto);
        Coordinate coordinate = converter.convertFromDto(coordinateDto);
        coordinate.setCreationDate(new Timestamp(System.currentTimeMillis()));
        System.out.println(coordinateDto.getName());
        System.out.println(coordinateDto.getEmail());
        System.out.println(coordinateDto.getNotes());
        coordinateRepository.save(coordinate);
    }

    public Coordinate getCoordinate(Integer id) {
        return coordinateRepository.findById(id).get();
    }

    public void deleteCoordinate(Integer id) {
        coordinateRepository.deleteById(id);
    }

    public List<Coordinate> filterByDate(Date date) {
        return coordinateRepository.findCoordinatesByCreationDate(date);
    }

    public void sendEmail(CoordinateDto coordinateDto) {
        mailSender.send(constructEmail(coordinateDto));
//        mailSender.send(constructConfirmationEmail(coordinateDto));
    }

    private MimeMessagePreparator constructEmail(CoordinateDto coordinateDto) {
        String message = "Tisztelt Címzett!<br><br>" +
                "A Jó levegőt Kistarcsának Facebook csoport számára létrehozott egyszerűsített szag bejelentői felületen a következő bejelentést tették:<br><br>" +
                "A mai napon erős, kellemetlen, záptojáshoz vagy csatornához hasonló szagot éreztem!<br><br>" +
                "Bejelentő neve: " + coordinateDto.getName() + "<br>" +
                "Bejelentő email címe: " + coordinateDto.getEmail() + "<br>" +
                "Helyszín: " + coordinateDto.getAddress() + "<br>" +
                "Egyéb információ: " + coordinateDto.getNotes() + "<br><br>" +
                "Szeretném felhívni a figyelmüket arra, hogy a tavalyi év folyamán már csak alig vagy a korábbihoz képest jóval ritkábban lehetett érezni ezt a jellegzetes bűzt, de ez év januárjában ismét gyakran van büdös.<br>" +
                "Kérem, legyenek szívesek ez ügyben vizsgálatokat, ellenőrzéseket végezni!<br><br>" +
                "Köszönettel,<br>" +
                "Jó levegőt Kistarcsának Facebook csoport";

        return prepareMimeMessage("Szagbejelentés", message);
    }

    private MimeMessagePreparator constructConfirmationEmail(CoordinateDto coordinateDto) {
        String message = "Tisztelt Bejelentő!<br><br>" +
                "Az Ön által megadott adatok alapján a következő jelentést küldtük el:<br><br>" +
                "A mai napon erős, kellemetlen, záptojáshoz vagy csatornához hasonló szagot éreztem!<br><br>" +
                "Bejelentő neve: " + coordinateDto.getName() + "<br>" +
                "Bejelentő email címe: " + coordinateDto.getEmail() + "<br>" +
                "Helyszín: " + coordinateDto.getAddress() + "<br>" +
                "Egyéb információ: " + coordinateDto.getNotes() + "<br><br>" +
                "Szeretném felhívni a figyelmüket arra, hogy a tavalyi év folyamán már csak alig vagy a korábbihoz képest jóval ritkábban lehetett érezni ezt a jellegzetes bűzt, de ez év januárjában ismét gyakran van büdös.<br>" +
                "Kérem, legyenek szívesek ez ügyben vizsgálatokat, ellenőrzéseket végezni!<br><br>" +
                "Köszönettel,<br>" +
                "Jó levegőt Kistarcsának Facebook csoport";

        return sendConfirmationEmail("Szagbejelentés", message, coordinateDto.getEmail());
    }

    private MimeMessagePreparator prepareMimeMessage(String subject, String body) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            String[] recipients = {"krisztina.bene@ecore.com", "polghiv@csomor.hu", "kornyezetvedelem@csomor.hu", "zoldhatosag@pest.gov.hu", "orszagoszoldhatosag@pest.gov.hu", "jolevegotkistarcsanak@gmail.com", "petrenyi@gmail.com", "gebeigabi86@gmail.com"};
//            String[] recipients = {"petrenyi@gmail.com"};

            messageHelper.setFrom("jolevegot@nz-smartlife.hu");
            messageHelper.setTo(recipients);
            messageHelper.setSubject(subject);
            messageHelper.setText(body, true);
        };
    }

    private MimeMessagePreparator sendConfirmationEmail(String subject, String body, String email) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            messageHelper.setFrom("jolevegot@nz-smartlife.hu");
            messageHelper.setTo(email);
            messageHelper.setSubject(subject);
            messageHelper.setText(body, true);
        };
    }
}