package com.edgar.bbs.dao;

import com.edgar.bbs.domain.Message;
import com.edgar.bbs.dao.info.MessageInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface MessageDao extends JpaRepository<Message, Long> {

    @Query(value = "SELECT id, `type`, title,content, `time`, `read` FROM message WHERE username=:username ORDER BY time DESC ", nativeQuery = true)
    List<MessageInfo> getAllByUsername(String username);

    @Query(value = "SELECT COUNT(*) FROM message WHERE username=?1 AND `read`=0", nativeQuery = true)
    Long getUnreadMessage(String username);

    @Query(value = "SELECT * FROM message WHERE username=?1 AND id=?2", nativeQuery = true)
    Optional<Message> findMessageByUsernameAndId(String username, Long id);

    @Modifying
    @Query(value = "DELETE FROM message WHERE id=?1", nativeQuery = true)
    void deleteMessageById(Long id);

    @Modifying
    @Query(value = "UPDATE message SET `read`=true WHERE id=?1", nativeQuery = true)
    void readMessageById(Long id);

    @Modifying
    @Query(value = "DELETE FROM message WHERE username=?1", nativeQuery = true)
    void deleteMessageByUsername(String username);

    @Modifying
    @Query(value = "UPDATE message set `read`=true WHERE username=?1", nativeQuery = true)
    void updateByUsername(String username);
}
