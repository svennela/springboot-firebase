package io.pivotal.firebase;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by svennela on 3/20/17.
 */

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    @Autowired
    ApplicationConfiguration appConfig;

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
                                sendMail.generateAndSendEmail(user.getEmail(), user.getMessage()
                                    ,appConfig.getUsername(),appConfig.getPassword());
                                myFirebaseRef.child("cfuser").child(user.getId()).child("status").setValue("sent");

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