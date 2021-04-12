package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;

import com.google.gson.Gson;

import java.util.List;
import java.util.ArrayList;

/**
 * Handles requests sent to the /getQuizQuestions URL. Try running a server and
 * navigating to /getQuizQuestions!
 */
@WebServlet("/getQuizQuestions")
public class QuizQuestionsServlet extends HttpServlet {

    static final long serialVersionUID = 0;

    class Question {
      public String question;
      public List<String> answers;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Create Datastore object and query questions
        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

        Query<Entity> query = Query.newEntityQueryBuilder().setKind("Questions").build();
        QueryResults<Entity> questions = datastore.run(query);

        List<Question> questionsForJson = new ArrayList<>();
        while (questions.hasNext()) {
            Entity question = questions.next();

            Question questObj = new Question();
            questObj.question = question.getString("QuestionText");
            questObj.answers = List.of(
              question.getString("Answer1"),
              question.getString("Answer2"),
              question.getString("Answer3"),
              question.getString("Answer4")
            );

            questionsForJson.add(questObj);
        }
        String json = convertToJsonUsingGson(questionsForJson);
        // Send the JSON as the response
        response.setContentType("application/json;");
        response.getWriter().println(json);
    }

    private String convertToJsonUsingGson(List<Question> q) {
        Gson gson = new Gson();
        String json = gson.toJson(q);
        return json;
    }
}