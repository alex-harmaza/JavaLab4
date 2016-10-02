package by.training.notebook.bean;

import by.training.notebook.CommandEnum;

import java.util.Date;

/**
 * Created by alexh on 27.09.2016.
 */
public class RequestWithCreatedDate extends Request {

    private Date createdDate;


    public RequestWithCreatedDate(CommandEnum commandEnum, Date createdDate) {
        super(commandEnum);
        setCreatedDate(createdDate);
    }


    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        if (createdDate == null){
            throw new IllegalArgumentException("CreatedDate is null");
        }
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("RequestWithCreatedDate{commandName=")
                .append(getCommandName()).append(";createdDate=")
                .append(getCreatedDate()).append("}").toString();
    }
}
