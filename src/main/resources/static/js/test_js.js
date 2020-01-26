document.addEventListener("DOMContentLoaded", function(event) {

    let betCounter = 0;
    let betStakeCounter = 1;
    const button_odds = document.getElementsByTagName("button");
    for (let i = 0; i < button_odds.length; i++) {

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
                const oddValue = but.getAttribute("data-el_selection");
                betStakeCounter = betStakeCounter * Number(oddValue);

                if(betCounter > 0) {
                    const betSummarizeContentOld = document.getElementsByClassName("betslip-summarize")[0];
                    const betSummarizeContent = betSummarizeContentOld.cloneNode(true);

                    betSummarizeContent.innerText = "bet summarize";
                    betSummarizeContent.style.height = "50px";

                    const betSummarizeTable = tableBetSummarizeCreate(betCounter, betStakeCounter);
                    betSummarizeContent.appendChild(betSummarizeTable);

                    const betPlacementButtons = tableBetPlacementCreate();
                    betSummarizeContent.appendChild(betPlacementButtons);

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

function tableBetSummarizeCreate(betCounter, betStakeCounter) {
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
    td01.colSpan = 2;
    td01.innerText = "chosen bets: " + betCounter + " course: " + betStakeCounter.toFixed(2);
    tr0.appendChild(td01);
    tblBody.appendChild(tr0);

    const tr1 = document.createElement("tr");
    const td11 = document.createElement("td");
    const cb1 = document.createElement("input");
    cb1.type = "checkbox";
    td11.innerText = "single:  ";
    td11.appendChild(cb1);
    td11.style.width = "30%";
    tr1.appendChild(td11);
    const td12 = document.createElement("td");
    const numberInput = document.createElement("input");
    numberInput.type = "number";
    numberInput.style.width = "50%";
    numberInput.style.height = "75%";
    numberInput.placeholder = "5.00";
    const customerStake = numberInput.value;
    td12.innerText = "stake: ";
    td12.appendChild(numberInput);
    tr1.appendChild(td12);

    tblBody.appendChild(tr1);

    const tr2 = document.createElement("tr");
    const td21 = document.createElement("td");
    const cb2 = document.createElement("input");
    cb2.type = "checkbox";
    td21.innerText = "multi:  ";
    td21.appendChild(cb2);
    tr2.appendChild(td21);
    td21.style.width = "30%";
    const td22 = document.createElement("td");
    const potentialWinDiv = document.createElement("div");
    potentialWinDiv.style.backgroundColor = "#fbfffb";

    //TODO how to keep value from input
    const potentialWinNumber = betStakeCounter * 5;

    potentialWinDiv.innerText = potentialWinNumber.toFixed(2).toString();
    td22.innerText = "potential win: ";
    td22.appendChild(potentialWinDiv);
    tr2.appendChild(td22);

    tblBody.appendChild(tr2);

    tbl.appendChild(tblBody);
    return tbl;
}

function tableBetPlacementCreate() {
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
    const bt1 = document.createElement("button");
    bt1.innerText = "PLACE BET";
    td01.style.width = "70%";
    td01.appendChild(bt1);
    tr0.appendChild(td01);

    const td02 = document.createElement("td");
    const bt2 = document.createElement("button");
    bt2.innerText = "CLEAR";
    td02.appendChild(bt2);
    tr0.appendChild(td02);

    tblBody.appendChild(tr0);
    tbl.appendChild(tblBody);
    return tbl;
}