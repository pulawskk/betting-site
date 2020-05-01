document.addEventListener("DOMContentLoaded", function(event) {
    if (sessionStorage.getItem("betslip-content-session")) {
        const betSlipFromSession = sessionStorage.getItem("betslip-content-session");
        const betSummarizeFromSession = sessionStorage.getItem("betslip-summarize-session");

        if (betSlipFromSession !== "null") {
            // trim the very first div
            // first '>' sign is on position:
            const startTrimPosition = betSlipFromSession.indexOf(">", 0);
            //last '<' sign is on position:
            const endTrimPosition = betSlipFromSession.indexOf("<", betSlipFromSession.length - 8);
            const betSlipFromSessionTrimmed = betSlipFromSession.substring(startTrimPosition + 1, endTrimPosition);

            const startTrimPosition2 = betSummarizeFromSession.indexOf(">", 0);
            const endTrimPosition2 = betSummarizeFromSession.indexOf("<", betSummarizeFromSession.length - 8);
            const betSummarizeFromSessionTrimmed = betSummarizeFromSession.substring(startTrimPosition2 + 1, endTrimPosition2);
            const newBetSlipContent = document.getElementsByClassName("betslip-content")[0];
            const newBetSlipSummarize = document.getElementsByClassName("betslip-summarize")[0];

            newBetSlipContent.innerHTML = betSlipFromSessionTrimmed;
            newBetSlipSummarize.innerHTML = betSummarizeFromSessionTrimmed;

            const placeBetButton = document.getElementsByName("bet-place-button")[0];

            placeBetButton.addEventListener("click", function () {
                placeBetSlip();
            });

            const clearButton = document.getElementsByName("bet-clear-button")[0];
            clearButton.addEventListener("click", function () {
                betStakeCounter = 1;
                clearBetSlip();
            });
        }
    }

    let betCounter = 0;
    let betStakeCounter = 1;
    const button_odds = document.getElementsByTagName("button");

    for (let i = 0; i < button_odds.length; i++) {

        const but = button_odds[i];

        if (but.name.includes("odd_button_", 0)) {
            but.addEventListener("click", function () {

                var rightContentLayoutTemplate = null;
                const rightContentLayout = document.getElementsByClassName("right-content-layout")[0];

                var betSlipContent = document.getElementsByClassName("betslip-content")[0];

                const betDiv = document.createElement("div");
                betDiv.className = "bet-chosen-content";

                const betTable = tableBetCreate(but);
                betDiv.appendChild(betTable);

                betSlipContent.appendChild(betDiv);

                var betSummarizeContentOld = document.getElementsByClassName("betslip-summarize")[0];

                var betSummarizeContent = betSummarizeContentOld.cloneNode(true);

                if (betSummarizeContent.childElementCount.valueOf() === 0) {
                    rightContentLayoutTemplate = rightContentLayout.outerHTML;
                    betCounter = 1;

                    const oddValue = but.getAttribute("data-el_selection");
                    betStakeCounter = betStakeCounter * Number(oddValue);

                    const betSummarizeTitle = document.createElement("h5");
                    betSummarizeTitle.innerText = "bet summarize";
                    betSummarizeContent.appendChild(betSummarizeTitle);

                    const betSummarizeTable = tableBetSummarizeCreate(betCounter, betStakeCounter);

                    betSummarizeContent.appendChild(betSummarizeTable);
                    const betPlacementButtons = tableBetPlacementCreate();

                    betSummarizeContent.appendChild(betPlacementButtons);
                } else {
                    betCounter = parseInt(sessionStorage.getItem("betslip-bet-counter"));
                    betStakeCounter = parseFloat(sessionStorage.getItem("betslip-bet-stakeCounter"));

                    betCounter++;
                    const oddValue = but.getAttribute("data-el_selection");
                    betStakeCounter = betStakeCounter * Number(oddValue);

                    const betSummarizeTable = tableBetSummarizeCreate(betCounter, betStakeCounter);

                    betSummarizeContent.replaceChild(betSummarizeTable, betSummarizeContent.children[1]);
                    const betPlacementButtons = tableBetPlacementCreate();
                    betSummarizeContent.replaceChild(betPlacementButtons, betSummarizeContent.lastChild);
                }

                rightContentLayout.replaceChild(betSummarizeContent, betSummarizeContentOld);

                const betSlipContentChosen = document.getElementsByClassName("betslip-content");
                const betsChosen = betSlipContentChosen[0].getElementsByClassName("bet-chosen-content");

                watchCustomerStakeInput(betStakeCounter);

                //save session
                if (sessionStorage) {
                    sessionStorage.setItem("betslip-content-session", betSlipContentChosen[0].outerHTML);
                    sessionStorage.setItem("betslip-summarize-session", betSummarizeContent.outerHTML);
                    sessionStorage.setItem("betslip-bet-counter", betCounter.toString());
                    sessionStorage.setItem("betslip-bet-stakeCounter", betStakeCounter.toString());
                }

                const clearButton = document.getElementsByName("bet-clear-button")[0];
                clearButton.addEventListener("click", function () {
                    betStakeCounter = 1;
                    clearBetSlip();
                });

                const placeBetButton = document.getElementsByName("bet-place-button")[0];

                placeBetButton.addEventListener("click", function () {
                    placeBetSlip();
                    betStakeCounter = 1;
                });
            });
        }
    }
});

