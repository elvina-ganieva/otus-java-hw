package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private final EntityClassMetaData<?> entityClassMetaData;
    private final String selectAllSql;
    private final String selectByIdSql;
    private final String insertSql;
    private final String updateSql;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;

        this.selectAllSql = createSelectAllSql();
        this.selectByIdSql = createSelectAllByIdSql();
        this.insertSql = createInsertSql();
        this.updateSql = createUpdateSql();
    }

    @Override
    public String getSelectAllSql() {
        return selectAllSql;
    }

    @Override
    public String getSelectByIdSql() {
        return selectByIdSql;
    }

    @Override
    public String getInsertSql() {
        return insertSql;
    }

    @Override
    public String getUpdateSql() {
        return updateSql;
    }

    private String createSelectAllByIdSql() {
        return "select " + getAllCommaSeparatedClassFields() +
                " from " + entityClassMetaData.getName() +
                " where " + entityClassMetaData.getIdField().getName() +
                " = ?;";
    }

    private String createSelectAllSql() {
        return "select " + getAllCommaSeparatedClassFields() +
                " from " + entityClassMetaData.getName() +
                ";";
    }

    private String createInsertSql() {
        var fieldNamesSeparatedByComma = entityClassMetaData.getFieldsWithoutId()
                .stream()
                .map(Field::getName)
                .collect(Collectors.joining(", "));

        var numberOfFieldsWithoutId = entityClassMetaData.getFieldsWithoutId().size();

        var query = new StringBuilder();
        query.append("insert into ")
                .append(entityClassMetaData.getName())
                .append(" (")
                .append(fieldNamesSeparatedByComma)
                .append(") values (");

        for (int i = 0; i < numberOfFieldsWithoutId; i++) {
            query.append("?");
            if (i != numberOfFieldsWithoutId - 1) {
                query.append(", ");
            }
        }
        query.append(");");
        return query.toString();
    }

    private String createUpdateSql() {
        var numberOfFieldsWithoutId = entityClassMetaData.getFieldsWithoutId().size();

        var query = new StringBuilder();
        query.append("update ")
                .append(entityClassMetaData.getName())
                .append(" set ");

        for (int i = 0; i < numberOfFieldsWithoutId; i++) {
            query.append(entityClassMetaData.getFieldsWithoutId().get(i).getName())
                    .append(" = ?");
            if (i != numberOfFieldsWithoutId - 1) {
                query.append(", ");
            }
        }
        query.append(" where ")
                .append(entityClassMetaData.getIdField().getName())
                .append(" = ?;");
        return query.toString();
    }

    private String getAllCommaSeparatedClassFields() {
        return entityClassMetaData.getAllFields().stream()
                .map(Field::getName)
                .collect(Collectors.joining(", "));
    }
}
