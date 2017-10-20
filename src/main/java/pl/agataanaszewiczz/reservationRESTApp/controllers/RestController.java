package pl.agataanaszewiczz.reservationRESTApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.agataanaszewiczz.reservationRESTApp.models.KeyModel;
import pl.agataanaszewiczz.reservationRESTApp.models.ReservationModel;
import pl.agataanaszewiczz.reservationRESTApp.models.repositories.KeyRepository;
import pl.agataanaszewiczz.reservationRESTApp.models.repositories.ReservationRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Controller
public class RestController {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    KeyRepository keyRepository;


    @RequestMapping(value = "/rest/reservation", method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity reservationIndex(@RequestHeader("Password-App") String key){

        if(keyRepository.existsByText(key)){
            KeyModel keyModel = keyRepository.findByText(key);
            if(keyModel.getCounter() >= 100){
                return new ResponseEntity("Too many uses", HttpStatus.NOT_ACCEPTABLE);
            }else{
                keyModel.setCounter(keyModel.getCounter() + 1);
                keyRepository.save(keyModel);
            }
        }

        return new ResponseEntity(reservationRepository.findAll(),HttpStatus.OK);
    }

    @RequestMapping(value = "/rest/reservation/{lastname}", method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity reservation(@PathVariable("lastname") String lastname){
        return new ResponseEntity(reservationRepository.findByLastnameIgnoreCase(lastname),HttpStatus.OK);
    }

    @RequestMapping(value = "/rest/reservation", method = RequestMethod.POST,
            produces = "application/json")
    public ResponseEntity reservation(@RequestBody ReservationModel model){
        reservationRepository.save(model);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/rest/reservation/{id}", method = RequestMethod.DELETE,
            produces = "application/json")
    public ResponseEntity reservation(@PathVariable("id") int id){
        reservationRepository.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/rest/reservation/{id}/{date}", method = RequestMethod.PUT,
            produces = "application/json")
    public ResponseEntity reservationDateChange(@PathVariable("id") int id,
                                                @PathVariable("date") String date){
        ReservationModel model = reservationRepository.findOne(id);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate newDate = LocalDate.from(formatter.parse(date));

        if(reservationRepository.existsByDateEquals(newDate)){
            return new ResponseEntity("This date is busy", HttpStatus.CONFLICT);
        }

        model.setDate(newDate);
        reservationRepository.save(model);
        return new ResponseEntity(HttpStatus.OK);
    }


    @RequestMapping(value = "/rest/reservation", method = RequestMethod.PUT,
            produces = "application/json")
    public ResponseEntity responseAct(@RequestBody ReservationModel reservationModel){
        reservationRepository.save(reservationModel);
        return new ResponseEntity(HttpStatus.OK);
    }
}