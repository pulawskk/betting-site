document.addEventListener("DOMContentLoaded", function(event) {

    const button_odds = document.getElementsByTagName("button");
    for (let i = 0; i < button_odds.length; i++) {
        let betCounter = 0;

        const but = button_odds[i];

        if (but.name.includes("odd_button_", 0)) {
            but.addEventListener("click", function () {

                const rightContentLayout = document.getElementsByClassName("right-content-layout")[0];
                const betSlipContent = document.getElementsByClassName("betslip-content")[0];
                const betDiv = document.createElement("div");

                const betTable = tableBetCreate(but);
                betDiv.appendChild(betTable);
                betSlipContent.appendChild(betDiv);

                betCounter++;

                if(betCounter > 0) {
                    const betSummarizeContentOld = document.getElementsByClassName("betslip-summarize")[0];
                    const betSummarizeContent = betSummarizeContentOld.cloneNode(true);


                    betSummarizeContent.innerText = "bet summarize";
                    betSummarizeContent.style.height = "50px";

                    const betSummarizeTable = tableBetSummarizeCreate();
                    betSummarizeContent.appendChild(betSummarizeTable);

                    rightContentLayout.replaceChild(betSummarizeContent, betSummarizeContentOld);
                }
            });
        }


    }

});

function tableBetCreate(oddButton) {
    const tbl = document.createElement("table");
    tbl.style.width = "90%";
    tbl.style.margin = "auto";
    tbl.style.marginTop = "2px";
    tbl.style.marginBottom = "2px";
    tbl.style.backgroundColor = "#a584a1";
    tbl.style.color = "#0529a5";
    tbl.setAttribute("border", "1");

    const tblBody = document.createElement("tbody");

    const tr0 = document.createElement("tr");
    const td01 = document.createElement("td");
    td01.innerText = oddButton.getAttribute("data-el_competition");
    tr0.appendChild(td01);
    const td02 = document.createElement("td");
    td02.innerText = oddButton.getAttribute("data-el_start-date");
    tr0.appendChild(td02);
    tblBody.appendChild(tr0);

    const tr1 = document.createElement("tr");
    const td11 = document.createElement("td");
    td11.innerText = oddButton.getAttribute("data-el_event-name");
    tr1.appendChild(td11);
    const td12 = document.createElement("td");
    td12.innerText = oddButton.getAttribute("data-el_market-type");
    tr1.appendChild(td12);
    tblBody.appendChild(tr1);

    const tr2 = document.createElement("tr");
    const td21 = document.createElement("td");
    td21.innerText = oddButton.getAttribute("data-el_selection");
    tr2.appendChild(td21);
    const td22 = document.createElement("td");
    td22.innerText = oddButton.getAttribute("data-el_user-type");
    tr2.appendChild(td22);
    tblBody.appendChild(tr2);

    tbl.appendChild(tblBody);
    return tbl;
}

function tableBetSummarizeCreate() {
    const tbl = document.createElement("table");
    tbl.style.width = "90%";
    tbl.style.margin = "auto";
    tbl.style.marginTop = "2px";
    tbl.style.marginBottom = "2px";
    tbl.style.backgroundColor = "#a584a1";
    tbl.style.color = "#0529a5";
    tbl.setAttribute("border", "1");

    const tblBody = document.createElement("tbody");

    const tr0 = document.createElement("tr");
    const td01 = document.createElement("td");
    td01.colSpan = 3;
    td01.innerText = "betslip summarize";
    tr0.appendChild(td01);
    tblBody.appendChild(tr0);

    const tr1 = document.createElement("tr");
    const td11 = document.createElement("td");
    td11.innerText = "A";
    tr1.appendChild(td11);
    const td12 = document.createElement("td");
    td12.innerText = "B";
    td12.colSpan = 2;
    tr1.appendChild(td12);

    tblBody.appendChild(tr1);

    const tr2 = document.createElement("tr");
    const td21 = document.createElement("td");
    td21.innerText = "A";
    tr2.appendChild(td21);
    const td22 = document.createElement("td");
    td22.colSpan = 2;
    td22.innerText = "B";
    tr2.appendChild(td22);

    tblBody.appendChild(tr2);

    const tr3 = document.createElement("tr");
    const td31 = document.createElement("td");
    td31.innerText = "A";
    tr3.appendChild(td31);
    const td32 = document.createElement("td");
    td32.innerText = "B";
    tr3.appendChild(td32);
    const td33 = document.createElement("td");
    td33.innerText = "C";
    tr3.appendChild(td33);

    tblBody.appendChild(tr3);


    tbl.appendChild(tblBody);
    return tbl;
}