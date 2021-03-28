package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/** Handles requests sent to the /hello URL. Try running a server and navigating to /hello! */
@WebServlet("/getDestinationInfo")
public class DestinationInfoServlet extends HttpServlet {

  static final long serialVersionUID = 0;
    class Destination {
        String name;
        String overallExpense;
        String type;

        public Destination(String name, String overallExpense, String type) {
            this.name = name;
            this.overallExpense = overallExpense;
            this.type = type;
        }
    }
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Destination newDestination = new Destination("Cancun","$$$","resort");

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