package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NoteMapper {

    @Select("SELECT * FROM notes WHERE userid=#{userId}")
    List<Note> getNotes(Integer noteId);

    @Select("SELECT * FROM notes WHERE noteid=#{noteId}")
    Integer getNote(Note noteId);

    @Insert("INSERT into Notes(notetitle, notedescription, userid) VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    void insertNote(Note note);

    @Update("UPDATE notes SET notetitle=#{noteTitle}, notedescription=#{noteDescription} WHERE noteid=#{noteId}")
    void updateNote(Note note);

    @Delete("DELETE FROM notes WHERE noteid=#{noteId}")
    void deleteNote(Integer id);
}
