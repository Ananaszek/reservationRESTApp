package pl.agataanaszewiczz.reservationRESTApp.models.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.agataanaszewiczz.reservationRESTApp.models.KeyModel;

public interface KeyRepository extends CrudRepository<KeyModel, Integer> {
    //@Query(nativeQuery = true, value = "SELECT EXISTS(SELECT * FROM `key` WHERE `keyvalue` = ?1)")
    boolean existsByText(String key);
    KeyModel findByText(String key);
}
