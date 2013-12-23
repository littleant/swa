package at.ac.tuwien.peer.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;

/**
 * Abstract database object to save and identify fingerprints and songs
 * 
 * @author Andreas
 */
public class Database {
	final Logger logger = LoggerFactory.getLogger(Database.class);
	private Connection connection;
	
	public Database() {
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			this.connection = DriverManager.getConnection("jdbc:hsqldb:mem:fingerprints", "SA", "");
			
			// create tables
			Statement statement = this.connection.createStatement();
			String sql = "CREATE TABLE fingerprints (name VARCHAR(255), fingerprint LONGVARCHAR, PRIMARY KEY(fingerprint))";
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			logger.error(e.getLocalizedMessage(), e);
		} catch (ClassNotFoundException e) {
			logger.error(e.getLocalizedMessage(), e);
		}
	}
	
	/**
	 * Save Fingerprints to database
	 * 
	 * @param fingerprints The fingerprints to save
	 */
	public void saveFingerprints(Map<String, Fingerprint> fingerprints) {
		Iterator<String> iterator = fingerprints.keySet().iterator();
		String name;
		Fingerprint fingerprint;
		while (iterator.hasNext()) {
			name = iterator.next();
			fingerprint = fingerprints.get(name);
			
			try {
				String sql = "INSERT INTO fingerprints (name, fingerprint) VALUES (?, ?)";
				PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
				preparedStatement.setString(1, name);
				preparedStatement.setString(2, fingerprint.toString());
				preparedStatement.execute();
			} catch (SQLException e) {
				logger.error(e.getLocalizedMessage(), e);
			}
		}
	}
	
	/**
	 * Identify a fingerprint
	 * 
	 * @param fingerprint Fingerprint to check the database against
	 * @return Name of the identified fingerprint, null if not found
	 */
	public String identifyFingerprint(Fingerprint fingerprint) {
		String name = null;
		try {
			String sql = "SELECT name FROM fingerprints WHERE fingerprint = ?";
			PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
			preparedStatement.setString(1, fingerprint.toString());
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				name = resultSet.getString("name");
			}
		} catch (SQLException e) {
			logger.error(e.getLocalizedMessage(), e);
		}
		
		return name;
	}
	
	/**
	 * Close the database connection
	 */
	public void close() {
		try {
			this.connection.close();
		} catch (SQLException e) {
			logger.debug(e.getLocalizedMessage(), e);
		}
	}
}
