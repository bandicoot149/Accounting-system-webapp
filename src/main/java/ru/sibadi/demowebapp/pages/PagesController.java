package ru.sibadi.demowebapp.pages;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sibadi.demowebapp.domain.Payment;
import ru.sibadi.demowebapp.domain.Person;
import ru.sibadi.demowebapp.repository.PaymentRepository;
import ru.sibadi.demowebapp.repository.PersonRepository;

import java.util.ArrayList;
import java.util.List;


@Controller
public class PagesController {
    private final PersonRepository personRepository;
    private final PaymentRepository paymentRepository;

    public PagesController(PersonRepository personRepository, PaymentRepository paymentRepository) {
        this.personRepository = personRepository;
        this.paymentRepository = paymentRepository;
    }

    // GET http://localhost:8080/
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("persons", personRepository.findAll());
        return "index"; // index.html
    }

    // GET http://localhost:8080/person/200
    @GetMapping("/person/{id}")
    public String personPage(
            @PathVariable("id") int id,
            Model model
    ) {
        Person person = null;
        for (Person p : personRepository.findAll()) {
            if (p.getId() == id) {
                person = p;
            }
        }

        model.addAttribute("person", person);
        List<Payment> personPayments = new ArrayList<>();
        for (Payment p : paymentRepository.findAll()) {
            if (p.getPersonId() == id) {
                personPayments.add(p);
            }
        }
        model.addAttribute("payments", personPayments);
        return "person"; // person.html
    }

    // GET http://localhost:8080/person/payment/200
    @GetMapping("/payment/{id}")
    public String paymentPage(
            @PathVariable("id") int id,
            Model model
    ) {
        Payment payment = null;
        for (Payment p : paymentRepository.findAll()) {
            if (p.getId() == id) {
                payment = p;
            }
        }
        model.addAttribute("payment", payment);
        return "payment";
    }

    @PostMapping("/person/{id}")
    public String personEdit(
            @PathVariable("id") int id,
            @RequestParam("name") String name,
            @RequestParam("salary") int salary
    ) {
        Person person = null;
        for (Person p : personRepository.findAll()) {
            if (p.getId() == id) {
                person = p;
            }
        }
        person.setName(name);
        person.setSalary(salary);
        return "redirect:/person/" + id;
    }



    @PostMapping("/person")
    public String personSave(
            @RequestParam("name") String name,
            @RequestParam("salary") int salary
    ) {
        Person person = new Person();
        person.setName(name);
        person.setSalary(salary);
        personRepository.save(person);
        return "redirect:/";
    }

    @RequestMapping(value="/delete", method=RequestMethod.GET)
    public String deletePayment(@RequestParam("paymentId") int paymentId, Model model) {
        Payment payment = null;
        for (Payment p : paymentRepository.findAll()) {
            if (p.getId() == paymentId) {
                payment = p;
            }
        }
        int personId = payment.getPersonId();
        paymentRepository.delete(payment);
        return "redirect:/person/" + personId;
    }

    @PostMapping("/payment/{id}")
    public String paymentEdit(
            @PathVariable("id") int id,
            @RequestParam("salary") int salary,
            @RequestParam("prize") int prize
    ) {
        Payment payment = null;
        for (Payment p : paymentRepository.findAll()) {
            if (p.getId() == id) {
                payment = p;
            }
        }
        payment.setSalary(salary);
        payment.setPrize(prize);
        return "redirect:/payment/" + id;
    }

    @PostMapping("/person/{personId}/payment")
    public String paymentSave(
            @PathVariable("personId") int personId,
            @RequestParam("salary") int salary,
            @RequestParam("prize") int prize,
            @RequestParam("date") String date
    ) {
        Payment payment = new Payment();
        payment.setPrize(prize);
        payment.setSalary(salary);
        payment.setPersonId(personId);
        payment.setDate(date);
        paymentRepository.save(payment);
        return "redirect:/person/" + personId;
    }
}
