package io.pivotal.firebase;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.data.repository.CrudRepository;
/**
 * Created by svennela on 3/20/17.
 */

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    @Autowired
    ApplicationConfiguration appConfig;



    private CrudRepository<io.pivotal.firebase.domain.User, String> repository;

    @Autowired
    public CommandLineAppStartupRunner(CrudRepository<io.pivotal.firebase.domain.User, String> repository) {
        this.repository = repository;
    }

    private static final String ROOT_URL = "https://cloudfoundry.firebaseio.com/";



    private static SendMail sendMail = new SendMail();

    @Override
    public void run(String...args) throws Exception {

        try {


            Firebase myFirebaseRef = new Firebase(ROOT_URL);


            myFirebaseRef.child("cfuser").addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot snapshot) {

                    System.out.println("***************");
                    System.out.println(appConfig.getUsername());
                    System.out.println(appConfig.getPassword());
                    System.out.println("***************");


                    for (DataSnapshot child : snapshot.getChildren()) {
                        User user = child.getValue(User.class);
                        user.setId(child.getKey());

                        System.out.println(user.getId());
                        System.out.println(user.getEmail());
                        System.out.println(user.getFullname());
                        System.out.println(user.getStatus());

                        if(user.getStatus().compareTo("pending") ==0){
                            // send email and update status

                            try {

                                sendMail.generateAndSendEmail(user.getEmail(), user.getMessage(),appConfig.getUsername(),appConfig.getPassword());

                                io.pivotal.firebase.domain.User dbUser = new io.pivotal.firebase.domain.User();
                                dbUser.setEmail(user.getEmail());
                                dbUser.setFullname(user.getFullname());
                                dbUser.setMessage(user.getMessage());
                                dbUser.setStatus(user.getStatus());
                                dbUser = repository.save(dbUser);

                                myFirebaseRef.child("cfuser").child(user.getId()).child("status").setValue("sent");
                                myFirebaseRef.child("cfuser").child(user.getId()).child("dbid").setValue(dbUser.getId());

                            }catch (Exception e){
                                System.out.println(e.toString());
                            }
                        }

                    }

                }



                @Override
                public void onCancelled(FirebaseError error) { }

            });
        }catch(Exception e){
            System.out.println(e.toString());
        }

    }
}