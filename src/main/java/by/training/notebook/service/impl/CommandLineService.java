package by.training.notebook.service.impl;

import by.training.notebook.CommandEnum;
import by.training.notebook.service.ICommandLineService;
import by.training.notebook.source.ConfigProvider;

/**
 * Created by alexh on 02.10.2016.
 */
public class CommandLineService implements ICommandLineService {

    @Override
    public String getCommandDescription() {
        StringBuilder builder = new StringBuilder();
        for (CommandEnum e : CommandEnum.values()){
            builder.append(ConfigProvider.getInstance().getProperty(e.name())).append("\n");
        }
        return builder.toString();
    }

}
