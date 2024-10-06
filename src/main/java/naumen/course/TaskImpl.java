package naumen.course;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

/**
 * Скачивает файл из интернета в файл на локальном хранилище.
 * Скачивание происходит в отдельном потоке порциями.
 * После чтения и записи очередной порции, поток смотрит, не остановили ли скачивание (прервали ли его).
 * Если скачивание остановили, поток удаляет загружаемый файл со скачанными данными и завершается.
 */
public class TaskImpl implements Task {
    /**
     * Размер буфера при скачивании файла кусками
     */
    private final int BUFFER_SIZE = 32 * 1024;

    /**
     * Ссылка на ресурс
     */
    private final URL sourceUrl;

    /**
     * Файл назначения
     */
    private final File destinationFile;

    /**
     * Состояние объекта, загружающего файл
     */
    private TaskState state = TaskState.CREATED;

    /**
     * Поток, осуществляющий скачивание файла
     */
    private Thread downloadThread;

    /**
     * Исключение, сгенерированное во время скачивания файла
     */
    private Exception exception;

    /**
     * Состояние объекта, загружающего файл
     */
    public enum TaskState {
        /**
         * Создан, готов к скачиванию файла
         */
        CREATED,
        /**
         * Скачивает файл
         */
        RUNNING,
        /**
         * Закончил скачивание файла
         */
        FINISHED,
        /**
         * Скачивание файла было остановлено
         */
        STOPPED,
        /**
         * Во время скачивания файла произошла ошибка
         */
        ERROR
    }

    /**
     * @param sourceUrl       ссылка на скачивание
     * @param destinationFile файл, в который записать скачанные данные
     */
    public TaskImpl(URL sourceUrl, File destinationFile) {
        this.sourceUrl = sourceUrl;
        this.destinationFile = destinationFile;
    }

    @Override
    public synchronized void start() {
        if(!TaskState.CREATED.equals(state)) {
            return;
        }

        downloadThread = new Thread(() -> {
            try(BufferedInputStream in = new BufferedInputStream(sourceUrl.openStream(), BUFFER_SIZE);
                FileOutputStream fileOutputStream = new FileOutputStream(destinationFile)) {
                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead;
                while((bytesRead = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
                    fileOutputStream.write(buffer, 0, bytesRead);
                    if(Thread.currentThread().isInterrupted()) {
                        state = TaskState.STOPPED;
                        break;
                    }
                }
            } catch(Exception e) {
                exception = e;
                state = TaskState.ERROR;
            } finally {
                switch(state) {
                    case STOPPED:
                        try {
                            Files.delete(destinationFile.toPath());
                        } catch(IOException e) {
                            exception = e;
                            state = TaskState.ERROR;
                        }
                        break;
                    case RUNNING:
                        state = TaskState.FINISHED;
                        break;
                    default:
                        break;
                }
            }
        });

        state = TaskState.RUNNING;
        downloadThread.start();
    }

    @Override
    public void stop() {
        if(state == TaskState.RUNNING) {
            downloadThread.interrupt();
        }
    }

    public URL getSourceUrl() {
        return sourceUrl;
    }

    public Exception getException() {
        return exception;
    }

    public File getDestinationFile() {
        return destinationFile;
    }

    public TaskState getState() {
        return state;
    }
}
