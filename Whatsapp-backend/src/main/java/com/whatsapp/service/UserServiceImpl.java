package com.whatsapp.service;
import java.util.List;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import com.whatsapp.exception.UserException;
import com.whatsapp.jwt.TokenProvider;
import com.whatsapp.model.User;
import com.whatsapp.repository.UserRepo;
import com.whatsapp.request.UpdateUserRequest;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepo userRepository;
	private final TokenProvider tokenprovider;

	@Override
	public User findUserByUserId(Integer userid) throws UserException {
		User findById = userRepository.findById(userid)
				.orElseThrow(() -> new UserException("user not found with id : " + userid));
		return findById;
	}

	@Override
	public User findUserProfile(String jwt) throws UserException {
		String email = tokenprovider.getEmailFromToken(jwt);

		if (email == null) {
			new BadCredentialsException("Invalid Token");
		}
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UserException("user not found with email : " + email));

		return user;
	}

	@Override
	public User updateUser(Integer userid, UpdateUserRequest req) throws UserException {
		User findById = userRepository.findById(userid)
				.orElseThrow(() -> new UserException("user not found with id : " + userid));
		
		if (req.getFull_name() != null) {
			findById.setFull_name(req.getFull_name());
		}
		
		if (req.getProfile_picture() != null) {
			findById.setFull_name(req.getFull_name());
		}
		
		findById.setProfile_picture(req.getProfile_picture());
		User user = userRepository.save(findById);
		return user;
	}

	@Override
	public List<User> searchUser(String query) {
	List<User> searchedUser = userRepository.searchUser(query);
		return searchedUser;
	}

}
