package ru.online_shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.online_shop.models.Person;
import ru.online_shop.services.PaymentTinkoffService;
import ru.online_shop.services.PersonService;

@RestController
public class PaymentTinkoffController {

    private final PaymentTinkoffService paymentService;
    private final PersonService personService;

    @Autowired
    public PaymentTinkoffController(PaymentTinkoffService paymentService, PersonService personService) {
        this.paymentService = paymentService;
        this.personService = personService;
    }

    @PostMapping("/payment/{id}")
    public ResponseEntity<?> createPayment(@PathVariable Long id) {
        Person person = personService.findById(id);
        if (person == null) {
            return ResponseEntity.notFound().build();
        }
        boolean paymentResult = paymentService.processPayment(person);
        if (paymentResult) {
            return ResponseEntity.ok("Payment processed successfully");
        } else {
            return ResponseEntity.status(500).body("Payment failed");
        }
    }
}
