package com.jjerometest.sync;

import com.jjerome.sync.reflection.context.annotation.SyncIgnore;
import com.jjerome.sync.reflection.context.annotation.SyncMe;
import com.jjerome.sync.reflection.context.annotation.SyncMethod;
import com.jjerometest.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@SyncMe
@RequiredArgsConstructor
@Getter
@Setter
public class SomeRoomInfo {

    int roomId;
    User user;
    @SyncIgnore
    String someSecretInfo;

    @SyncMethod({"roomId", "user"})
    public int doSomeAndChangeParams() {
        roomId = 4308;
        user = new User();

        return 1;
    }
}
