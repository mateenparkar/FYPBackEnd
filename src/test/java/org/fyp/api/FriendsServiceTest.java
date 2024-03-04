package org.fyp.api;
import static org.junit.jupiter.api.Assertions.*;
import org.fyp.cli.FriendRequest;
import org.fyp.client.FailedToAddFriendException;
import org.fyp.client.FailedToGetFriendsException;
import org.fyp.db.FriendsDao;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FriendsServiceTest {
    FriendsDao friendsDaoMock = Mockito.mock(FriendsDao.class);
    FriendsService friendsService = new FriendsService(friendsDaoMock);

    @Test
    public void addFriend_ShouldDoNothing_WhenFriendIsAdded() {
        FriendRequest friendRequest = new FriendRequest(1, 2);
        assertDoesNotThrow(() -> friendsService.addFriend(friendRequest));
    }

    @Test
    public void addFriend_ShouldThrowException_WhenDaoThrowsException() throws SQLException {
        FriendRequest friendRequest = new FriendRequest(1, 2);
        Mockito.doThrow(SQLException.class).when(friendsDaoMock).addFriend(1, 2);
        assertThrows(FailedToAddFriendException.class, () -> friendsService.addFriend(friendRequest));
    }

    @Test
    public void getFriends_ShouldReturnListOfFriends_WhenIdIsValid() throws SQLException, FailedToGetFriendsException {
        List<String> friends = new ArrayList<>();
        friends.add("friend1");
        when(friendsDaoMock.getFriends(123)).thenReturn(friends);
        List<String> retrievedFriends = friendsService.getFriends(123);
        assertEquals(friends, retrievedFriends);
    }

    @Test
    public void getFriends_ShouldThrowException_WhenDaoThrowsException() throws SQLException {
        when(friendsDaoMock.getFriends(123)).thenThrow(SQLException.class);
        assertThrows(FailedToGetFriendsException.class, () -> friendsService.getFriends(123));
    }
}
