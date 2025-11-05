package tw.odk.spring3.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.odk.spring3.entity.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long>{

}
