package io.pivotal.firebase.repositories;



import io.pivotal.firebase.domain.User;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by svennela on 8/4/17.
 */

@Repository
public interface JpaUserRepository extends JpaRepository<User, String> {
}
