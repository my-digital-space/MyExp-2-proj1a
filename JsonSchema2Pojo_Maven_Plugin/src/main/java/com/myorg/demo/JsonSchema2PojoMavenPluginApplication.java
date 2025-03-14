package com.myorg.demo;

import com.myorg.generated.Person;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JsonSchema2PojoMavenPluginApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(JsonSchema2PojoMavenPluginApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Create an instance of the generated POJO
		Person person = new Person();
		person.setFirstName("John");
		person.setLastName("Doe");
		person.setAge(30);

		// Use the POJO (here, simply printing to console)
		System.out.println("Generated Person: "
				+ person.getFirstName() + " "
				+ person.getLastName() + ", age "
				+ person.getAge());
	}
}
