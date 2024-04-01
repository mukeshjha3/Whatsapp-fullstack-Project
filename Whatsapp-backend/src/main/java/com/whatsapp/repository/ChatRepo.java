package com.whatsapp.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.whatsapp.model.Chat;
import com.whatsapp.model.User;


@Repository
public interface ChatRepo extends JpaRepository<Chat, Integer> {
	
	@Query("select c from Chat c join c.users u where u.id =:userId")
	public List<Chat> findchatByUserId(@Param("userId") Integer userId);
	
//	@Query("SELECT c FROM Chat c JOIN c.users u WHERE c.isGroup = false AND u.id = :userId AND :reqUserId IN (SELECT u2.id FROM c.users u2)")
//	public Chat findSingleChatByUserId(@Param("userId") Integer userId, @Param("reqUserId") Integer reqUserId);

	
	@Query("select c from Chat c join c.users u where c.isGroup = false and :user member of c.users and :reqUser member of c.users")
	public Chat findSingleChatByUserId(@Param("user") User user, @Param("reqUser") User reqUser);
}
