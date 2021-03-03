package org.geektimes.projects.user.service;

import org.apache.derby.iapi.db.Database;
import org.geektimes.projects.user.domain.User;
import org.geektimes.projects.user.repository.DatabaseUserRepository;
import org.geektimes.projects.user.sql.MyDBConnectionManager;

/**
 * Created by lt 2021/3/3
 */
public class UserServiceImpl implements UserService{

    private DatabaseUserRepository repository;

    @Override
    public boolean register(User user) {
        repository = new DatabaseUserRepository(new MyDBConnectionManager());
        return repository.save(user);
    }

    @Override
    public boolean deregister(User user) {
        return false;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public User queryUserById(Long id) {
        return null;
    }

    @Override
    public User queryUserByNameAndPassword(String name, String password) {
        return null;
    }
}
