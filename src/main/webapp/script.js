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
let resultObject;
let results;
let resultKeys;
let userID;

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

    const quiz = document.getElementById(QUIZ_ELEMENT_ID);
    const question = quizObject[0];

    quiz.appendChild(createParagraphElement(question.question));
    for (let answer of question.answers) {
        let button = createButton(answer);
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
    const question = quizObject[quizIndex];

    quiz.appendChild(createParagraphElement(question.question));
    for (let answer of question.answers) {
        let button = createButton(answer);
        button.classList.add("nextButton");
        if (quizIndex == quizObject.length - 1) {            
            button.addEventListener("click", function() {
              saveMatch(this);
            });
        } else {
            button.addEventListener("click", function() {
                onClick(this);
            });
        }
        quiz.appendChild(button);
    }
}

async function saveMatch(elm) {
    responses.push(elm.value);
    userID = create_UUID();
    clearElm(QUIZ_ELEMENT_ID);

    let jsonResponses = {
      responses: responses,
    };

    //to sendUsers
    var data = new FormData();
    data.append('responses', JSON.stringify(jsonResponses));
    data.append('userId', userID);

    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'sendUserAnswers');
    xhr.onload = function(event) {
      displayUserResult(elm);
    };
    xhr.send(data);
}

function displayUserResult() {
    //to destinationInfo
    var data = new FormData();
    data.append('userId', userID);

    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'getDestinationInfo');
    xhr.send(data);
    xhr.onload = function(event) {
      clearElm(QUIZ_ELEMENT_ID);
      const quiz = document.getElementById(QUIZ_ELEMENT_ID);
      quiz.appendChild(createParagraphElement("Based On Your Results, You Should visit..."));
      displayResults(xhr.response);
    };
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

function create_UUID(){
    var dt = new Date().getTime();
    var uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        var r = (dt + Math.random()*16)%16 | 0;
        dt = Math.floor(dt/16);
        return (c=='x' ? r :(r&0x3|0x8)).toString(16);
    });
    return uuid;
}

async function displayResults(placeJsonData) {

  //   jsonPlaces = '{ "Places" : [' +
  //  '{ "Place":"Paris" , "Currency":"Euro","Language":"Parisian French","Price":"$$$","Food":["Caramels", "Baguette", "Pain Au chocolat", "Pastries", "Chocolate", "Macarons", "Cheese from Laurent Dubois", "Cr??me Br??l??e ", "??clair", "Croissants" ]},' +
  //       '{ "Place":"New York" , "Currency":"US Dollar","Language":"English","Price":"$$$","Food":["Pizza", "Bagels", "Burgers", "Sandwiches", "Ramen", "Food Trucks", "Cheesecake" ]},' +
  //       '{ "Place":"Hawaii" , "Currency":"US Dollar","Language":"English, Creole, and Hawaiian Pidgin","Price":"$$","Food":["All-Natural Shave Ice", "Saimin", "Poke", "Luau Stew", "Manapua", "Fish Tacos", "Huli Huli Chicken", "Loco Moco", "Malasadas"]},' +
  //       '{ "Place":"Cape Town" , "Currency":"South African Rand","Language":"Afrikaans","Price":"$","Food":["Fish and Chips", "Game Meat", "Gatsby", "Bunny Chow","Bobotie", "Biltong and Dro??wors", "Malva Pudding" , "Koeksister"] } ]}';
    resultObject = JSON.parse(placeJsonData);
    var h = document.createElement("H3");
    let i = 2;
    h.appendChild(document.createTextNode(resultObject.name));
    document.body.appendChild(h);
    document.body.appendChild(createParagraphElement("Currency: "+resultObject.currency));
    document.body.appendChild(createParagraphElement("Language: "+resultObject.language));
    document.body.appendChild(createParagraphElement("Price: "+resultObject.overallExpense));
    document.body.appendChild(createParagraphElement("Food to try: ")); 
    for (var j = 0; j < resultObject.food.length; j++) {
       document.body.appendChild(createParagraphElement(resultObject.food[j]));
    
    }
    
}