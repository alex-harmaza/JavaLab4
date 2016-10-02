package by.training.notebook.command;

import by.training.notebook.CommandEnum;
import by.training.notebook.bean.entity.NoteBook;
import by.training.notebook.source.ConfigProvider;
import by.training.notebook.bean.Request;
import by.training.notebook.bean.RequestWithCreatedDate;
import by.training.notebook.bean.entity.Note;
import by.training.notebook.command.impl.WriteNoteBookInFile;
import by.training.notebook.command.exception.CommandException;
import by.training.notebook.source.NoteBookProvider;
import org.junit.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

/**
 * Created by Aliaksandr_Harmaza on 9/29/2016.
 */
public class WriteNoteBookInFileCommandTest extends CommandTest {

    public WriteNoteBookInFileCommandTest(){
        super(new WriteNoteBookInFile());
    }


    @Override
    @Test(expected = CommandException.class)
    public void checkOnIncorrectRequestType() throws CommandException {
        getCommand().execute(new RequestWithCreatedDate(CommandEnum.WRITE, new Date()));
    }

    @Override
    @Test
    public void checkResponse() throws CommandException, IOException {
        Note note = new Note(new Date(0), "test");
        NoteBookProvider.getInstance().getNoteBook().add(note);

        getCommand().execute(new Request(CommandEnum.WRITE));
        NoteBook noteBook;

        try (ObjectInputStream s = new ObjectInputStream(
                new FileInputStream(ConfigProvider.getInstance().getProperty("file.path")))) {
            noteBook = (NoteBook) s.readObject();
        }
        catch (ClassNotFoundException ex){
            throw new IOException(ex);
        }

        assertEquals("Incorrect input note", note, noteBook.get(0));
    }


    @Before @After
    public void deleteSourceFile() throws IOException {
        Files.deleteIfExists(Paths.get(ConfigProvider.getInstance().getProperty("file.path")));
    }
}
