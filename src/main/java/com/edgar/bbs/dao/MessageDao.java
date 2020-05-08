package com.edgar.bbs.dao;

import com.edgar.bbs.domain.Message;
import com.edgar.bbs.dao.info.MessageInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageDao extends JpaRepository<Message, Long> {

    @Query(value = "SELECT type, content, title, `time`, `read` FROM message WHERE username=:username", nativeQuery = true)
    List<MessageInfo> getMessageByUsername(String username);
}
