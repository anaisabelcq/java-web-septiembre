package ar.com.educacionit.dao.user;

import ar.com.educacionit.app.domain.User;
import ar.com.educacionit.dao.exceptions.GenericExeption;

public interface UserRepository {

	public User getuser(String username) throws GenericExeption;
}
