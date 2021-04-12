package com.google.sps.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.sps.data.Destination;
import com.google.sps.data.UserAnswers;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.Value;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;


@WebServlet("/getDestinationInfo")
@MultipartConfig
public class DestinationInfoServlet extends HttpServlet {

    static final long serialVersionUID = 0;
    private KeyFactory keyFactory;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userID = request.getParameter("userId");
        System.err.println("/getDestinationInfo:" + userID);

        // get userAnswers from datastore
        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
        Query<Entity>query = Query.newEntityQueryBuilder()
            .setKind("UserAnswers").build();
        QueryResults<Entity>results = datastore.run(query);

        List < UserAnswers > userAnswers = new ArrayList<>();
        while (results.hasNext()) {
            Entity entity = results.next();
            if (it is the right one) {
              long id = entity.getKey().getId();
              List < Value < String >> allAnswers = entity.getList("AllAnswers");

              UserAnswers answer = new UserAnswers(id, allAnswers);
              userAnswers.add(answer);
              break;
            }
            
        }
        System.err.println("getDestinationInfo6");


        // get destination info from datastore
        Query < Entity > destinationQuery = Query.newEntityQueryBuilder().setKind("Destinations").build();
        QueryResults < Entity > destinationResults = datastore.run(destinationQuery);

        List < Destination > destinations = new ArrayList < > ();
        while (destinationResults.hasNext()) {
            Entity entity = destinationResults.next();

            String name = entity.getString("name");
            String overallExpense = entity.getString("overallExpense");
            String type = entity.getString("type");
            String language = entity.getString("language");
            String currency = entity.getString("currency");
            List < Value < String >> keywords = entity.getList("keywords");
            List < Value < String >> food = entity.getList("food");

            Destination destination = new Destination(name, overallExpense, type, language, currency, keywords, food);
            destinations.add(destination);
        }

        boolean found = false;
        // default hard-coded destination in case no destination is found
        Destination newDestination = new Destination("Hawaii", "$$$", "beach", "English", "U.S. Dollar");

        //match responses with destination
        for (UserAnswers answers: userAnswers) {
            List < Value < String >> answerKeywords = answers.getAllAnswers();
            for (Destination d: destinations) {
                List < Value < String >> destinationKeywords = d.getKeywords();
                for (Value < String > keyword: answerKeywords) {
                    if (destinationKeywords.contains(keyword)) {
                        newDestination = d;
                        found = true;
                        break;
                    }
                }
                if (found) break;
            }
            if (found) break;
        }

        // Convert the server stats to JSON
        String json = convertToJsonUsingGson(newDestination);

        // Send the JSON as the response
        response.setContentType("application/json;");
        response.getWriter().println(json);
    }

    /**
     * Converts a ServerStats instance into a JSON string using the Gson library.
     */
    private String convertToJsonUsingGson(Destination obj) {
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        return json;
    }
}