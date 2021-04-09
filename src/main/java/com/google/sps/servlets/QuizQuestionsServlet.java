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


/**
 * Handles requests sent to the /getQuizQuestions URL. Try running a server and
 * navigating to /getQuizQuestions!
 */
@WebServlet("/getQuizQuestions")
public class QuizQuestionsServlet extends HttpServlet {

    static final long serialVersionUID = 0;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Create Datastore object and query questions
        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
        
        Query<Entity> query = Query.newEntityQueryBuilder().setKind("Questions").build();
        QueryResults<Entity> questions = datastore.run(query);

        // Send the JSON as the response
        response.setContentType("application/json;");
        response.getWriter().println(questions);
    }
}