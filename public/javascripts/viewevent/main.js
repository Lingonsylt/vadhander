
function editDescription() {
    div = document.getElementById("description-div");
    editButton = document.getElementById("edit-description-button");
    paragraph = document.getElementById("description");

    if(paragraph.innerHTML != "Det finns ingen beskrivning till eventet."){
    var paragraphText = document.createTextNode(paragraph.innerHTML);
    }
    else{
    paragraphText = document.createTextNode("");
    }



var textArea = document.createElement("TEXTAREA");
textArea.appendChild(paragraphText);
textArea.setAttribute("rows", "8");
textArea.setAttribute("id", "edit-description-text-area");
div.appendChild(textArea);

editButton.setAttribute("value", "Spara");
editButton.setAttribute("onclick", "saveDescription()");

paragraph.parentNode.removeChild(paragraph);
}

function saveDescription() {
    div = document.getElementById("description-div");
    saveButton = document.getElementById("edit-description-button");
    textArea = document.getElementById("edit-description-text-area");

    if(textArea.value != ""){
    var textAreaText = document.createTextNode(textArea.value);
    }
else {
    textAreaText = document.createTextNode("Det finns ingen beskrivning till eventet.");
    }

var paragraph = document.createElement("p");
paragraph.appendChild(textAreaText);

//How to set event.description to be the input of the textArea?

paragraph.setAttribute("id", "description");
div.appendChild(paragraph);

saveButton.setAttribute("value", "Redigera");
saveButton.setAttribute("onclick", "editDescription()");

textArea.parentNode.removeChild(textArea);
}



function editRoadDescription() {
    div = document.getElementById("road-description-div");
    editButton = document.getElementById("edit-road-description-button");
    paragraph = document.getElementById("road-description");

    if(paragraph.innerHTML != "Det finns ingen vägbeskrivning till eventet."){
    var paragraphText = document.createTextNode(paragraph.innerHTML);
    }
else{
    paragraphText = document.createTextNode("");
    }



var textArea = document.createElement("TEXTAREA");
textArea.appendChild(paragraphText);
textArea.setAttribute("rows", "8");
textArea.setAttribute("id", "edit-road-description-text-area");
div.appendChild(textArea);

editButton.setAttribute("value", "Spara");
editButton.setAttribute("onclick", "saveRoadDescription()");

paragraph.parentNode.removeChild(paragraph);
}

function saveRoadDescription() {
    div = document.getElementById("road-description-div");
    saveButton = document.getElementById("edit-road-description-button");
    textArea = document.getElementById("edit-road-description-text-area");

    if(textArea.value != ""){
    var textAreaText = document.createTextNode(textArea.value);
    }
else {
    textAreaText = document.createTextNode("Det finns ingen vägbeskrivning till eventet.");
    }

var paragraph = document.createElement("p");
paragraph.appendChild(textAreaText);

//How to set event.description to be the input of the textArea?

paragraph.setAttribute("id", "road-description");
div.appendChild(paragraph);

saveButton.setAttribute("value", "Redigera");
saveButton.setAttribute("onclick", "editRoadDescription()");

textArea.parentNode.removeChild(textArea);
}
