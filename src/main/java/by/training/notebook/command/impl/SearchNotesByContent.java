package by.training.notebook.command.impl;

import by.training.notebook.bean.Request;
import by.training.notebook.bean.Response;
import by.training.notebook.bean.RequestWithNoteContent;
import by.training.notebook.bean.ResponseWithNoteArray;
import by.training.notebook.bean.entity.Note;
import by.training.notebook.command.ICommand;
import by.training.notebook.command.exception.CommandException;
import by.training.notebook.service.exception.ServiceException;
import by.training.notebook.service.factory.ServiceFactory;


/**
 * Created by Aliaksandr_Harmaza on 9/27/2016.
 */
public class SearchNotesByContent implements ICommand {

    @Override
    public Response execute(Request request) throws CommandException {
        if (request == null || request.getClass() != RequestWithNoteContent.class){
            throw new CommandException("The request does not SearchNoteByContentRequest the class");
        }

        RequestWithNoteContent temp = (RequestWithNoteContent) request;
        Note[] notes;

        try {
           notes = ServiceFactory.getInstance().getNoteBookService()
                   .searchNotesByContent(temp.getContent());
        }
        catch (ServiceException ex){
            throw new CommandException(ex.getMessage(), ex);
        }

        return new ResponseWithNoteArray(true, notes);
    }

}
