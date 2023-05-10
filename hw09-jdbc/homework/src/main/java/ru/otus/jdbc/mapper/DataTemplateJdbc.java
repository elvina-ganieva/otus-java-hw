package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(
                connection,
                entitySQLMetaData.getSelectByIdSql(),
                List.of(id),
                rs -> {
                    try {
                        if (rs.next()) {
                            return getClassInstance(rs);
                        }
                    } catch (SQLException e) {
                        throw new DataTemplateException(e);
                    }
                    return null;
                });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(
                connection,
                entitySQLMetaData.getSelectAllSql(),
                Collections.emptyList(),
                rs -> {
                    var entityList = new ArrayList<T>();
                    try {
                        while (rs.next()) {
                            entityList.add(getClassInstance(rs));
                        }
                        return entityList;
                    } catch (SQLException e) {
                        throw new DataTemplateException(e);
                    }
                }).orElseThrow(DataTemplateException::new);
    }

    @Override
    public long insert(Connection connection, T client) {
        try {
            return dbExecutor.executeStatement(
                    connection,
                    entitySQLMetaData.getInsertSql(),
                    getQueryParams(client));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T client) {
        List<Object> params = getQueryParams(client);
        params.add(getIdValue(client));
        try {
            dbExecutor.executeStatement(
                    connection,
                    entitySQLMetaData.getUpdateSql(),
                    params);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    private List<Object> getQueryParams(T client) {
        List<Object> params = new ArrayList<>();

        var getters = Arrays.stream(client.getClass().getDeclaredMethods())
                .filter(method -> method.getName().startsWith("get") || method.getName().startsWith("is"))
                .toList();

        for (Method method : getters) {
            try {
                if (!method.getName().substring(3).equalsIgnoreCase(entityClassMetaData.getIdField().getName())) {
                    params.add(method.invoke(client));
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new DataTemplateException(e);
            }
        }
        return params;
    }

    private Object getIdValue(T client) {
        var idFieldName = entityClassMetaData.getIdField().getName();

        var getters = Arrays.stream(client.getClass().getDeclaredMethods())
                .filter(method -> method.getName().startsWith("get"))
                .toList();

        var getIdMethod = getters.stream()
                .filter(method -> method.getName().substring(3).equalsIgnoreCase(idFieldName))
                .findFirst()
                .orElseThrow();

        try {
            return getIdMethod.invoke(client);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new DataTemplateException(e);
        }
    }

    private T getClassInstance(ResultSet rs) {
        List<Object> params = new ArrayList<>();

        try {
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                var object = rs.getObject(i + 1);
                params.add(object);
            }
            return entityClassMetaData.getConstructor().newInstance(params.toArray());
        } catch (SQLException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
