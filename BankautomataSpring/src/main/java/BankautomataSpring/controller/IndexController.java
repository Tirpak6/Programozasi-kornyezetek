package BankautomataSpring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.example.BankAutomata.atm.AtmApplication.*;

@Controller
public class IndexController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping(path = "/cardNumber", method = RequestMethod.POST)
    public ResponseEntity<String> cardNumber(@RequestBody String cardNumber) {
        System.out.println(cardNumber);
        if(!isAccountValidCardNum(cardNumber)){
            return new ResponseEntity<>("New account, please enter pin code.", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Please enter pin code.", HttpStatus.OK);
        }
    }

    @RequestMapping(path = "/pinCode", method = RequestMethod.POST)
    public ResponseEntity<String> pinCode(@RequestBody String pinCode) {
        System.out.println(pinCode);
        if(!checkPinCode(pinCode)) {
            return new ResponseEntity<>("Pin code is not correct.", HttpStatus.BAD_REQUEST);
        }
        else {
            return new ResponseEntity<>("Your balance is " + isBalance(pinCode) + ".", HttpStatus.OK);
        }

    }

    @RequestMapping(path = "/newCustomer", method = RequestMethod.POST)
    public ResponseEntity<String> newCustomer(@RequestBody String pinCode) {
        //registerNewCustomer(pinCode);
        FirstInsertNewRecord(pinCode);
        return new ResponseEntity<>("Welcome to our Bank, please don't forget your pin code.", HttpStatus.OK);

    }

    @RequestMapping(path = "/deposit", method = RequestMethod.POST)
    public ResponseEntity<String> deposit(@RequestBody String amount) {
        /*
         *               A beérkező amount string típusú, ezt át kell integerré konvertálni!
         *
         *                   int intAmount = Integer.parseInt(amount);
         *
         *               A fenti képlet elvileg működik (nem teszteltem)
         *
         *               Innen meg tudod hívni az atmApplication függvényeit
         * */

        System.out.println(amount);
        Integer intAmount = Integer.valueOf(amount); //működött, de nem kell, mert stringként kell átvinni
        //System.out.println(intAmount.getClass().getName());
        depositFirst(amount);
        return new ResponseEntity<>("Your new balance is " + getBalance(), HttpStatus.OK);
    }

    @RequestMapping(path = "/withdraw", method = RequestMethod.POST)
    public ResponseEntity<String> withdrawal(@RequestBody String amount) {
        System.out.println(amount);
        Integer intAmount = Integer.valueOf(amount); //működött, de nem kell, mert stringként kell átvinni
        withdrawalFirst(amount);
        return new ResponseEntity<>("Your new balance is " + getBalance(), HttpStatus.OK);
    }

}
