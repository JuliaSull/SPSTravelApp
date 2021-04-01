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

var jsonPlacehold = '{ "questions" : [' +
'{ "q":"How would you spend an afternoon?" , "options":["hiking mountains", "shopping crafts", "trying local eats", "visiting a museum"]},' +
'{ "q":"What would you choose for breakfast?" , "options":["acai bowl", "waffles", "crossaints", "huevos rancheros", "bagels"] },' +
'{ "q":"Pick a TV show" , "options":["Emily in Paris", "Doctor Who", "Brooklyn 99", "Itaewon Class"] } ]}';
var obj = JSON.parse(jsonPlacehold);
var objIndex = 0;

function onLoad(){
    const quiz = document.getElementById('quiz');
    quiz.appendChild(createParElement(obj.questions[0].q));
    for (a in obj.questions[0].options){
        // console.log(obj.questions[0].options[a])
        var button = createButton(obj.questions[0].options[a])
        button.addEventListener("click", onClick)
        quiz.appendChild(button);
    }

}

function onClick(){
    objIndex = objIndex+1;
    // console.log(objIndex);
    clearElm("quiz");
    const quiz = document.getElementById('quiz');
    quiz.appendChild(createParElement(obj.questions[objIndex].q));
    for (a in obj.questions[objIndex].options){
        // console.log(obj.questions[0].options[a])
        var button = createButton(obj.questions[objIndex].options[a]);
        if (objIndex==obj.questions.length-1){
            button.addEventListener("click", goMatch);
        }
        else {
            button.addEventListener("click", onClick);
        }
        quiz.appendChild(button);
    }
}

function goMatch(){
    clearElm("quiz");
    const quiz = document.getElementById('quiz');
    quiz.appendChild(createParElement("Make redirect to destination match"));
}

/** Creates an <p> element containing text. */
function createParElement(text) {
    const pElement = document.createElement('p');
    pElement.innerText = text;
    return pElement;
}

// function createOption(text) {
//     var option = document.createElement('INPUT');
//     option.setAttribute("type", "radio");    
//     option.innerHTML = text;
//     return button;
// }

function createButton(text) {
    var button = document.createElement('INPUT');
    button.setAttribute("type", "button");    
    button.innerHTML = text;
    button.setAttribute("value",text);
    return button;
}

function clearElm(elementID)
{
    document.getElementById(elementID).innerHTML = "";
}