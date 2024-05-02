package model.persistence.dao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.exceptions.EntityNotFoundException;
import model.persistence.DatabaseConnection;
import model.persistence.dao.interfaces.IGenericDAO;
import model.persistence.dao.paciente.PacienteFullDTO;

public abstract class GenericDAO<T> implements IGenericDAO<T> {
	protected Connection connection;
	private String sqlQuery;
	private String fileName;

    public GenericDAO(String fileName) {
        this.connection = DatabaseConnection.getConnection();
        this.fileName = fileName;
        this.sqlQuery = DatabaseConnection.loadSQL("resources/sql/querys/" + fileName + ".sql");
    }

    @Override
	public List<T> getAll() {
        List<T> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sqlQuery);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                T entity = convertToEntity(resultSet);
                list.add(entity);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao executar consulta: " + e.getMessage());
        }
        return list;
    }
	
	@Override
	public int save(Object entity, String tableName) {
		StringBuilder columns = new StringBuilder();
	    StringBuilder values = new StringBuilder();
	    List<Object> valueList = new ArrayList<>();

	    Field[] fields = entity.getClass().getDeclaredFields();
	    for (Field field : fields) {
	        field.setAccessible(true);
	        try {
	            if (columns.length() > 0) {
	                columns.append(", ");
	                values.append(", ");
	            }
	            columns.append(field.getName());
	            values.append("?");
	            valueList.add(field.get(entity));
	        } catch (IllegalAccessException e) {
	            System.err.println("Falha no acesso ao campo: " + e.getMessage());
	        }
	    }

	    String sql = "INSERT INTO " + tableName + " (" + columns.toString() + ") VALUES (" + values.toString() + ")";
	    System.out.println(sql);
	    try (PreparedStatement statement = connection.prepareStatement(sql)) {
	        for (int i = 0; i < valueList.size(); i++) {
	            statement.setObject(i + 1, valueList.get(i));
	        }
	        int affectedRows = statement.executeUpdate();
	        if (affectedRows == 0) {
	            throw new SQLException("Creating user failed, no rows affected.");
	        }

	        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                return generatedKeys.getInt(1);
	            } else {
	                throw new SQLException("Creating user failed, no ID obtained.");
	            }
	        }
	    } catch (SQLException e) {
	        System.err.println("Erro ao salvar o objeto: " + e.getMessage());
	        return -1;
	    }
	}
    
	protected abstract T convertToEntity(ResultSet resultSet) throws SQLException;
}
