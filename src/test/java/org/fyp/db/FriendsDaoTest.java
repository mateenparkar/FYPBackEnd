package org.fyp.db;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class FriendsDaoTest {
    private DatabaseConnector databaseConnector = Mockito.mock(DatabaseConnector.class);
    private Connection connection = Mockito.mock(Connection.class);
    private PreparedStatement statement = Mockito.mock(PreparedStatement.class);
    private ResultSet resultSet = Mockito.mock(ResultSet.class);

    private FriendsDao friendsDao = new FriendsDao(databaseConnector);

    @Test
    public void addFriend_ShouldAddFriend_WhenValid() throws Exception {
        int user_id = 1;
        int friend_id = 2;

        String preparedStatement = "INSERT INTO Friends (user_id, friend_user_id) VALUES (?, ?)";

        DatabaseConnector.setConn(connection);
        Mockito.when(connection.prepareStatement(preparedStatement)).thenReturn(statement);
        Mockito.when(statement.executeUpdate()).thenReturn(1);

        friendsDao.addFriend(user_id, friend_id);

        Mockito.verify(statement, Mockito.times(1)).setInt(1, user_id);
        Mockito.verify(statement, Mockito.times(1)).setInt(2, friend_id);
        Mockito.verify(statement, Mockito.times(1)).executeUpdate();
    }

    @Test
    public void getFriends_ShouldReturnListOfFriends_WhenUserHasFriends() throws Exception {
        int id = 1;
        String expectedFriend = "friend";

        String preparedStatement = "SELECT u.username " +
                "FROM Friends f " +
                "JOIN Users u ON f.friend_user_id = u.user_id " +
                "WHERE f.user_id = ?";

        DatabaseConnector.setConn(connection);
        Mockito.when(connection.prepareStatement(preparedStatement)).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenReturn(resultSet);  // Mocking executeQuery
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
        Mockito.when(resultSet.getString("username")).thenReturn("friend");

        List<String> friends = friendsDao.getFriends(id);
        assertEquals(expectedFriend, friends.get(0));
    }

    @Test
    public void getFriends_ShouldReturnEmptyList_WhenUserHasNoFriends() throws Exception {
        int id = 1;
        String preparedStatement = "SELECT u.username " +
                "FROM Friends f " +
                "JOIN Users u ON f.friend_user_id = u.user_id " +
                "WHERE f.user_id = ?";

        DatabaseConnector.setConn(connection);
        Mockito.when(connection.prepareStatement(preparedStatement)).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(false);

        List<String> friends = friendsDao.getFriends(id);
        assertTrue(friends.isEmpty());
    }

}
