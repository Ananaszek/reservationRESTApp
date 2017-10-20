package pl.agataanaszewiczz.reservationRESTApp.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.agataanaszewiczz.reservationRESTApp.models.forms.ReservationForm;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "reservation")
@NoArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ReservationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlAttribute
    private int id;
    //@Column(name = "name1")
    @XmlElement
    private String name;
    @XmlElement
    private String lastname;
    @XmlElement
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate date;
    @XmlElement
    private String adres;

    public ReservationModel(ReservationForm form){
        name = form.getName();
        lastname = form.getLastname();
        date = form.getFormatedDate();
        adres = form.getAdres();
    }
}