package naumen.course;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.Scanner;

/**
 * Скачивание файла размером 100MB. При нажатии любой кнопки скачивание останавливается
 */
public class TestTask {
    public static void main(String[] args) throws MalformedURLException {
        Task task = new TaskImpl(URI.create("https://speedtest.selectel.ru/100MB").toURL(), new File("~file.bin"));
        task.start();
        new Thread(() -> {
            System.out.println("Tap any button to stop: ");
            try {
                System.in.read();
            } catch(IOException e) {
                throw new RuntimeException(e);
            }
            task.stop();
        }).start();
    }
}
