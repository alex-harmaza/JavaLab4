package by.training.notebook.command.impl;

import by.training.notebook.bean.Request;
import by.training.notebook.bean.Response;
import by.training.notebook.bean.ResponseWithNoteArray;
import by.training.notebook.command.ICommand;
import by.training.notebook.command.exception.CommandException;
import by.training.notebook.service.factory.ServiceFactory;

/**
 * Created by alexh on 27.09.2016.
 */
public class ShowNotesInNoteBook implements ICommand {

    @Override
    public Response execute(Request request) throws CommandException {
        if (request == null || request.getClass() != Request.class){
            throw new CommandException("The request does not Request the class");
        }

        return new ResponseWithNoteArray(true,
                ServiceFactory.getInstance().getNoteBookService().showNotes());
    }

}
