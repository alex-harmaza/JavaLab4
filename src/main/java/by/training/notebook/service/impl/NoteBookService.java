package by.training.notebook.service.impl;

import by.training.notebook.bean.entity.Note;
import by.training.notebook.bean.entity.NoteBook;
import by.training.notebook.service.INoteBookService;
import by.training.notebook.service.exception.ServiceException;
import by.training.notebook.source.ConfigProvider;
import by.training.notebook.source.NoteBookProvider;

import java.io.*;
import java.util.*;

/**
 * Created by alexh on 02.10.2016.
 */
public class NoteBookService implements INoteBookService {

    public void addNote(Date dateOfCreation, String content) throws ServiceException {
        if (dateOfCreation == null){
            throw new ServiceException("Date of creation is null");
        }
        if (content == null || content.trim().isEmpty()){
            throw new ServiceException("Incorrect content");
        }
        NoteBookProvider.getInstance().getNoteBook()
                .add(new Note(dateOfCreation, content));
    }

    public void createNewNoteBook() {
        NoteBookProvider.getInstance().getNoteBook().clear();
    }

    public Note[] showNotes(){
        NoteBook noteBook = NoteBookProvider.getInstance().getNoteBook();
        return noteBook.toArray(new Note[noteBook.size()]);
    }

    public void loadFromFile() throws ServiceException {
        NoteBook newNoteBook;

        try (ObjectInputStream s = new ObjectInputStream(
                new FileInputStream(ConfigProvider.getInstance().getProperty("file.path")))) {
             newNoteBook = (NoteBook) s.readObject();
        }
        catch (IOException | ClassNotFoundException ex) {
            throw new ServiceException(ex.getMessage(), ex);
        }

        NoteBook noteBook = NoteBookProvider.getInstance().getNoteBook();
        noteBook.clear();
        noteBook.addAll(newNoteBook);
    }

    public void writeInFile() throws ServiceException {
        try (ObjectOutputStream s = new ObjectOutputStream(
                new FileOutputStream(ConfigProvider.getInstance().getProperty("file.path")))) {
            s.writeObject(NoteBookProvider.getInstance().getNoteBook());
        }
        catch (IOException ex){
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    public Note[] searchNotesByContent(String content) throws ServiceException {
        if (content == null || content.trim().isEmpty()){
            throw new ServiceException("Incorrect content");
        }

        List<Note> result = new ArrayList<>();
        Iterator<Note> iterator = NoteBookProvider.getInstance()
                .getNoteBook().iterator();

        while (iterator.hasNext()){
            Note note = iterator.next();
            if (note.getData().equals(content)){
                result.add(note);
            }
        }

        return result.toArray(new Note[result.size()]);
    }

    public Note[] searchNotesByDateOfCreation(Date date) throws ServiceException {
        if (date == null){
            throw new ServiceException("Incorrect date");
        }

        List<Note> result = new ArrayList<>();
        Iterator<Note> iterator = NoteBookProvider.getInstance()
                .getNoteBook().iterator();

        while (iterator.hasNext()){
            Note note = iterator.next();

            Calendar createdDate = Calendar.getInstance();
            Calendar askingDate = Calendar.getInstance();

            createdDate.setTime(note.getCreatedDate());
            askingDate.setTime(date);

            if (createdDate.get(Calendar.YEAR) == askingDate.get(Calendar.YEAR)
                    && createdDate.get(Calendar.DAY_OF_YEAR) == askingDate.get(Calendar.DAY_OF_YEAR)){
                result.add(note);
            }
        }

        return result.toArray(new Note[result.size()]);
    }

}
