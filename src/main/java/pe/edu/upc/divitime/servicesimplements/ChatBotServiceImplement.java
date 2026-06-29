package pe.edu.upc.divitime.servicesimplements;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pe.edu.upc.divitime.servicesinterfaces.IChatBotService;

import java.util.List;
import java.util.Map;

@Service
public class ChatBotServiceImplement implements IChatBotService {

    @Value("${groq.api.url}")
    private String groqApiUrl;

    @Value("${groq.api.key}")
    private String groqApiKey;

    @Value("${groq.model}")
    private String groqModel;

    private final WebClient webClient;

    private static final String OUT_OF_SCOPE_RESPONSE =
            "Puedo ayudarte únicamente con temas relacionados a emociones, bienestar personal, estrés, ánimo, comunicación emocional o manejo de conflictos. Si deseas, cuéntame cómo te sientes.";

    public ChatBotServiceImplement() {
        this.webClient = WebClient.builder().build();
    }

    @Override
    public String generateResponse(String userMessage) {

        if (!isEmotionRelated(userMessage)) {
            return OUT_OF_SCOPE_RESPONSE;
        }

        Map<String, Object> requestBody = Map.of(
                "model", groqModel,
                "messages", List.of(
                        Map.of(
                                "role", "system",
                                "content",
                                "Eres Divi, un asistente emocional del sistema DiviTime. " +
                                        "Tu función es ayudar únicamente con temas relacionados a emociones, bienestar personal, estrés, ansiedad leve, tristeza, frustración, enojo, comunicación emocional, autoestima, organización emocional y manejo de conflictos cotidianos. " +
                                        "No respondas preguntas de programación, tareas académicas, finanzas, recetas, tecnología, política, entretenimiento ni temas que no estén relacionados con emociones. " +
                                        "Si el usuario pregunta algo fuera del tema, responde amablemente que solo puedes ayudar con temas emocionales. " +
                                        "No des diagnósticos médicos ni psicológicos. No reemplazas a un profesional de salud mental. " +
                                        "Si el usuario expresa intención de hacerse daño o dañar a otros, recomiéndale buscar ayuda inmediata con una persona de confianza o servicios de emergencia."
                        ),
                        Map.of(
                                "role", "user",
                                "content", userMessage
                        )
                ),
                "temperature", 0.5
        );

        Map response = webClient.post()
                .uri(groqApiUrl)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + groqApiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        if (response == null || response.get("choices") == null) {
            return "No pude generar una respuesta en este momento.";
        }

        List choices = (List) response.get("choices");

        if (choices.isEmpty()) {
            return "No pude generar una respuesta en este momento.";
        }

        Map firstChoice = (Map) choices.get(0);
        Map message = (Map) firstChoice.get("message");

        if (message == null || message.get("content") == null) {
            return "No pude generar una respuesta en este momento.";
        }

        return message.get("content").toString();
    }

    private boolean isEmotionRelated(String message) {
        if (message == null || message.trim().isEmpty()) {
            return false;
        }

        String text = message.toLowerCase();

        String[] allowedKeywords = {
                "siento", "sentir", "sentimiento", "emocion", "emoción", "emociones",
                "triste", "tristeza", "feliz", "felicidad", "alegre", "alegría",
                "ansiedad", "ansioso", "ansiosa", "estres", "estrés", "estresado", "estresada",
                "preocupado", "preocupada", "preocupación", "miedo", "temor",
                "enojo", "enojado", "enojada", "ira", "molesto", "molesta",
                "frustrado", "frustrada", "frustración", "culpa", "vergüenza",
                "solo", "sola", "soledad", "desanimado", "desanimada",
                "autoestima", "confianza", "inseguridad", "calmarme", "relajarme",
                "respirar", "bienestar", "ánimo", "animo", "conflicto",
                "familia", "discusión", "pelea", "relación", "relaciones",
                "acompañame", "escúchame", "escuchame", "ayuda emocional"
        };

        for (String keyword : allowedKeywords) {
            if (text.contains(keyword)) {
                return true;
            }
        }

        return false;
    }
}