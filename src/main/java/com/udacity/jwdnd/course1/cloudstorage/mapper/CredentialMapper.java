package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CredentialMapper {
    @Select("SELECT * FROM credentials WHERE userid=#{userId}")
    List<Credential> getCredentials(Integer credentialId);

    @Select("SELECT * FROM credentials WHERE credentialid=#{credentialId}")
    Integer getCredential(Credential credentialId);

    @Insert("INSERT into Credentials(url, username, key, password, userid) VALUES(#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    void insertCredential(Credential credential);

    @Update("UPDATE credentials SET url=#{url}, username=#{username}, password=#{password}, key=#{key} WHERE credentialid=#{credentialId}")
    void updateCredential(Credential credential);

    @Delete("DELETE FROM credentials WHERE credentialid=#{credentialId}")
    void deleteCredential(Integer id);
}
