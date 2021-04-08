package frosty.op65n.tech.tagsspigot.database.tables;

import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;
import org.op65n.gazelle.api.DatabaseTable;

public final class TableConfigRegistry implements DatabaseTable {

    @Override
    public int priority() {
        return 0;
    }

    @Override
    @Language("MariaDB")
    public @NotNull String createQuery() {
        return "CREATE TABLE IF NOT EXISTS `%database%`.`config_registry` ( " +
                "  `identifier` VARCHAR(36) NOT NULL, " +
                "  `description` VARCHAR(100) NOT NULL, " +
                "  `display` VARCHAR(100) NOT NULL, " +
                "  `permission` VARCHAR(100) NOT NULL, " +
                "  PRIMARY KEY (`identifier`)) " +
                "ENGINE = InnoDB;";
    }

}
