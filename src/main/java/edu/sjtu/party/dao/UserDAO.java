package edu.sjtu.party.dao;

import edu.sjtu.party.model.UserProfile;

/**
 * @description:
 * @author: lay
 * @create: 2021/11/15 14:05
 **/
public interface UserDAO {

    UserProfile findById(String openId);

    void saveUser(UserProfile userProfile);

    void updateUser(UserProfile userProfile);
}
