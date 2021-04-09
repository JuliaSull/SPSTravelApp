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
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.ListValue;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet responsible for creating new tasks. */
@WebServlet("/sendUserAnswers")
public class SendUserAnswers extends HttpServlet {
    
  static final long serialVersionUID = 0;

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
    
    
    String answers = request.getParameter("responses");
   
    Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

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
  }
}