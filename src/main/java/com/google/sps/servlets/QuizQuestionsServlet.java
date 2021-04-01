package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;

/** Handles requests sent to the /hello URL. Try running a server and navigating to /hello! */
@WebServlet("/getQuizQuestions")
public class QuizQuestionsServlet extends HttpServlet {

  static final long serialVersionUID = 0;
    class QuizQuestion {
        HashMap<String, List<String>> quiz = new HashMap<>();

        public QuizQuestion() {
            quiz.put("Which of these would you most like to do?", List.of("Go on a hike in nature", "Take a trip downtown", "Go to a museum", "Take a day for relaxation"));
            quiz.put("What is your favorite food?", List.of("Fruit", "Burgers", "Wraps", "Steak"));
            quiz.put("How active do you like to be?", List.of("Very much", "Much", "Not much", "Not at all"));
            quiz.put("What do you value most?", List.of("Learning new thigs", "Physical activity", "Beautiful scenery", "New experiences"));
            quiz.put("How long will your trip be?", List.of("2+ weeks", "1 week", "Less than a week", "One day"));
            quiz.put("How much are you willing to spend?", List.of("$$$$", "$$$", "$$", "$"));
            quiz.put("Will you bring children? If so, how many?", List.of("No", "1", "2-4", "4+"));

        }
    }
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ArrayList<String> a = new ArrayList<String>();
        a.add("Hike");
        a.add("Swim");
        a.add("Relax");

        QuizQuestion quest = new QuizQuestion();

        // Convert the server stats to JSON
        String json = convertToJsonUsingGson(quest);

        // Send the JSON as the response
        response.setContentType("application/json;");
        response.getWriter().println(json);
    }

    /**
    * Converts a ServerStats instance into a JSON string using the Gson library.
    */
    private String convertToJsonUsingGson(QuizQuestion quest) {
        Gson gson = new Gson();
        String json = gson.toJson(quest);
        return json;
    }
}
