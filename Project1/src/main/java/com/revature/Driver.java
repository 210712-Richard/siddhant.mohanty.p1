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
		try {
			Thread.sleep(30000); // wait 30 seconds
		} catch(Exception e) {
			e.printStackTrace();
		}
		DataBaseCreator.createTables();
		try {
			Thread.sleep(20000); // wait 20 seconds
		} catch(Exception e) {
			e.printStackTrace();
		}
		DataBaseCreator.populateEmployeeTable();
		DataBaseCreator.populateFormTable();
		System.exit(0);
	}
}

	