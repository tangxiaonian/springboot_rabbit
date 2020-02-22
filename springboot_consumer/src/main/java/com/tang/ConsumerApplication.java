package com.tang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

/**
 * @Classname ConsumerApplication
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2019/12/25 18:38
 * @Created by ASUS
 */
@SpringBootApplication
public class ConsumerApplication {

    public static void main(String[] args) {

        SpringApplication.run(ConsumerApplication.class, args);

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}