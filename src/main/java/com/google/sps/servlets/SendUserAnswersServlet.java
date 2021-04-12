// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.ListValue;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;

import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet responsible for creating new tasks. */
@WebServlet("/sendUserAnswers")
@MultipartConfig
public class SendUserAnswersServlet extends HttpServlet {

    static final long serialVersionUID = 0;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        String answers = request.getParameter("responses");
        System.err.println(answers);
        // final JSONObject obj = new JSONObject(answers);
        // System.err.println("yo");
        // final JSONArray data = obj.getJSONArray("responses");
        // System.err.println("yo");
        // ArrayList<String> list = new ArrayList<>();
        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

        ListValue.Builder builder = ListValue.newBuilder();
        System.err.println("yo");
        if (data != null) { 
            for (int i=0;i<data.length();i++){ 
                list.add(data.getString(i));
                builder.addValue(data.getString(i));
            } 
        } 
        System.err.println("yo");

        // Save to datastore
        Key taskKey = datastore.newKeyFactory().setKind("UserAnswers").newKey(request.getParameter("userId"));
        Entity toSave = Entity.newBuilder(taskKey).set("AllAnswers", builder.build()).build();

        datastore.put(toSave);
    }
}