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

    @Select("SELECT * FROM noteTitle WHERE noteid=#{noteId} AND userid=#{userId}")
    Note getNote(String noteTitle);

    @Insert("INSERT into Notes(notetitle, notedescription, userid) VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insertNote(Note note);

    @Update("UPDATE notes SET notetitle=#{noteTitle}, notedescription=#{noteDescription} WHERE noteid=#{noteId}")
    int updateNote(Note note);

    @Delete("DELETE FROM notes WHERE noteid=#{noteId}")
    void deleteNote(int id);
}
