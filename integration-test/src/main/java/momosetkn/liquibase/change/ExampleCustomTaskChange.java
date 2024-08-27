package momosetkn.liquibase.change;

import liquibase.change.custom.CustomTaskChange;
import liquibase.change.custom.CustomTaskRollback;
import liquibase.database.Database;
import liquibase.exception.CustomChangeException;
import liquibase.exception.RollbackImpossibleException;
import liquibase.exception.SetupException;
import liquibase.exception.ValidationErrors;
import liquibase.resource.ResourceAccessor;

public class ExampleCustomTaskChange implements CustomTaskChange, CustomTaskRollback {
    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

    public static int executeCallCount = 0;
    public static int rollbackCallCount = 0;

    @Override
    public void execute(Database database) throws CustomChangeException {
        logger.info("execute");
        executeCallCount++;
    }

    @Override
    public String getConfirmationMessage() {
        return "getConfirmationMessage";
    }

    @Override
    public void setUp() throws SetupException {
    }

    @Override
    public void setFileOpener(ResourceAccessor resourceAccessor) {

    }

    @Override
    public ValidationErrors validate(Database database) {
        return new ValidationErrors();
    }

    @Override
    public void rollback(Database database) throws CustomChangeException, RollbackImpossibleException {
        logger.info("rollback");
        rollbackCallCount++;
    }
}
