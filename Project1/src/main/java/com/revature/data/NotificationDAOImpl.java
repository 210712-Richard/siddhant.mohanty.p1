package com.revature.data;

import java.util.ArrayList;
import java.util.List;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.DefaultConsistencyLevel;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatementBuilder;
import com.revature.util.CassandraUtil;

public class NotificationDAOImpl implements NotificationDAO {

	private CqlSession session = CassandraUtil.getInstance().getSession();
	
	@Override
	public void updateNotifications(String recipient, String message) {
		List<String> currentNotifications = getNotifications(recipient);
		currentNotifications.add(message);
		String query = "Insert into notifications (recipient, messages) values (?, ?);";
		SimpleStatement s = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM)
				.build();
		BoundStatement bound = session.prepare(s).bind(recipient, currentNotifications);
		session.execute(bound);
	}

	@Override
	public List<String> getNotifications(String recipient) {
		String query = "Select messages from notifications where recipient=?";
		SimpleStatement s = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM)
				.build();
		BoundStatement bound = session.prepare(s).bind(recipient);
		ResultSet rs = session.execute(bound);
		List<String> notifications = new ArrayList<String>();
		rs.forEach(row -> {
			List<String> notificationDB = row.getList("messages", String.class);
			for (String x : notificationDB) {
				notifications.add(x);
			}
		});
		return notifications;
	}

	@Override
	public void clearNotifications(String recipient) {
		String query = "DELETE messages FROM notifications WHERE recipient=?";
		SimpleStatement s = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM)
				.build();
		BoundStatement bound = session.prepare(s).bind(recipient);
		session.execute(bound);
	}

	@Override
	public void createWelcomeNotification(String recipient) {
		String query = "Insert into notifications (recipient, messages) values (?, ?);";
		List<String> welcomeMessage = new ArrayList<String>();
		welcomeMessage.add("Welcome!");
		SimpleStatement s = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM)
				.build();
		BoundStatement bound = session.prepare(s).bind(recipient, welcomeMessage);
		session.execute(bound);
	}

}
