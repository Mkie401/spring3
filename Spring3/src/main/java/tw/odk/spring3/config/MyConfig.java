package tw.odk.spring3.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {
	
	@Value("${odk.company.name}")
	private String companyName;
	
	@Bean
	public String companyName() {
		return "Super" + companyName;
	}
	
}
