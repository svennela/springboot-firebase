package io.pivotal.firebase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.firebase.client.Config;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.Logger.Level;
import com.firebase.client.ValueEventListener;
import com.firebase.client.FirebaseError;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfiguration.class)
public class SpringbootFirebaseApplication {

	@Autowired
	ApplicationConfiguration applicationConfiguration;


	public static void main(String[] args) {

		SpringApplication.run(SpringbootFirebaseApplication.class, args);



	}


}
