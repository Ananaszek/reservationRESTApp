package pl.agataanaszewiczz.reservationRESTApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.agataanaszewiczz.reservationRESTApp.models.ReservationModel;
import pl.agataanaszewiczz.reservationRESTApp.models.forms.ReservationForm;
import pl.agataanaszewiczz.reservationRESTApp.models.repositories.ReservationRepository;
import pl.agataanaszewiczz.reservationRESTApp.models.services.StringService;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
public class MainController {

    @Autowired
    StringService stringService;

    @Autowired
    ReservationRepository reservationRepository;

    @GetMapping("/{page}")
    public String index(Model model, @PathVariable("page") int pageNumber){
        model.addAttribute("reservationForm", new ReservationForm());
        PageRequest pageRequest = new PageRequest(pageNumber, 2);

        model.addAttribute("reservations", reservationRepository.findByDateIsBetween(
                LocalDate.now(),
                LocalDate.now().plusWeeks(1), pageRequest));
        return "index";
    }

    @PostMapping("/")
    public String index(@ModelAttribute("reservationForm") @Valid ReservationForm form, BindingResult result, Model model){
        if(result.hasErrors()){
            return "index";
        }else if(reservationRepository.existsByDateEquals(form.getFormatedDate())){
            model.addAttribute("error", "Ten dzień jest już zajęty");
        }
        reservationRepository.save(new ReservationModel(form));
        return "index";
    }

//    @GetMapping("/test")
//    @ResponseBody
//    public String index() {
//        List<ReservationModel> reservationModel = reservationRepository.findByDateAfter(LocalDate.of(2017, 1, 1));
//
//        return reservationModel.stream()
//                .map(s -> s.toString())
//                .collect(Collectors.joining(" , "));
//    }
}