package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

import com.google.gson.Gson;

/** Handles requests sent to the /hello URL. Try running a server and navigating to /hello! */
@WebServlet("/getQuizQuestions")
public class QuizQuestionsServlet extends HttpServlet {

  static final long serialVersionUID = 0;
    class QuizQuestion {
        String id;
        String question;
        ArrayList<String> answers;
        String questionType;

        public QuizQuestion(String id, String question, ArrayList<String> answers, String questionType) {
            this.id = id;
            this.question = question;
            this.answers = answers;
            this.questionType = questionType;
        }
    }
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ArrayList<String> a = new ArrayList<String>();
        a.add("Hike");
        a.add("Swim");
        a.add("Relax");

        QuizQuestion quest = new QuizQuestion("0","What do you like to do?", a, "Introduction");

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
