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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet responsible for creating new tasks. */
@WebServlet("/new-task")
public class FirstNewTaskServlet extends HttpServlet {
    static final long serialVersionUID = 0;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String[] questions = new String[7];
        questions[0] = "Which of these would you most like to do?";
        questions[1] = "What is your favorite food?";
        questions[2] = "How active do you like to be?";
        questions[3] = "What do you value most?";
        questions[4] = "How long will your trip be?";
        questions[5] = "How much are you willing to spend?";
        questions[6] = "Will you bring children? If so, how many?";

        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
        
        Key taskKey = datastore.newKeyFactory()
            .setKind("Quiz")
            .newKey(0);
        Entity task = Entity.newBuilder(taskKey)
            .set("question", questions[0])
            .set("answers", ListValue.of("Go on a hike in nature", "Take a trip downtown",
                "Go to a museum", "Take a day for relaxation"))
            .build();
        datastore.put(task);
            
        Key taskKey1 = datastore.newKeyFactory()
            .setKind("Quiz")
            .newKey(1);
        Entity task1 = Entity.newBuilder(taskKey1)
            .set("question", questions[1])
            .set("answers", ListValue.of("Fruit", "Burgers", "Wraps", "Steak"))
            .build();
            datastore.put(task1);

        Key taskKey2 = datastore.newKeyFactory()
            .setKind("Quiz")
            .newKey(2);
        Entity task2 = Entity.newBuilder(taskKey2)
            .set("question", questions[2])
            .set("answers", ListValue.of("Very much", "Much", "Not much", "Not at all"))
            .build();
            datastore.put(task2);

        Key taskKey3 = datastore.newKeyFactory()
            .setKind("Quiz")
            .newKey(3);
        Entity task3 = Entity.newBuilder(taskKey3)
            .set("question", questions[3])
            .set("answers", ListValue.of("Learning new thigs", "Physical activity", "Beautiful scenery", "New experiences"))
            .build();
            datastore.put(task3);

        Key taskKey4 = datastore.newKeyFactory()
            .setKind("Quiz")
            .newKey(4);
        Entity task4 = Entity.newBuilder(taskKey4)
            .set("question", questions[4])
            .set("answers", ListValue.of("2+ weeks", "1 week", "Less than a week", "One day"))
            .build();
            datastore.put(task4);
        
        Key taskKey5 = datastore.newKeyFactory()
            .setKind("Quiz")
            .newKey(5);
        Entity task5 = Entity.newBuilder(taskKey5)
            .set("question", questions[5])
            .set("answers", ListValue.of("$$$$", "$$$", "$$", "$"))
            .build();
            datastore.put(task5);

        Key taskKey6 = datastore.newKeyFactory()
            .setKind("Quiz")
            .newKey(6);
        Entity task6 = Entity.newBuilder(taskKey6)
            .set("question", questions[6])
            .set("answers", ListValue.of("No", "1", "2-4", "4+"))
            .build();
            datastore.put(task6);


    response.sendRedirect("/getQuizQuestions");
  }
}