package ar.com.alura.literalura.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class ConsumoAPI {
    private static final Logger logger = LoggerFactory.getLogger(ConsumoAPI.class);

    public String obtenerDatos(String direccion) {
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL) // Configurar para seguir redirecciones
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(direccion))
                .build();

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            logger.info("Código de estado: " + response.statusCode());
            logger.info("Cuerpo de la respuesta: " + response.body());
            if (response.statusCode() == 200) {
                return response.body();
            } else {
                throw new RuntimeException("Error al obtener los datos. Código de estado HTTP: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error al obtener los datos de la API: " + e.getMessage(), e);
        }
    }
}
