package org.fyp.db;

import org.fyp.cli.Author;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AuthorDaoTest {
    private DatabaseConnector databaseConnector = Mockito.mock(DatabaseConnector.class);
    private Connection connection = Mockito.mock(Connection.class);
    private PreparedStatement statement = Mockito.mock(PreparedStatement.class);
    private ResultSet resultSet = Mockito.mock(ResultSet.class);

    private AuthorDao authorDao = new AuthorDao(databaseConnector);

    @Test
    public void getAuthorById_ShouldReturnAuthor_WhenAuthorExists() throws SQLException {
        int id = 1;
        String name = "author";
        Author expectedAuthor = new Author(id, name);

        String preparedStatement = "SELECT author_id, name FROM Authors WHERE author_id = " + id;
        DatabaseConnector.setConn(connection);
        Mockito.when(connection.createStatement()).thenReturn(statement);
        Mockito.when(statement.executeQuery(preparedStatement)).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true);
        Mockito.when(resultSet.getInt("author_id")).thenReturn(id);
        Mockito.when(resultSet.getString("name")).thenReturn(name);

        Author author = authorDao.getAuthorById(id);

        assertEquals(expectedAuthor.getAuthor_id(), author.getAuthor_id());
        assertEquals(expectedAuthor.getName(), author.getName());
    }

    @Test
    public void getAuthorById_ShouldReturnNull_WhenAuthorDoesNotExist() throws SQLException {
        int id = 1;

        String preparedStatement = "SELECT author_id, name FROM Authors WHERE author_id = " + id;
        DatabaseConnector.setConn(connection);
        Mockito.when(connection.createStatement()).thenReturn(statement);
        Mockito.when(statement.executeQuery(preparedStatement)).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(false);

        Author author = authorDao.getAuthorById(id);

        assertNull(author);
    }
}
