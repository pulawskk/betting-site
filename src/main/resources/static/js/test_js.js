document.addEventListener("DOMContentLoaded", function(event) {

    const button_odds = document.getElementsByTagName("button");
    for (let i = 0; i < button_odds.length; i++) {
        button_odds[i].addEventListener("click", function () {

            const betSlipContent = document.getElementsByClassName("betslip-content")[0];
            const betDiv = document.createElement("div");
            betDiv.innerText = "test";
            betSlipContent.appendChild(betDiv);
        })
    }


});

