package edu.sjtu.party.dao.hibernate;

import edu.sjtu.party.dao.UserDAO;
import edu.sjtu.party.model.UserProfile;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: lay
 * @create: 2021/11/15 14:05
 **/
@Repository
public class UserDAOHibernate extends AutowiredDAOHibernate<UserProfile> implements UserDAO {

    @Override
    public UserProfile findById(String openId) {
        return super.getObject(UserProfile.class, openId);
    }

    @Override
    public void saveUser(UserProfile userProfile) {
        super.saveObject(userProfile);
    }

    @Override
    public void updateUser(UserProfile userProfile) {
        super.updateObject(userProfile);
    }
}
