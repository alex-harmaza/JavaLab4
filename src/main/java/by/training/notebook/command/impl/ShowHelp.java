package by.training.notebook.command.impl;

import by.training.notebook.service.factory.ServiceFactory;
import by.training.notebook.bean.Request;
import by.training.notebook.bean.Response;
import by.training.notebook.bean.ResponseWithMessage;
import by.training.notebook.command.ICommand;
import by.training.notebook.command.exception.CommandException;

/**
 * Created by alexh on 29.09.2016.
 */
public class ShowHelp implements ICommand {

    @Override
    public Response execute(Request request) throws CommandException {
        if (request == null || request.getClass() != Request.class){
            throw new CommandException("Incorrect request type");
        }

        return new ResponseWithMessage(true, ServiceFactory.getInstance()
                .getCommandLineService().getCommandDescription());
    }
}
