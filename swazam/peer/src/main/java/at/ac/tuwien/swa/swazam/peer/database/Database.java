package at.ac.tuwien.swa.swazam.peer.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import ac.at.tuwien.infosys.swa.audio.SubFingerprint;

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
			String sql = "CREATE TABLE fingerprints (name VARCHAR(255), start DOUBLE, shift DOUBLE, fingerprint LONGVARCHAR, PRIMARY KEY(fingerprint))";
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			logger.debug(e.getLocalizedMessage(), e);
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
				String sql = "INSERT INTO fingerprints (name, start, shift, fingerprint) VALUES (?, ?, ?, ?)";
				PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
				preparedStatement.setString(1, name);
				preparedStatement.setDouble(2, fingerprint.getStartTime());
				preparedStatement.setDouble(3, fingerprint.getShiftDuration());
				preparedStatement.setString(4, fingerprint.toString());
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
			String sql = "SELECT name, start, shift, fingerprint FROM fingerprints";
			PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Fingerprint tmpFingerprint = this.deserializeFingerprint(resultSet.getDouble("start"), resultSet.getDouble("shift"), resultSet.getString("fingerprint"));
				if (fingerprint.match(tmpFingerprint) == 0.0) {
					// found audio
					
					name = resultSet.getString("name");
					
					break;
				}
			}
		} catch (SQLException e) {
			logger.error(e.getLocalizedMessage(), e);
		}
		
		return name;
	}
	
	public Map<Fingerprint, String> getFingerprints() {
		Map<Fingerprint, String> fingerprints = new HashMap<Fingerprint, String>();
		
		try {
			String sql = "SELECT name, start, shift, fingerprint FROM fingerprints";
			PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				fingerprints.put(this.deserializeFingerprint(resultSet.getDouble("start"), resultSet.getDouble("shift"), resultSet.getString("fingerprint")), resultSet.getString("name"));
			}
		} catch (SQLException e) {
			logger.error(e.getLocalizedMessage(), e);
		}
		
		return fingerprints;
	}
	
	/**
	 * Deserializes a fingerprint from a string
	 * 
	 * @param string Serialized Fingerprint
	 * @return Fingerprint
	 */
	public Fingerprint deserializeFingerprint(Double start, Double shift, String string) {
		String[] strings = string.split(System.getProperty("line.separator"));
		
		Collection<SubFingerprint> subFingerprints = new LinkedList<SubFingerprint>();
		for (String subString : strings) {
			SubFingerprint subFingerprint = this.deserializeSubFingerprint(subString);
			
			subFingerprints.add(subFingerprint);
		}
		
		Fingerprint fingerprint = new Fingerprint(start, shift, subFingerprints);
		
		return fingerprint;
	}
	
	public SubFingerprint deserializeSubFingerprint(String string) {
		int value = 0;
		
		if (string.charAt(0) == 1) {
			value = Integer.MIN_VALUE;
		}
		
		for (int i = 1; i < string.length(); i++) {
			if (string.charAt(i) == '1') {
				value += Math.pow(2, (string.length() - 1) - i);
			}
		}
		
		SubFingerprint subFingerprint = new SubFingerprint(value);
		
		return subFingerprint;
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
