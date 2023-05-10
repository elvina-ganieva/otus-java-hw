package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {

    private final EntityClassMetaData<T> entityClassMetaData;

    private final String selectAllSql;

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;

        var commaSeparatedFields = entityClassMetaData.getAllFields().stream()
                .map(Field::getName)
                .collect(Collectors.joining(", "));
        this.selectAllSql = "select " + commaSeparatedFields + " from " + entityClassMetaData.getName() + ";";
    }

    @Override
    public String getSelectAllSql() {
        return selectAllSql;
    }

    @Override
    public String getSelectByIdSql() {
        return "select * from " +
                entityClassMetaData.getName() +
                " where " +
                entityClassMetaData.getIdField().getName() +
                " = ?;";
    }

    @Override
    public String getInsertSql() {
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

    @Override
    public String getUpdateSql() {
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
}
