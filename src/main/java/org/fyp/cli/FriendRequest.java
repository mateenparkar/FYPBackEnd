package org.fyp.cli;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FriendRequest {
    @JsonCreator
    public FriendRequest(@JsonProperty("user_id") int user_id, @JsonProperty("friend_user_id") int friend_id) {
        this.user_id = user_id;
        this.friend_id = friend_id;
    }

    private int user_id;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(int friend_id) {
        this.friend_id = friend_id;
    }

    private int friend_id;

}
