package by.training.notebook.command.impl;

import by.training.notebook.bean.Request;
import by.training.notebook.bean.RequestWithNoteContent;
import by.training.notebook.bean.Response;
import by.training.notebook.bean.ResponseWithMessage;
import by.training.notebook.command.ICommand;
import by.training.notebook.command.exception.CommandException;
import by.training.notebook.service.exception.ServiceException;
import by.training.notebook.service.factory.ServiceFactory;

import java.util.Date;

/**
 * Created by Aliaksandr_Harmaza on 9/27/2016.
 */
public class AddNoteToNoteBook implements ICommand {

    @Override
    public Response execute(Request request) throws CommandException {
        if (request == null || request.getClass() != RequestWithNoteContent.class){
            throw new CommandException("The request does not AddNoteToNoteBookRequest the class");
        }

        RequestWithNoteContent temp = (RequestWithNoteContent) request;

        try {
            ServiceFactory.getInstance().getNoteBookService()
                    .addNote(new Date(), temp.getContent());
        }
        catch (ServiceException ex){
            throw new CommandException(ex.getMessage(), ex);
        }

        return new ResponseWithMessage(true, "Note added");
    }

}
