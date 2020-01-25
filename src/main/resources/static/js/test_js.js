document.addEventListener("DOMContentLoaded", function(event) {

    const button_odds = document.getElementsByTagName("button");
    for (let i = 0; i < button_odds.length; i++) {
        const but = button_odds[i];

        console.dir(but);
        if (but.name.includes("odd_button_", 0)) {
            button_odds[i].addEventListener("click", function () {

                const betSlipContent = document.getElementsByClassName("betslip-content")[0];
                const betDiv = document.createElement("div");
                betDiv.innerText = "test";
                betSlipContent.appendChild(betDiv);
            });
        }
    }

});