function placeBetSlip() {
    let competition_cell = null;
    let event_time_cell = null;
    let event_name_cell = null;
    let market_type_cell = null;
    let odd_value_cell = null;
    let user_type_cell = null;
    let betSlipType = null;

    let placedStake = document.getElementById("customer-stake").value;

    console.dir(placedStake.length);

    if (placedStake.length === 0) {
        placedStake = '5';
    }

    const betsChosen = document.getElementsByClassName("bet-chosen-content");

    for (let i = 0; i < betsChosen.length; i++) {
        const betChosenTable = betsChosen[i].firstElementChild;

        const uniqueId = betChosenTable.getAttribute("event_id");

        const tableBody = betChosenTable.firstChild;
        const tableRows = tableBody.childNodes;

        competition_cell = tableRows[0].firstChild.firstChild.nodeValue;
        event_time_cell = tableRows[0].lastChild.firstChild.nodeValue;
        event_name_cell = tableRows[1].firstChild.firstChild.nodeValue;
        market_type_cell = tableRows[1].lastChild.firstChild.nodeValue;
        odd_value_cell = tableRows[2].firstChild.firstChild.nodeValue;
        user_type_cell = tableRows[2].lastChild.firstChild.nodeValue;

        betSlipType = betsChosen.length > 1 ? "MULTIPLE" : "SINGLE";
        const selection = "                    \"bet\" :\n" +
            "                        {\n" +
            "                            \"uniqueId\" : \"test123\",\n" +
            "                            \"userType\": \"test123\"\n" +
            "                        }";
        console.dir("id: " + i + " | " + uniqueId + " | " + competition_cell + " | " + event_time_cell + " | " + event_name_cell + " | " + market_type_cell + " | " + odd_value_cell + " | " + user_type_cell);
    }

    var data = '{"stake":" ' + placedStake + ' ","types":["' + betSlipType + '"]}';

    $.ajax({
        type: "POST",
        url: "http://localhost:8081/settler/post",
        data: data,
        contentType: "application/json",
    });

    //clear betStakeCounter
    betStakeCounter = 1;
    clearBetSlip();
}

function clearBetSlip() {
    sessionStorage.setItem("betslip-content-session", null);
    sessionStorage.setItem("betslip-summarize-session", null);
    sessionStorage.setItem("betslip-bet-counter", null);
    sessionStorage.setItem("betslip-bet-stakeCounter", null);

    var betSlipContentToDelete = document.getElementsByClassName("betslip-content")[0];
    while (betSlipContentToDelete.children.length > 1) {
        betSlipContentToDelete.lastChild.remove();
    }

    var betSlipSummarizeToDelete = document.getElementsByClassName("betslip-summarize")[0];
    while (betSlipSummarizeToDelete.children.length > 0) {
        betSlipSummarizeToDelete.firstChild.remove();
    }
}

function tableBetCreate(oddButton) {
    const tbl = document.createElement("table");
    tbl.style.width = "90%";
    tbl.style.margin = "auto";
    tbl.style.marginTop = "2px";
    tbl.style.marginBottom = "2px";
    tbl.style.backgroundColor = "#a584a1";
    tbl.style.color = "#0529a5";
    tbl.setAttribute("border", "1");
    tbl.setAttribute("name", "123");
    tbl.setAttribute("event_id", oddButton.getAttribute("data-el_event-id"));

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
    numberInput.name = "customer-stake";
    numberInput.id = "customer-stake";
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
    potentialWinDiv.setAttribute("name", "potential-win-div");

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
    bt1.name = "bet-place-button";
    td01.style.width = "70%";
    td01.appendChild(bt1);
    tr0.appendChild(td01);

    const td02 = document.createElement("td");
    const bt2 = document.createElement("button");
    bt2.innerText = "CLEAR";
    bt2.name = "bet-clear-button";
    td02.appendChild(bt2);
    tr0.appendChild(td02);

    tblBody.appendChild(tr0);
    tbl.appendChild(tblBody);
    return tbl;
}

function watchCustomerStakeInput(betStakeCounter) {
    // get input element
    const customerStakeValue = document.getElementsByName("customer-stake")[0];

    customerStakeValue.addEventListener("input", updateCustomerStakeValue);

    function updateCustomerStakeValue(e) {
        const numGetFromInput = e.target.value;

        // get potential win div
        const currentPotentialWinDiv = document.getElementsByName("potential-win-div")[0];
        const newPotentialWin = betStakeCounter * numGetFromInput;
        currentPotentialWinDiv.innerText = newPotentialWin.toFixed(2).toString();
    }
}

