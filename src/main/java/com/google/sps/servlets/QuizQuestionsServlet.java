package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.Value;
import com.google.cloud.datastore.ListValue;
import com.google.cloud.datastore.StringValue;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.testing.LocalDatastoreHelper;
import com.google.gson.Gson;

import java.util.ArrayList;


/**
 * Handles requests sent to the /getQuizQuestions URL. Try running a server and
 * navigating to /getQuizQuestions!
 */
@WebServlet("/getQuizQuestions")
public class QuizQuestionsServlet extends HttpServlet {

    static final long serialVersionUID = 0;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

        Query<Entity> query = Query.newEntityQueryBuilder().setKind("Questions").build();
        QueryResults<Entity> questions = datastore.run(query);

        // "Questions": [
        //   "Question1": {
        //     "QuestionText": "this is the question text",
        //     "Answer1": "Something1",
        //     "Answer2": "Something2",
        //     "Answer3": "Something3",
        //     "Answer4": "Something4",
        //   }
        // ]
        
        while(questions.hasNext()) {
          Entity question = questions.next();
          System.err.println(question.getString("QuestionText"));
          System.err.println(question.getString("Answer1"));
          System.err.println(question.getString("Answer2"));
          System.err.println(question.getString("Answer3"));
          System.err.println(question.getString("Answer4"));
        }

        // {
        //   "UUID": "KHSDF*OIUHSDF)*97yh234lkjhsdf89u",
        //   "Answers": [
        //     "LikesCake",
        //     "LikesBeaches",
        //   ]
        // }

        List<String> answers;

        ListValue.Builder builder = ListValue.newBuilder();
        for (String str : answers) {
          builder.addValue(str);
        }
        // Save to datastore
        Key taskKey = datastore.newKeyFactory()
            .setKind("UserAnswers")
            .newKey("SomeUUIDFromJavaScript");
        Entity toSave = Entity.newBuilder(taskKey).set("AllAnswers", builder.build()).build();
       
        datastore.put(toSave);

        // Get From Datastore

        // "UserAnswers": [ <--- userAnswers
        //   "UserId1": { // UUID from JavaScript 
        //     "AllAnswers": ["LikesCake", "LikesBeaches"]
        //   },
        //   "UserId2": { // UUID from JavaScript
        //     "AllAnswers": ["LikesCake", "LikesBeaches"]
        //   }
        // ]

        Query<Entity> userQuery = Query.newEntityQueryBuilder().setKind("UserAnswers").build();
        QueryResults<Entity> userAnswers = datastore.run(userQuery);

        while (userAnswers.hasNext()) {
          Entity answers = userAnswers.next();
          for (Value value : answers.getList("AllAnswers")) {
            StringValue strValue = (StringValue) value;
            System.err.println(strValue.get());
          }
        }

        // Entity task = datastore.get(datastore.newKeyFactory().setKind("Questions").newKey("Mytest"));
        
    //     LocalDatastoreHelper HELPER = LocalDatastoreHelper.create(1.0);
        
    //     KeyFactory keyFactory;
    //     Datastore datastore;
    //     datastore = HELPER.getOptions().toBuilder().setNamespace("ghijklmnop").build().getService();
    //     keyFactory = datastore.newKeyFactory().setKind("Task");
   
    //     Key taskKey = keyFactory.newKey(0);
    //     Key taskKey1 = keyFactory.newKey(1);
    //     Key taskKey2 = keyFactory.newKey(2);
    //     Key taskKey3 = keyFactory.newKey(3);
    //     Key taskKey4 = keyFactory.newKey(4);
    //     Key taskKey5 = keyFactory.newKey(5);
    //     Key taskKey6 = keyFactory.newKey(6);
    // // [START datastore_batch_lookup]
    //     Iterator<Entity> tasks = datastore.get(taskKey, taskKey1, taskKey2, taskKey3, 
    //                                            taskKey4, taskKey5, taskKey6);

        // Convert the quiz question to JSON
        
        String json = "{}";

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