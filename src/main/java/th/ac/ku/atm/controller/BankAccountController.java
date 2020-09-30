package th.ac.ku.atm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import th.ac.ku.atm.model.BankAccount;
import th.ac.ku.atm.service.BankAccountService;

@Controller
@RequestMapping("/bankaccount")
public class BankAccountController {

    private BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService){
        this.bankAccountService = bankAccountService;
    }

    @GetMapping
    public String getOpenBankAccountPage(Model model){
        model.addAttribute("bankaccounts", bankAccountService.getBankAccounts());
        return "bankaccount";
    }
    @PostMapping
    public String openAccount(@ModelAttribute BankAccount bankAccount, Model model) {
        bankAccountService.openAccount(bankAccount);
        model.addAttribute("bankaccounts",bankAccountService.getBankAccounts());
        return "redirect:bankaccount";
    }

    @GetMapping("/edit/{id}")
    public String getEditBankAccountPage(@PathVariable int id, Model model){
        BankAccount account = bankAccountService.getBankAccount(id);
        model.addAttribute("bankAccount", account);
        return "bankaccount-edit";
    }

    @PostMapping("/edit/{id}")
    public String editAccount(@PathVariable int id,
                              @ModelAttribute BankAccount bankAccount,
                              Model model) {
        return "redirect:/bankaccount";
    }

    @GetMapping("/delete/{id}")
    public String getDeleteBankAccountPage(@PathVariable int id, Model model){
        BankAccount account = bankAccountService.getBankAccount(id);
        model.addAttribute("bankAccount", account);
        return "bankaccount-delete";
    }

    @PostMapping("/delete/{id}")
    public String deleteAccount(@PathVariable int id,
                              @ModelAttribute BankAccount bankAccount,
                              Model model) {

        bankAccountService.deleteBankAccount(bankAccount);
        model.addAttribute("bankaccounts",bankAccountService.getBankAccounts());
        return "redirect:/bankaccount";
    }

    @GetMapping("/withdraw/{id}")
    public String getWithdrawPage(@PathVariable int id, Model model){
        BankAccount account = bankAccountService.getBankAccount(id);
        model.addAttribute("bankAccount", account);
        return "bankaccount-withdraw";
    }

    @PostMapping("/withdraw/{id}")
    public String withdraw(@PathVariable int id,
                           @ModelAttribute BankAccount bankAccount,
                           Model model) {
        BankAccount bankAccount1 = bankAccountService.getBankAccount(id);

        double checkWithdraw= bankAccount1.getBalance()-bankAccount.getAmount();

        if(checkWithdraw>=0){
            bankAccount.setBalance(checkWithdraw);
            bankAccountService.editBankAccount(bankAccount);
            model.addAttribute("bankaccounts",bankAccountService.getBankAccounts());
            return "redirect:/bankaccount";
        }
        else {
                bankAccountService.editBankAccount(bankAccount1);
                model.addAttribute("bankAccount", bankAccount1);
            model.addAttribute("alert", "Withdraw amount exceed your balance.");
            return "bankaccount-withdraw";
        }
    }

    @GetMapping("/deposit/{id}")
    public String getDepositPage(@PathVariable int id, Model model){
        BankAccount account = bankAccountService.getBankAccount(id);
        model.addAttribute("bankAccount", account);
        return "bankaccount-deposit";
    }

    @PostMapping("/deposit/{id}")
    public String deposit(@PathVariable int id,
                           @ModelAttribute BankAccount bankAccount,
                           Model model) {
        BankAccount bankAccount1 = bankAccountService.getBankAccount(id);
        double sum = bankAccount1.getBalance()+bankAccount.getAmount();
        bankAccount.setBalance(sum);
        bankAccountService.editBankAccount(bankAccount);
        model.addAttribute("bankaccounts",bankAccountService.getBankAccounts());
        return "redirect:/bankaccount";
    }
}
