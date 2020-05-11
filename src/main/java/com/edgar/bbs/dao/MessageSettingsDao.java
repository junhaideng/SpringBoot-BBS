package com.edgar.bbs.dao;

import com.edgar.bbs.domain.MessageSettings;
import com.edgar.bbs.dao.info.MessageSettingsInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

@Transactional
public interface MessageSettingsDao extends JpaRepository<MessageSettings, Long> {

    @Query(value = "SELECT `comment`,`like`, star from message_settings WHERE username=:username", nativeQuery = true)
    public MessageSettingsInfo findMessageSettingsUsingUsername(String username);

    @Modifying
    @Query(value = "UPDATE message_settings set `comment`=:comment, `like`=:like, star=:star WHERE username=:username", nativeQuery = true)
    void updateMessageSettingsByUsername(boolean comment, boolean like, boolean star, String username);

    @Modifying
    @Query(value = "INSERT INTO message_settings(username) VALUES (?1)", nativeQuery = true)
    void insert(String username);
}
