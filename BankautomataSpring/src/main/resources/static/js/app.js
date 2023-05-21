let textBox = document.getElementById("textBox");
let messageBox = document.getElementById("messageBox");
let greenText = document.getElementById("green-text");
let yellowText = document.getElementById("yellow-text");
let redText = document.getElementById("red-text");
let btnGreen = document.getElementById("btn-green");
let btnYellow = document.getElementById("btn-yellow");
let btnRed = document.getElementById("btn-red");

let numberArray = new Array();
let numString;
let firstPhase = true;
let isCardCode = true;
let isPinCode = false;
let customer = true;
let isDeposit = false;
let isWithdraw = false;
let cardNumber;
let pinCode;
let responseMessage;
let amountOfMoney;

messageBox.innerHTML = "Please enter card number";

function btnPush(n)
{
    if(firstPhase && isCardCode)
    {
        btnRed.addEventListener("click", abort);
        btnYellow.addEventListener("click", cancel);
        numberArray.push(n);
        if(numberArray.length == 4 || numberArray.length == 9 || numberArray.length == 14)
        {
            numberArray.push(" ");
        }
        numString = numberArray.join("").toString();

        if(numberArray.length == 19)
        {
            greenText.innerHTML = "Submit";
            btnGreen.addEventListener("click", submit);
            isCardCode = false;
        }
        textBox.innerHTML = numString;
        yellowText.innerHTML = "Delete";
        redText.innerHTML = "Abort";

    }

    if(firstPhase && isPinCode)
    {
        redText.innerHTML = "Quit";
        btnRed.addEventListener("click", refresh);
        if(responseMessage.includes("New"))
        {
                        numberArray.push(n)
                        if(numberArray.length == 4)
                        {
                            pinCode = numberArray.join("").toString();
                            let request = new XMLHttpRequest();
                            request.onreadystatechange = function() //onreadystatechange , mit jelent, meg magába a function()
                            {
                                if (this.readyState == 4)
                                {
                                    messageBox.innerHTML = this.responseText; //responsText mit csinál?
                                    console.log(this.responseText);
                                }
                            };
                            request.open("POST", "/newCustomer"); //itt hívja meg a @RequestMapping(path = "/cardNumber", method = RequestMethod.POST)
                            request.send(pinCode);//elküldi a pinkódot
                            isPinCode = false;
                            firstPhase = false;
                        }

            customer = false;
        }

        else
        {
            numberArray.push(n)
            if(numberArray.length == 4)
            {
                pinCode = numberArray.join("").toString();
                let request = new XMLHttpRequest();
                request.onreadystatechange = function()
                {
                    if (this.readyState == 4)
                    {
                        messageBox.innerHTML = this.responseText;
                        console.log(this.responseText);
                    }
                };
                request.open("POST", "/pinCode");
                request.send(pinCode);
                isPinCode = false;
                firstPhase = false;
            }
        }
        numString = numberArray.join("").toString().replaceAll(/[0-p]/ig, "*");
        textBox.innerHTML = numString;
    }

    if(!firstPhase && customer && !isDeposit && !isWithdraw) {
        messageBox.innerHTML = responseMessage;
        if(!responseMessage.includes("Pin")) {
            textBox.innerHTML = "Please choose what to do next";
            greenText.innerHTML = "Deposit";
            btnGreen.addEventListener("click", deposit);
            yellowText.innerHTML = "Withdraw"
            btnYellow.addEventListener("click", withdraw);
            redText.innerHTML = "Quit";
            btnRed.addEventListener("click", refresh);
            numberArray = [];
            responseMessage = "";
        }
        else {
            greenText.innerHTML = "";
            yellowText.innerHTML = ""
            redText.innerHTML = "Quit";
            btnRed.addEventListener("click", refresh);
        }

    }

    if(!firstPhase && !customer && !isDeposit && !isWithdraw) {
            messageBox.innerHTML = responseMessage;
            textBox.innerHTML = "";
            greenText.innerHTML = "";
            yellowText.innerHTML = ""
            redText.innerHTML = "Quit";
            btnRed.addEventListener("click", refresh);

    }

    if(isDeposit) {
        numberArray.push(n);
        numString = numberArray.join("").toString();
        textBox.innerHTML = numString;
        amountOfMoney = numString;

        btnGreen.addEventListener("click", submitDeposit);
    }

    if(isWithdraw) {
        numberArray.push(n);
        numString = numberArray.join("").toString();
        textBox.innerHTML = numString;
        amountOfMoney = numString;

        btnGreen.addEventListener("click", submitWithdraw);
    }

}

function submit() {
    cardNumber = numString.replace(/\s/g, "");
    let request = new XMLHttpRequest();
    request.onreadystatechange = function() {
                    if (this.readyState == 4) {
                        messageBox.innerHTML = this.responseText;
                        responseMessage = this.responseText;
                        console.log(this.responseText);
                    }
                };
    request.open("POST", "/cardNumber");
    request.send(cardNumber);
    yellowText.innerHTML = "";
    greenText.innerHTML = "";
    redText.innerHTML = "";
    isPinCode = true;
    numberArray = [];
    numString = numberArray.toString();
    textBox.innerHTML = numString;
    btnGreen.removeEventListener("click", submit);
}

function cancel() {
    if(numberArray.length <= 19) {
        greenText.innerHTML = "";
    }

    if(isCardCode || numberArray.length == 19) {
        if(numberArray.length == 19) {
            isCardCode = true;
        }
        if(numberArray[numberArray.length-1] == " ") {
            numberArray.pop();
        }
        numberArray.pop();
        numString = numberArray.join("").toString();
        textBox.innerHTML = numString;
    }
}

function abort() {
    numberArray = [];
    isCardCode = true;
    numString = numberArray.join("").toString();
    textBox.innerHTML = numString;
}

function refresh() {
    window.location.reload();
}

function withdraw() {
    textBox.innerHTML = "";
    greenText.innerHTML = "Submit";
    yellowText.innerHTML = "";
    btnRed.addEventListener("click", refresh);
    messageBox.innerHTML = "Please enter the amount of money you would like to withdraw";
    isWithdraw = true;
    btnGreen.removeEventListener("click", deposit);
}

function deposit() {
    textBox.innerHTML = "";
    greenText.innerHTML = "Submit";
    yellowText.innerHTML = "";
    btnRed.addEventListener("click", refresh);
    messageBox.innerHTML = "Please enter the amount of money you would like to deposit";
    isDeposit = true;
    btnGreen.removeEventListener("click", deposit);
}

function submitDeposit() {
    textBox.innerHTML = "Thank you for choosing us";
    greenText.innerHTML = "";
    btnGreen.removeEventListener("click", submitDeposit);
    btnYellow.removeEventListener("click", withdraw);
        let request = new XMLHttpRequest();
        request.onreadystatechange = function() {
                        if (this.readyState == 4) {
                            messageBox.innerHTML = this.responseText;
                            responseMessage = this.responseText;
                            console.log(this.responseText);
                        }
                    };
        request.open("POST", "/deposit");
        request.send(amountOfMoney);
}

function submitWithdraw() {
    textBox.innerHTML = "Thank you for choosing us";
    greenText.innerHTML = "";
    btnGreen.removeEventListener("click", submitWithdraw);
    btnYellow.removeEventListener("click", withdraw);
        let request = new XMLHttpRequest();
        request.onreadystatechange = function() {
                        if (this.readyState == 4) {
                            messageBox.innerHTML = this.responseText;
                            responseMessage = this.responseText;
                            console.log(this.responseText);
                        }
                    };
        request.open("POST", "/withdraw");
        request.send(amountOfMoney);
}