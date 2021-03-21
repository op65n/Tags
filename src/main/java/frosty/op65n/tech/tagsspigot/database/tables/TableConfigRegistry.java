package frosty.op65n.tech.tagsspigot.database.tables;

import frosty.op65n.tech.tagsspigot.database.adapter.ITable;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;

public final class TableConfigRegistry implements ITable {

    @NotNull
    @Language("MariaDB")
    private String database = "tags";

    public TableConfigRegistry(@Language("MariaDB") final @NotNull String database) {
        this.database = database;
    }

    @Override
    public int priority() {
        return 0;
    }

    @Override
    @Language("MariaDB")
    public @NotNull String getCreateQuery() {
        return "CREATE TABLE IF NOT EXISTS `" + database + "`.`config_registry` ( " +
                "  `identifier` VARCHAR(36) NOT NULL, " +
                "  `description` VARCHAR(100) NOT NULL, " +
                "  `display` VARCHAR(100) NOT NULL, " +
                "  `permission` VARCHAR(100) NOT NULL, " +
                "  PRIMARY KEY (`identifier`)) " +
                "ENGINE = InnoDB;";
    }

}
