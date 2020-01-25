document.addEventListener("DOMContentLoaded", function(event) {

    const button_odds = document.getElementsByTagName("button");
    for (let i = 0; i < button_odds.length; i++) {
        const but = button_odds[i];

        console.dir(but);
        if (but.name.includes("odd_button_", 0)) {
            button_odds[i].addEventListener("click", function () {

                const betSlipContent = document.getElementsByClassName("betslip-content")[0];
                const betDiv = document.createElement("div");

                const betTable = tableBetCreate();
                betDiv.appendChild(betTable);
                betSlipContent.appendChild(betDiv);
            });
        }
    }

});

function tableBetCreate() {
    var tbl = document.createElement("table");
    tbl.style.width = "90%";
    tbl.setAttribute("border", "1");

    var tblBody = document.createElement("tbody");
    for (var i = 0; i < 2; i++) {
        var tr = document.createElement("tr");
        for (var j = 0; j < 2; j++) {
            var td = document.createElement("td");
            td.appendChild(document.createTextNode("test " + i + "-" + j));
            tr.appendChild(td);
        }
        tblBody.appendChild(tr)
    }
    tbl.appendChild(tblBody);
    return tbl;
}

