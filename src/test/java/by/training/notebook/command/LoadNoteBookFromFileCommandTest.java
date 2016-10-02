package by.training.notebook.command;

import by.training.notebook.CommandEnum;
import by.training.notebook.bean.entity.Note;
import by.training.notebook.bean.entity.NoteBook;
import by.training.notebook.source.ConfigProvider;
import by.training.notebook.bean.*;
import by.training.notebook.command.impl.LoadNoteBookFromFile;
import by.training.notebook.command.exception.CommandException;
import by.training.notebook.source.NoteBookProvider;
import org.junit.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

/**
 * Created by Aliaksandr_Harmaza on 9/29/2016.
 */
public class LoadNoteBookFromFileCommandTest extends CommandTest {

    public LoadNoteBookFromFileCommandTest(){
        super(new LoadNoteBookFromFile());
    }


    @Override
    @Test(expected = CommandException.class)
    public void checkOnIncorrectRequestType() throws CommandException {
        getCommand().execute(new RequestWithCreatedDate(CommandEnum.LOAD, new Date()));
    }

    @Test(expected = CommandException.class)
    public void checkOfTheAbsenceFile() throws CommandException, IOException {
        getCommand().execute(new Request(CommandEnum.LOAD));
    }

    @Override
    @Test()
    public void checkResponse() throws IOException, CommandException {
        Note note = new Note(new Date(0), "test");
        NoteBook noteBook = NoteBookProvider.getInstance().getNoteBook();
        noteBook.add(note);

        ObjectOutputStream s = null;
        try {
            s = new ObjectOutputStream(new FileOutputStream(
                    ConfigProvider.getInstance().getProperty("file.path")));
            s.writeObject(noteBook);
        }
        finally {
            if (s != null){
                s.close();
            }
        }

        noteBook.clear();
        Response response = getCommand().execute(new Request(CommandEnum.LOAD));

        assertEquals("Incorrect response status", response.isStatus(), true);
        assertEquals("Incorrect response type", response.getClass(), ResponseWithMessage.class);
        assertEquals("Incorrect load note", note, noteBook.get(0));
    }


    @Before @After
    public void deleteSourceFile() throws IOException {
        Files.deleteIfExists(Paths.get(ConfigProvider.getInstance().getProperty("file.path")));
    }
}
