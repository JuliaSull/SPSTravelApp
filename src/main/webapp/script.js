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
let quizKeys;
let quizIndex = 0;
let responses = [];
const QUIZ_ELEMENT_ID = "quiz";

// What should be fetched from /getQuizQuestion
// {
// 	"quiz": {
// 		"How much are you willing to spend?": ["$$$$", "$$$", "$$", "$"],
// 		"What is your favorite food?": ["Fruit", "Burgers", "Wraps", "Steak"],
// 		"What do you value most?": ["Learning new thigs", "Physical activity", "Beautiful scenery", "New experiences"],
// 		"Which of these would you most like to do?": ["Go on a hike in nature", "Take a trip downtown", "Go to a museum", "Take a day for relaxation"],
// 		"How active do you like to be?": ["Very much", "Much", "Not much", "Not at all"],
// 		"Will you bring children? If so, how many?": ["No", "1", "2-4", "4+"],
// 		"How long will your trip be?": ["2+ weeks", "1 week", "Less than a week", "One day"]
// 	}
// }

async function onLoad() {    
    quizObject = await fetch('/getQuizQuestions').then(a=>a.json());
    quizKeys = Object.keys(quizObject.quiz);
    
    const quiz = document.getElementById(QUIZ_ELEMENT_ID);
    const question = quizKeys[0];

    quiz.appendChild(createParagraphElement(question));
    for (let a of quizObject.quiz[question]) {
        let button = createButton(a);
        button.classList.add("nextButton");
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
    const question = quizKeys[quizIndex];

    quiz.appendChild(createParagraphElement(question));
    for (let a of quizObject.quiz[question]) {
        let button = createButton(a);
        button.classList.add("nextButton");
        if (quizIndex == quizKeys.length - 1) {            
            button.addEventListener("click", function() {
                saveMatch(this);
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

function userResult() {
    clearElm(QUIZ_ELEMENT_ID);
    const quiz = document.getElementById(QUIZ_ELEMENT_ID);
    quiz.appendChild(createParagraphElement("Based On Your Results, You Should visit..."));
    displayResults(this);
}

function displayResults() {
    var h = document.createElement("H3");
    h.appendChild(document.createTextNode("Paris!"));
    document.body.appendChild(h);
}