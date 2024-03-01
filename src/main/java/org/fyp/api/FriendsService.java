package org.fyp.api;

import org.fyp.cli.FriendRequest;
import org.fyp.client.FailedToAddFriendException;
import org.fyp.db.FriendsDao;

import java.sql.SQLException;

public class FriendsService {
    private FriendsDao friendsDao;

    public FriendsService(FriendsDao friendsDao) {
        this.friendsDao = friendsDao;
    }

    public void addFriend(FriendRequest friendRequest) throws FailedToAddFriendException, SQLException {
        try {
            friendsDao.addFriend(friendRequest.getUser_id(), friendRequest.getFriend_id());
        } catch (SQLException e) {
            throw new FailedToAddFriendException();
        }
    }
}
