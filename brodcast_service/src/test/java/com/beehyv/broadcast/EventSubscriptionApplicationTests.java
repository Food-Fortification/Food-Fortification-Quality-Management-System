package com.beehyv.broadcast;

import com.beehyv.broadcast.service.ConsumerService;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

@SpringBootTest
class EventSubscriptionApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testFile() {

        String text = "Hello world";
        BufferedWriter output = null;
        try {
            File file = new File("example.txt");
            output = new BufferedWriter(new FileWriter(file));
            output.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    void testLog() {
        Logger logger  = Logger.getLogger(ConsumerService.class);

        logger.info(new Date().toString());
        logger.info("str");
    }
    @Test
    void testFile11() {
        try{
            // Create new file
            String content = "This is the content to write into create file1";
            String path="/home/beehyv/Desktop/rice/evnet driven_service/example.txt";
            File file = new File(path);

            // If file doesn't exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            // Write in file
            bw.write(content);

            // Close connection
            bw.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

}
