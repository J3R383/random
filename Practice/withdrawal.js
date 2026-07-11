function withdraw(amount, accountNumber, bankName) {
    if (amount <= 50 && accountNumber == 1234098765 && bankName === "Equity") {
        console.log("cannot withdraw from account");
    } else {
        console.log("Withdrawal successful!");
    }
}

withdraw(1000, 1234098765, "Equity");
function deposit(amount, accountNumber, bankName) {
    if (amount > 10000 && accountNumber === 1234098765 && bankName === "Equity") {
        console.log("cannot deposit from account");
    } else {
        console.log("Deposit successful!");
    }
}

deposit(1000, 1234098765, "Equity")
