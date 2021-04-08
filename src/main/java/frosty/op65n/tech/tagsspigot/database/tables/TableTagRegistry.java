package frosty.op65n.tech.tagsspigot.database.tables;

import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;
import org.op65n.gazelle.api.DatabaseTable;

public class TableTagRegistry implements DatabaseTable {

    @Override
    public int priority() {
        return 0;
    }

    @Override
    @Language("MariaDB")
    public @NotNull String createQuery() {
        return "CREATE TABLE IF NOT EXISTS `%database%`.`tag_registry` ( " +
                "  `player` VARCHAR(36) NOT NULL, " +
                "  `tag` VARCHAR(36) NULL, " +
                "  PRIMARY KEY (`player`), " +
                "  UNIQUE INDEX `player_UNIQUE` (`player` ASC)) " +
                "ENGINE = InnoDB;";
    }

}
