document.addEventListener("DOMContentLoaded", function(event) {
    const button = document.getElementsByClassName("test_button")[0];
    const text = document.getElementsByName("test_area")[0];

    const button_one = document.getElementsByName("button_add_one")[0];
    const button_two = document.getElementsByName("button_add_two")[0];

    button.addEventListener("click", myFunction);
    button_one.addEventListener("click", myFunction1);
    button_two.addEventListener("click", myFunction2);

    function myFunction() {
        alert ("Hello World!" + text.innerText);
    }
    console.dir(text);

    function myFunction1() {
        button_one.innerText = "clicked";
        button_two.innerText = "click me";
        text.innerText = button_one.value;
    }
    console.dir(button_one);

    function myFunction2() {
        button_two.innerText = "clicked";
        button_one.innerText = "click me";
        text.innerText = button_two.value;
    }
    console.dir(button_two);
});

