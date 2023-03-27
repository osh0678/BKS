package com.seong.bks;

import com.seong.bks.controller.BksController;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@EnableAsync
public class BookingSchedule {
    private final BksController Bks;
    public static final String MASSAGE = "massageCode";
    public static final String LUNCH = "lunchCode";
    public static final String TYPE_PATH = "massagerType.txt";
    LocalDateTime now = LocalDateTime.now();


    /**
     * Cron 표현식을 사용한 작업 예약
     * 초(0-59) 분(0-59) 시간(0-23) 일(1-31) 월(1-12) 요일(0-7)
     */
    @Scheduled(cron = "0 0 8 * * 1-4") // 실제 사용할 것 || 월 - 목, 00시 00분 00초에 스케쥴링
//    @Scheduled(cron = "*/10 * * * * ?") //테스트 코드
    public void scheduleTaskForMassage() throws IOException {
        FileReader typeReader = new FileReader(TYPE_PATH);

        String type = "";

        int ch;
        while ((ch = typeReader.read()) != -1) {
            type += String.valueOf((char) ch);
        }
        System.out.println("type : " + type);

        String cookie = "";
        System.out.println("[Massage] Start Schedule - " + now);
        cookie = Bks.login("id", "password");

        if(type.equals("true")){
            Bks.bookingEvent(cookie, MASSAGE, "ID", "14:00");
        }else{
            Bks.bookingEvent(cookie, MASSAGE, "ID", "14:30");
        }
    }

    /**
     * Cron 표현식을 사용한 작업 예약
     * 초(0-59) 분(0-59) 시간(0-23) 일(1-31) 월(1-12) 요일(0-7)
     */
    @Scheduled(cron = "0 0 8 * * 1-4") // 실제 사용할 것 || 월 - 목, 00시 00분 00초에 스케쥴링
//    @Scheduled(cron = "*/10 * * * * ?") //테스트 코드
    public void scheduleTaskForMassageEnd() throws IOException {
        FileReader typeReader = new FileReader(TYPE_PATH);

        String type = "";

        int ch;
        while ((ch = typeReader.read()) != -1) {
            type += String.valueOf((char) ch);
        }
        System.out.println("type : " + type);

        String cookie = "";
        System.out.println("[Massage] Start Schedule - " + now);
        cookie = Bks.login("id", "password");

        if(type.equals("true")){
            Bks.bookingEvent(cookie, MASSAGE, "hwan", "14:00");
//            Bks.bookingEvent(cookie, MASSAGE,"lulu", "15:00");
        }else{
            Bks.bookingEvent(cookie, MASSAGE, "hwan", "14:30");
//            Bks.bookingEvent(cookie, MASSAGE,"lulu", "15:30");
        }
    }


    @Scheduled(cron = "58 59 7 * * 2-4") // 실제 사용할 것 || 화 - 목, 00시 00분 00초에 스케쥴링
    public void scheduleTaskForLunch() {
        String cookie = "";
        System.out.println("[Lunch] Start Schedule - " + now);
        cookie = Bks.login("id", "password");

        Bks.bookingEvent(cookie, LUNCH, "id", "12:30");
    }

//    @Scheduled(cron = "*/20 * * * * *")
    @Scheduled(cron = "0 0 8 * * 6")
    public void scheduleTaskForChangeTime() {
        try{
            FileReader typeReader = new FileReader(TYPE_PATH);
            File file = new File(TYPE_PATH);
            String trueText = "true";
            String falseText = "false";

            String type = "";

            int ch;

            while ((ch = typeReader.read()) != -1) {
                type += String.valueOf((char) ch);
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            if(type.equals("true")){
                System.out.println("false 로 전환");
                writer.write(falseText);
            }else{
                System.out.println("true 로 전환");
                writer.write(trueText);
            }
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
