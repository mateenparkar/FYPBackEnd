package org.fyp.resources;

import org.fyp.api.FriendsService;
import org.fyp.cli.FriendRequest;
import org.fyp.client.FailedToAddFriendException;
import org.fyp.client.FailedToGetFriendsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)
public class FriendsControllerTest {
    FriendsService friendsServiceMock = Mockito.mock(FriendsService.class);
    FriendsController friendsController = new FriendsController(friendsServiceMock);

    @Test
    public void addFriend_ShouldReturn201_WhenFriendIsAdded() throws SQLException, FailedToAddFriendException {
        FriendRequest friendRequest = new FriendRequest(1, 2);
        Mockito.doNothing().when(friendsServiceMock).addFriend(friendRequest);

        Response response = friendsController.addFriend(friendRequest);

        assertEquals(201, response.getStatus());
    }

    @Test
    public void addFriend_ShouldReturn400_WhenFailedToAddFriendExceptionIsThrown() throws SQLException, FailedToAddFriendException {
        FriendRequest friendRequest = new FriendRequest(1, 2);
        Mockito.doThrow(new FailedToAddFriendException()).when(friendsServiceMock).addFriend(friendRequest);

        Response response = friendsController.addFriend(friendRequest);

        assertEquals(400, response.getStatus());
    }

    @Test
    public void addFriend_ShouldReturn500_WhenFriendIsNotAdded() throws SQLException, FailedToAddFriendException {
        FriendRequest friendRequest = new FriendRequest(1, 2);
        Mockito.doThrow(new SQLException()).when(friendsServiceMock).addFriend(friendRequest);

        Response response = friendsController.addFriend(friendRequest);

        assertEquals(500, response.getStatus());
    }

    @Test
    public void getFriends_ShouldReturn200_WhenFriendsExist() throws SQLException, FailedToGetFriendsException {
        int userId = 1;
        List<String> friends = new ArrayList<>();
        Mockito.when(friendsServiceMock.getFriends(userId)).thenReturn(friends);

        Response response = friendsController.getFriends(userId);

        assertEquals(200, response.getStatus());
    }

    @Test
    public void getFriends_ShouldReturn500_WhenFriendsDoNotExist() throws SQLException, FailedToGetFriendsException {
        int userId = 1;
        Mockito.when(friendsServiceMock.getFriends(userId)).thenThrow(new FailedToGetFriendsException());
        Response response = friendsController.getFriends(userId);

        assertEquals(500, response.getStatus());
    }
}
