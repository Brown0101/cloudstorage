package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CredentialMapper {
    @Select("SELECT * FROM credentials WHERE credentialid=#{credentialId}")
    List<Credential> getCredentials(Integer credentialId);

    @Select("SELECT * FROM credentials WHERE credentialid=#{credentialId}")
    Integer getCredential(Credential credentialId);

    @Insert("INSERT into Credential(url, username, key, password, userid) VALUES(#{url}, #{username}, #{key}), #{password}), #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insertCredential(Credential credential);

    @Update("UPDATE notes SET url=#{url}, username=#{username}, password=#{password} WHERE credentialid=#{credentialId}")
    void updateCredential(Credential credential);

    @Delete("DELETE FROM credential WHERE credentialid=#{credentialId}")
    void deleteCredential(Integer id);
}
