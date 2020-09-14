package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FileMapper {
    @Select("SELECT * FROM files WHERE userid=#{userId}")
    List<File> getFiles(Integer fileId);

    @Select("SELECT * FROM files WHERE filename=#{fileName}")
    Integer getFile(File fileId);

    @Select("SELECT * FROM files WHERE fileName=#{fileName}")
    File getFileData(String fileName);

    @Insert("INSERT into Files(filename, contenttype, filesize, userid, filedata) VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    void insertFile(File file);

    @Delete("DELETE FROM files WHERE fileid=#{fileId}")
    void deleteFile(Integer id);
}
