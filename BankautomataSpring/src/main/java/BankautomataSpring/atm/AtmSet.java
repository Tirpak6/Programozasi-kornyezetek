package BankautomataSpring.atm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AtmSet {
    private int accountId;
    private float balance;
    private String cardNumber;

    public void withdrawal(float amount){
        if(balance > amount){
            balance = balance - amount;
        }
    }

    public void Deposit(float amount) {
        balance = balance + amount;
    }
}