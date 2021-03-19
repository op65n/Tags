package frosty.op65n.tech.tagsspigot.database.tables;

import frosty.op65n.tech.tagsspigot.database.adapter.ITable;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;

public class TableTagRegistry implements ITable {

    @NotNull
    @Language("MariaDB")
    private String database = "tags";

    public TableTagRegistry(@Language("MariaDB") final @NotNull String database) {
        this.database = database;
    }

    @Override
    public int priority() {
        return 0;
    }

    @Override
    @Language("MariaDB")
    public @NotNull String getCreateQuery() {
        return "CREATE TABLE IF NOT EXISTS `" + database + "`.`tag_registry` ( " +
                "  `player` VARCHAR(36) NOT NULL, " +
                "  `tag` VARCHAR(36) NULL, " +
                "  PRIMARY KEY (`player`), " +
                "  UNIQUE INDEX `player_UNIQUE` (`player` ASC)) " +
                "ENGINE = InnoDB;";
    }

}
