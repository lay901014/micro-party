package edu.sjtu.party.service.impl;

import edu.sjtu.party.dao.UserDAO;
import edu.sjtu.party.model.UserProfile;
import edu.sjtu.party.service.UserProfileService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description:
 * @author: lay
 * @create: 2021/11/15 11:25
 **/
@Service
@Transactional
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public void saveUserProfile(UserProfile userProfile) {
        UserProfile p = userDAO.findById(userProfile.getOpenId());
        if(p == null) {
            userDAO.saveUser(userProfile);
        }
        else {
            BeanUtils.copyProperties(userProfile, p);
            userDAO.updateUser(p);
        }

    }

}
