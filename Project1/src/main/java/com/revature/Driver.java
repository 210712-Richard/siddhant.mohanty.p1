package com.revature;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.javalin.Javalin;
import io.javalin.plugin.json.JavalinJackson;

public class Driver {
	public static void main(String[] args) {
		instantiateDatabase();
		//javalin();
	}

	public static void instantiateDatabase() {
		DataBaseCreator.dropTables();
		System.out.println("Tables dropped");
		try {
			Thread.sleep(40000); // wait 40 seconds
		} catch(Exception e) {
			e.printStackTrace();
		}
		DataBaseCreator.createTables();
		System.out.println("Tables created");
		try {
			Thread.sleep(50000); // wait 50 seconds
		} catch(Exception e) {
			e.printStackTrace();
		}
		DataBaseCreator.populateEmployeeTable();
		DataBaseCreator.populateFormTable();
		DataBaseCreator.populateNotificationsTable();
		DataBaseCreator.populateDepartmentsTable();
		System.out.println("Tables populated");
		System.exit(0);
	}
}

	