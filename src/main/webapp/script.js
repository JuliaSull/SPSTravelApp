// Copyright 2020 Google LLC
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
let jsonPlacehold;
let quizObject;
let quizIndex = 0;
let responses = [];
const QUIZ_ELEMENT_ID = "quiz";


function onLoad() {
    jsonPlacehold = '{ "questions" : [' +
        '{ "question":"How would you spend an afternoon?" , "options":["hiking mountains", "shopping crafts", "trying local eats", "visiting a museum"]},' +
        '{ "question":"What would you choose for breakfast?" , "options":["acai bowl", "waffles", "crossaints", "huevos rancheros", "bagels"] },' +
        '{ "question":"Pick a TV show" , "options":["Emily in Paris", "Doctor Who", "Brooklyn 99", "Itaewon Class"] } ]}';
    quizObject = JSON.parse(jsonPlacehold);
    const quiz = document.getElementById(QUIZ_ELEMENT_ID);
    quiz.appendChild(createParagraphElement(quizObject.questions[0].question));
    for (let a of quizObject.questions[0].options) {
        let button = createButton(a);
        button.addEventListener("click", function() {
            onClick(this);
        });
        quiz.appendChild(button);
    }

}

function onClick(elm) {
    responses.push(elm.value);
    quizIndex = quizIndex + 1;
    clearElm(QUIZ_ELEMENT_ID);
    const quiz = document.getElementById(QUIZ_ELEMENT_ID);
    quiz.appendChild(createParagraphElement(quizObject.questions[quizIndex].question));
    for (let a of quizObject.questions[quizIndex].options) {
        let button = createButton(a);
        if (quizIndex == quizObject.questions.length - 1) {
            button.addEventListener("click", function() {
                //saveMatch(this);
                userResult(this);
            });
        } else {
            button.addEventListener("click", function() {
                onClick(this);
            });
        }
        quiz.appendChild(button);
    }
}

function saveMatch(elm) {
    responses.push(elm.value);
    clearElm(QUIZ_ELEMENT_ID);
    const quiz = document.getElementById(QUIZ_ELEMENT_ID);
    quiz.appendChild(createParagraphElement("Make redirect to destination match"));
    quiz.appendChild(createParagraphElement(responses.toString()));
}

/** Creates an <p> element containing text. */
function createParagraphElement(text) {
    const pElement = document.createElement('p');
    pElement.innerText = text;
    return pElement;
}

function createButton(text) {
    let button = document.createElement('INPUT');
    button.setAttribute("type", "button");
    button.innerHTML = text;
    button.setAttribute("value", text);
    return button;
}

function clearElm(elementID) {
    document.getElementById(elementID).innerHTML = "";
}

function userResult(){
   
    clearElm(QUIZ_ELEMENT_ID);
    const quiz = document.getElementById(QUIZ_ELEMENT_ID);
    quiz.appendChild(createParagraphElement("Based On Your Results You Should visit..."));
   
    getHeader(this);
}

function getHeader(){
    var h = document.createElement("H3");
    var t = document.createTextNode("Paris!");
     h.appendChild(t);
    document.body.appendChild(h);
}
