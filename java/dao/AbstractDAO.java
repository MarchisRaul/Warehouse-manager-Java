package dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;

/**
 * @Author: Technical University of Cluj-Napoca, Romania Distributed Systems
 *          Research Laboratory, http://dsrl.coned.utcluj.ro/
 * @Since: Apr 03, 2017
 * @Source http://www.java-blog.com/mapping-javaobjects-database-reflection-generics
 */
public class AbstractDAO<T> {
	protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

	private final Class<T> type;

	@SuppressWarnings("unchecked")
	public AbstractDAO() {
		this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

	}

	/**
	 * 
	 * @param fields = numele variabilelor instanta cu care este apelata metoda
	 * @return query ce afiseaza toate randurile pentru id-ul trimis prin "?"
	 */
	private String createSelectQuery(String[] fields) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" * ");
		sb.append(" FROM ");
		sb.append("warehousedb." + type.getSimpleName());
		sb.append(" WHERE " + fields[0] + " = ?");
		return sb.toString();
	}

	/**
	 * 
	 * @return query ce afiseaza toate randurile dintr-un tabel
	 */
	public String createFindAllQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append("* ");
		sb.append("FROM ");
		sb.append("warehousedb." + type.getSimpleName());

		return sb.toString();
	}

	/**
	 * 
	 * @param fields = numele variabilelor instanta cu care este apelata metoda
	 * @return query ce sterge toate randul dintr-un tabel pentru care id-ul este
	 *         egal cu "?"
	 */
	public String createDeleteQuery(String[] fields) {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE ");
		sb.append("FROM ");
		sb.append("warehousedb." + type.getSimpleName());
		sb.append(" WHERE ");
		sb.append(fields[0]);
		sb.append(" = ");
		sb.append("?");

		return sb.toString();
	}

	/**
	 * 
	 * @param fields = numele variabilelor instanta cu care este apelata metoda
	 * @return query ce realizeaza update-ul unui rand din tabel pentru care id-ul
	 *         este egal cu "?"
	 */
	public String createUpdateQuery(String[] fields) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ");
		sb.append("warehousedb." + type.getSimpleName());
		sb.append(" set ");
		for (int i = 0; i < fields.length; i++) {
			sb.append(fields[i]);
			sb.append(" = ?");
			if (i != fields.length - 1)
				sb.append(", ");
		}
		sb.append("WHERE ");
		sb.append(fields[0]);
		sb.append(" = ");
		sb.append("?");

		return sb.toString();
	}

	/**
	 * 
	 * @param fields = numele variabilelor instanta cu care este apelata metoda
	 * @return query ce va insera o linie intr-un tabel
	 */
	public String insertQuery(String[] fields) {
		// String query = "INSERT INTO records (id, time) VALUES (?, ?)";
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT ");
		sb.append("INTO ");
		sb.append("warehousedb." + type.getSimpleName());
		sb.append(" (");
		for (int i = 0; i < fields.length; i++) {
			sb.append(fields[i]);
			if (i != fields.length - 1)
				sb.append(", ");
		}
		sb.append(") ");
		sb.append(" VALUES ");
		sb.append(" (");
		for (int i = 0; i < fields.length; i++) {
			sb.append("?");
			if (i != fields.length - 1)
				sb.append(", ");
		}
		sb.append(")");

		return sb.toString();
	}

	/**
	 * 
	 * @return o lista cu toate randurile dintr-un tabel cu care se apeleaza aceasta
	 *         metoda
	 */
	public List<T> findAll() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		String query = createFindAllQuery();
		try {
			connection = ConnectionFactory.getConnection(); // facem conexiunea
			statement = connection.prepareStatement(query); // pregatim query-ul
			resultSet = statement.executeQuery();

			return createObjects(resultSet);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return null;
	}

	/**
	 * 
	 * @param id reprezinta id-ul unui client / produs / comanda
	 * @return obiectul din tabelul cu care se apeleaza aceasta metoda si care are
	 *         id-ul primit ca parametru
	 */
	public T findById(int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Field[] fields = type.getDeclaredFields();
		String[] fieldsName = new String[fields.length];
		Object[] valuesForFields = new Object[fields.length];
		for (int i = 0; i < fields.length; i++)
			fieldsName[i] = fields[i].getName();

		String query = createSelectQuery(fieldsName);
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();

			return createObjects(resultSet).get(0);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return null;
	}

	/**
	 * 
	 * @param resultSet reprezinta toate randurile dintr-un tabel
	 * @return o lista cu obiectele din acel tabel
	 */
	private List<T> createObjects(ResultSet resultSet) {
		List<T> list = new ArrayList<T>();
		try {
			while (resultSet.next()) {
				T instance = type.newInstance();
				for (Field field : type.getDeclaredFields()) {
					Object value = resultSet.getObject(field.getName());
					PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
					Method method = propertyDescriptor.getWriteMethod();
					method.invoke(instance, value);
				}
				list.add(instance);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * metoda insereaza un element intr-o tabela
	 * 
	 * @param t un obiect generic
	 * @return obiectul generic t
	 */
	public T insert(T t) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Field[] fields = type.getDeclaredFields();
		String[] fieldsName = new String[fields.length];
		Object[] valuesForFields = new Object[fields.length];
		for (int i = 0; i < fields.length; i++)
			fieldsName[i] = fields[i].getName();

		int j = 0;
		for (Field field : fields) {
			field.setAccessible(true);

			Object value = null;

			try {
				value = field.get(t);
				valuesForFields[j++] = value;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

		}
		String query = insertQuery(fieldsName);

		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);

			for (int i = 0; i < valuesForFields.length; i++)
				if (valuesForFields[i].getClass().getSimpleName().equals("Integer"))
					statement.setInt(i + 1, ((Integer) valuesForFields[i]).intValue());
				else if (valuesForFields[i].getClass().getSimpleName().equals("String"))
					statement.setString(i + 1, ((String) valuesForFields[i]));
				else if (valuesForFields[i].getClass().getSimpleName().equals("Float"))
					statement.setFloat(i + 1, ((Float) valuesForFields[i]).floatValue());

			statement.executeUpdate();

		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:insertToDB " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}

		return t;
	}

	/**
	 * metoda efectueaza un update intr-un tabel, la campul care are id-ul egal cu
	 * cel primit ca parametru
	 * 
	 * @param t  un obiect generic
	 * @param id un id
	 * @return obiectul t
	 */
	public T update(T t, int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		Field[] fields = type.getDeclaredFields();
		String[] fieldsName = new String[fields.length];
		Object[] valuesForFields = new Object[fields.length];
		for (int i = 0; i < fields.length; i++)
			fieldsName[i] = fields[i].getName();

		int j = 0;
		for (Field field : fields) {
			field.setAccessible(true);

			Object value = null;

			try {
				value = field.get(t);
				valuesForFields[j++] = value;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

		}

		String query = createUpdateQuery(fieldsName);

		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			for (int i = 0; i < valuesForFields.length; i++)
				if (valuesForFields[i].getClass().getSimpleName().equals("Integer"))
					statement.setInt(i + 1, ((Integer) valuesForFields[i]).intValue());
				else if (valuesForFields[i].getClass().getSimpleName().equals("String"))
					statement.setString(i + 1, ((String) valuesForFields[i]));
				else if (valuesForFields[i].getClass().getSimpleName().equals("Float"))
					statement.setFloat(i + 1, ((Float) valuesForFields[i]).floatValue());
			statement.setInt(valuesForFields.length + 1, id);
			statement.executeUpdate();

		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:updateTheDB " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}

		return t;
	}

	/**
	 * metoda sterge obiectul primit din tabela de catre care este apelata
	 * 
	 * @param t un obiect generic
	 */
	public void delete(T t) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		Field[] fields = type.getDeclaredFields();
		String[] fieldsName = new String[fields.length];
		Object[] valuesForFields = new Object[fields.length];
		for (int i = 0; i < fields.length; i++)
			fieldsName[i] = fields[i].getName();

		int j = 0;
		for (Field field : fields) {
			field.setAccessible(true);

			Object value = null;

			try {
				value = field.get(t);
				valuesForFields[j++] = value;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		String query = createDeleteQuery(fieldsName);

		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			statement.setInt(1, ((Integer) valuesForFields[0]).intValue());
			statement.executeUpdate();

		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:deleteFromDB " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
	}
}
