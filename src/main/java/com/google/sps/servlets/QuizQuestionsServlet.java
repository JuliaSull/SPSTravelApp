package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.testing.LocalDatastoreHelper;
import com.google.gson.Gson;

/**
 * Handles requests sent to the /getQuizQuestions URL. Try running a server and
 * navigating to /getQuizQuestions!
 */
@WebServlet("/getQuizQuestions")
public class QuizQuestionsServlet extends HttpServlet {

    static final long serialVersionUID = 0;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LocalDatastoreHelper HELPER = LocalDatastoreHelper.create(1.0);
        
        KeyFactory keyFactory;
        Datastore datastore;
        datastore = HELPER.getOptions().toBuilder().setNamespace("ghijklmnop").build().getService();
        keyFactory = datastore.newKeyFactory().setKind("Task");
   
        Key taskKey = keyFactory.newKey(0);
        Key taskKey1 = keyFactory.newKey(1);
        Key taskKey2 = keyFactory.newKey(2);
        Key taskKey3 = keyFactory.newKey(3);
        Key taskKey4 = keyFactory.newKey(4);
        Key taskKey5 = keyFactory.newKey(5);
        Key taskKey6 = keyFactory.newKey(6);
    // [START datastore_batch_lookup]
        Iterator<Entity> tasks = datastore.get(taskKey, taskKey1, taskKey2, taskKey3, 
                                               taskKey4, taskKey5, taskKey6);

        // Convert the quiz question to JSON
        String json = convertToJsonUsingGson(tasks);

        // Send the JSON as the response
        response.setContentType("application/json;");
        response.getWriter().println(json);
    }

    /**
     * Converts a QuizQuestions instance into a JSON string using the Gson library.
     */
    private String convertToJsonUsingGson(Iterator<Entity> task) {
        return new Gson().toJson(task);
    }
}