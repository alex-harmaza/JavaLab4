package by.training.notebook.command.impl;

import by.training.notebook.bean.*;
import by.training.notebook.bean.entity.Note;
import by.training.notebook.command.ICommand;
import by.training.notebook.command.exception.CommandException;
import by.training.notebook.service.exception.ServiceException;
import by.training.notebook.service.factory.ServiceFactory;


/**
 * Created by alexh on 27.09.2016.
 */
public class SearchNotesByCreatedDate implements ICommand {

    @Override
    public Response execute(Request request) throws CommandException {
        if (request == null || request.getClass() != RequestWithCreatedDate.class){
            throw new CommandException("The request does not RequestWithCreatedDate the class");
        }

        RequestWithCreatedDate temp = (RequestWithCreatedDate) request;
        Note[] notes;

        try {
            notes = ServiceFactory.getInstance()
                    .getNoteBookService().searchNotesByDateOfCreation(temp.getCreatedDate());
        }
        catch (ServiceException ex){
            throw new CommandException(ex.getMessage(), ex);
        }

        return new ResponseWithNoteArray(true, notes);
    }

}
