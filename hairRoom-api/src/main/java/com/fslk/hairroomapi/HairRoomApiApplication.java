package com.fslk.hairroomapi;

import com.fslk.hairroomapi.entities.Client;
import com.fslk.hairroomapi.entities.Customer;
import com.fslk.hairroomapi.entities.Hairdresser;
import com.fslk.hairroomapi.repositories.ClientRepository;
import com.fslk.hairroomapi.repositories.CustomerRepository;
import com.fslk.hairroomapi.repositories.HairdresserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HairRoomApiApplication {
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private HairdresserRepository hairRepository;
	@Autowired
	private ClientRepository clientRepository;

	public static void main(String[] args) {
		SpringApplication.run(HairRoomApiApplication.class, args);
	}

	@Bean
	CommandLineRunner run(HairdresserRepository hairRepository) {
		return args -> {
			/*hairRepository.save(new Hairdresser(null, "Ali", "Mourad", "0666544522", "flation1.png", null));
			hairRepository.save(new Hairdresser(null, "Mourad", "Riadh", "0666544522", "flation2.png", null));
			hairRepository.save(new Hairdresser(null, "Riadh", "Ali", "0666544522", "flation3.png", null));

			hairRepository.findAll().forEach((hrdrs -> {
				for (int i = 0; i < 3; i++) {
					Customer cust = new Customer();
					cust.setFirstname(RandomStringUtils.randomAlphabetic(8));
					cust.setLastname(RandomStringUtils.randomAlphabetic(8));
					cust.setPhone((06) + RandomStringUtils.randomNumeric(8));
					cust.setHairdresser(hrdrs);
					customerRepository.save(cust);

					Client cli = new Client();
					cli.setFirstname(RandomStringUtils.randomAlphabetic(8));
					cli.setLastname(RandomStringUtils.randomAlphabetic(8));
					cli.setPhone((06) + RandomStringUtils.randomNumeric(8));
					clientRepository.save(cli);
				}

			}));*/
		};
	}


}
