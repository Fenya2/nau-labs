package naumen.course;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

/**
 * Необходимо сделать “GET” запрос на указанный адрес и обработать
 * ответ. Запрос выполняется на тестовый сервер по адресу
 * “<a href="https://httpbin.org/">...</a>”. Сервер возвращает ответ в формате JSON. Из ответа
 * необходимо извлечь и вывести в консоль
 * только заголовки запроса в виде списка значений через
 * запятую (запрос выполняется по адресу “<a href="https://httpbin.org/headers">...</a>”)
 */
public class Task4V3 {
    /**
     * Целевой URI
     */
    private static final URI uri = URI.create("https://httpbin.org/headers");

    /**
     * Преобразует ответ на запрос по целевому URI в java-объект
     */
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Разобранный ответ на запрос по целевому URI
     */
    private record DTO(@JsonProperty("headers") Map<String, String> headers) {
    }

    /**
     * Решает задачу
     */
    private void solve() {
        DTO dto = headersDTO();
        printHeaderValues(dto);
    }

    /**
     * Печатает в stdout значения заголовков у переданного DTO объекта
     */
    private void printHeaderValues(DTO dto) {
        dto.headers.values().stream().reduce((a, b) -> a + ", " + b).ifPresent(System.out::println);
    }

    /**
     * Выполняет get-запрос на указанный URI, возвращает десериализованный ответ
     */
    private DTO headersDTO() {
        try {
            return mapper.readValue(makeGetRequest(), DTO.class);
        } catch(IOException e) {
            throw new RuntimeException("Не удалось создать DTO", e);
        }
    }

    /**
     * Выполнить GET-запрос на целевой URI
     *
     * @return тело ответа
     */
    private byte[] makeGetRequest() {
        try(HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .build();
            HttpResponse<byte[]> response = client.send(request,
                    HttpResponse.BodyHandlers.ofByteArray());
            return response.body();
        } catch(Exception e) {
            throw new RuntimeException("Не удалось выполнить запрос", e);
        }
    }

    public static void main(String[] args) {
        new Task4V3().solve();
    }
}
