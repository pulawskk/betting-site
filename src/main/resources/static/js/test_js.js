document.addEventListener("DOMContentLoaded", function(event) {

    const buttonGetDataFromSession = document.getElementsByClassName("get-session-data")[0];
    if (buttonGetDataFromSession != null) {
        buttonGetDataFromSession.addEventListener("click", function () {
            if (sessionStorage) {
                const dataFromSession = sessionStorage.getItem("betslip-content-session");

                //trim the very first div

                //first '>' sign is on position:
                const startTrimPosition = dataFromSession.indexOf(">", 0);
                //last '<' sign is on position:
                const endTrimPosition = dataFromSession.indexOf("<", dataFromSession.length - 8);
                console.dir("start: " + startTrimPosition + " end: " + endTrimPosition);

                const dataFromSessionTrimmed = dataFromSession.substring(startTrimPosition + 1, endTrimPosition);

                const newBetSlipContent = document.getElementsByClassName("betslip-content")[0];
                newBetSlipContent.innerHTML = dataFromSessionTrimmed;

                console.dir(dataFromSessionTrimmed);
                alert(dataFromSessionTrimmed);
            }
        })
    }

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
                betDiv.className = "bet-chosen-content";

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

                    watchCustomerStakeInput(betStakeCounter);

                    const placeBetButton = document.getElementsByName("bet-place-button")[0];

                    placeBetButton.addEventListener("click", function () {
                        const betSlipContentChosen = document.getElementsByClassName("betslip-content");
                        const betsChosen = betSlipContentChosen[0].getElementsByClassName("bet-chosen-content");

                        //session save
                        const lastAccess = new Date().getTime();
                        if (sessionStorage) {
                            sessionStorage.setItem("betslip-content-session", betSlipContentChosen[0].outerHTML);
                        }

                        let competition_cell = null;
                        let event_time_cell = null;
                        let event_name_cell = null;
                        let market_type_cell = null;
                        let odd_value_cell = null;
                        let user_type_cell = null;
                        let betSlipType = null;

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

                            betSlipType = betsChosen.length > 1 ? "multi" : "single";

                            console.dir("id: " + i + " | " + uniqueId + " | " + competition_cell + " | " + event_time_cell + " | " + event_name_cell + " | " + market_type_cell + " | " + odd_value_cell + " | " + user_type_cell);
                        }


                        $.post("http://localhost:8081/after", { json_string:JSON.stringify(
                                {
                                    "betslipType": "test123",
                                    "bets" :
                                        {
                                            "bet" :
                                                {
                                                    "uniqueId" : "test123",
                                                    "userType": "test123"
                                                }
                                        }
                                }
                            )});

                        // $.ajax({
                        //     type: "POST",
                        //     url: "http://localhost:8081/after",
                        //     data: {
                        //         "betslipType": betSlipType.toString(),
                        //         "bets" :
                        //             {
                        //                 "bet" :
                        //                     {
                        //                         "uniqueId" : uniqueId.toString(),
                        //                         "userType": user_type_cell.toString()
                        //                     }
                        //             }
                        //     },
                        //     success: function (msg) {
                        //         alert("wow" + msg);
                        //     }
                        //
                        // });
                    })

                    const time = sessionStorage.getItem("myapp_time");
                    alert(time);
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

