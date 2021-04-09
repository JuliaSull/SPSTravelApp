package com.google.sps.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.sps.data.Destination;
import com.google.sps.data.UserAnswers;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.Value;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Handles requests sent to the /hello URL. Try running a server and navigating
 * to /hello!
 */
@WebServlet("/getDestinationInfo")
public class DestinationInfoServlet extends HttpServlet {

    static final long serialVersionUID = 0;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // get userAnswers from datastore and match them with a destination

        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
        Query<Entity> query = Query.newEntityQueryBuilder().setKind("UserAnswers").build();
        QueryResults<Entity> results = datastore.run(query);

        List<UserAnswers> userAnswers = new ArrayList<>();
        while (results.hasNext()) {
            Entity entity = results.next();

            long id = entity.getKey().getId();
            List<Value<String>> allAnswers = entity.getList("AllAnswers");

            UserAnswers answer = new UserAnswers(id, allAnswers);
            userAnswers.add(answer);
        }

        // Hard-coded destination info
        Destination newDestination = new Destination("Hawaii","$$$","beach");

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