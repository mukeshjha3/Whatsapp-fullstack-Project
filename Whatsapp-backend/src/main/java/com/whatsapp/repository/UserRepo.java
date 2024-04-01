package com.whatsapp.repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.whatsapp.model.User;


@Repository
public interface UserRepo extends JpaRepository<User, Integer>{

	public Optional<User> findByEmail(String email);
	
	@Query("SELECT u FROM User u WHERE u.full_name LIKE %:query% OR u.email LIKE %:query%")
	public List<User> searchUser(@Param("query") String query);
	
}
