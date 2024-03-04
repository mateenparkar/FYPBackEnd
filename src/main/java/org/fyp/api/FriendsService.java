package org.fyp.api;

import org.fyp.cli.FriendRequest;
import org.fyp.client.FailedToAddFriendException;
import org.fyp.client.FailedToGetFriendsException;
import org.fyp.db.FriendsDao;

import java.sql.SQLException;
import java.util.List;

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

    public List<String> getFriends(int user_id) throws SQLException, FailedToGetFriendsException {
        try{
            return friendsDao.getFriends(user_id);
        }catch(SQLException e){
            throw new FailedToGetFriendsException();
        }
    }
}
