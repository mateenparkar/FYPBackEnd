package org.fyp.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fyp.db.DatabaseConnector;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import java.util.*;


public class GroqCloudService {
    private static String API = null;
    private final String groqCloudUrl = "https://api.groq.com/openai/v1/chat/completions";
    public GroqCloudService(String API) {
        API = loadApiKey();
        this.API = API;
    }
    private String loadApiKey(){
        try{
            Properties prop = new Properties();
            prop.load(DatabaseConnector.class.getClassLoader().getResourceAsStream("db.properties"));

            API = prop.getProperty("ai_key");
            if(API == null){
                throw new Exception("Properties file must exist " +
                        "and must contain ai_key");
            }

            return API;


        } catch (Exception e) {
            System.err.println(e.getMessage());

            throw new IllegalArgumentException();
        }
    }

    public List<String> generateQuestions(String bookName) throws Exception {
        String prompt = "Generate 3 questions about the book \"" + bookName + "\".";

        Map<String, Object> payload = new HashMap<>();
        payload.put("model", "llama3-8b-8192");

        List<Map<String, Object>> messages = new ArrayList<>();
        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", prompt);
        messages.add(message);

        payload.put("messages", messages);

        String jsonPayload = new ObjectMapper().writeValueAsString(payload);

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(groqCloudUrl);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("Authorization", "Bearer " + API);
            httpPost.setEntity(new StringEntity(jsonPayload));

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                if (response.getStatusLine().getStatusCode() == 200) {
                    Map<String, Object> responseBody = new ObjectMapper()
                            .readValue(response.getEntity().getContent(), Map.class);
                    String groqResponse = extractContentFromResponse(responseBody);
                    return extractQuestions(groqResponse);
                } else {
                    throw new Exception("Failed to communicate with GroqCloud. Status: "
                            + response.getStatusLine().getStatusCode());
                }
            }
        }
    }

    private String extractContentFromResponse(Map<String, Object> responseBody) {
        List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
        if (choices != null && !choices.isEmpty()) {
            Map<String, String> message = (Map<String, String>) choices.get(0).get("message");
            return message.get("content");
        }
        return "";
    }

    private List<String> extractQuestions(String groqResponse) {
        String[] questionsArray = groqResponse.split("\n");
        List<String> questionsList = new ArrayList<>();

        for (String question : questionsArray) {
            questionsList.add(question);
        }

        return questionsList;
    }

}
