package pl.agataanaszewiczz.reservationRESTApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@EntityScan(
		basePackageClasses = {ReservationRestAppApplication.class, Jsr310JpaConverters.class}
)

@SpringBootApplication
public class ReservationRestAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservationRestAppApplication.class, args);
	}
}
