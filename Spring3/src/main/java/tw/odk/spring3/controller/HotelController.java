package tw.odk.spring3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tw.odk.spring3.entity.Hotel;
import tw.odk.spring3.repository.HotelRepository;

@RestController
@RequestMapping("/api/hotel")
public class HotelController {
	
	
	@Autowired
	private HotelRepository repository;
	
	@GetMapping("/all")
	public ResponseEntity<Page<Hotel>> getAll(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size ) {
		
		PageRequest pageable = PageRequest.of(page, size);
		Page<Hotel> pageHotel = repository.findAll(pageable);
		
		return ResponseEntity.ok(pageHotel);
	}
}
