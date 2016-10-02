package by.training.notebook.command.impl;

import by.training.notebook.service.exception.ServiceException;
import by.training.notebook.service.factory.ServiceFactory;
import by.training.notebook.bean.Request;
import by.training.notebook.bean.Response;
import by.training.notebook.bean.ResponseWithMessage;
import by.training.notebook.command.ICommand;
import by.training.notebook.command.exception.CommandException;

/**
 * Created by Aliaksandr_Harmaza on 9/28/2016.
 */
public class LoadNoteBookFromFile implements ICommand {

    @Override
    public Response execute(Request request) throws CommandException {
        if (request == null || request.getClass() != Request.class){
            throw new CommandException("The request does not Request the class");
        }

        try {
            ServiceFactory.getInstance().getNoteBookService().loadFromFile();
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }

        return new ResponseWithMessage(true, "Notebook was loaded");
    }

}
