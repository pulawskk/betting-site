document.addEventListener("DOMContentLoaded", function(event) {
    const button = document.getElementsByClassName("test_button")[0];
    button.addEventListener("click", myFunction);
    function myFunction() {
        alert ("Hello World!" + text.innerText);
    }

    const text = document.getElementsByName("test_area")[0];

    function addOddToListening() {

        const button_odds = document.getElementsByTagName("button");
        console.dir(button_odds);
        for (let i = 0; i < button_odds.length; i++) {
            button_odds[i].addEventListener("click", function () {
                alert(button_odds[i].innerText)
            })
        }
    }

    window.addEventListener("load", function () {
        addOddToListening();
    });

});

