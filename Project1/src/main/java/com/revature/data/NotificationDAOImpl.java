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
	public void addNotification(String recipient, String message) {
		String query = "Insert into notifications (recipient, message) values (?, ?);";
		SimpleStatement s = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM)
				.build();
		BoundStatement bound = session.prepare(s).bind(recipient, message);
		session.execute(bound);
	}

	@Override
	public List<String> getNotifications(String recipient) {
		List<String> notifications = new ArrayList<String>(); 
		String query = "Select message from notifications where recipient=?";
		SimpleStatement s = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM)
				.build();
		BoundStatement bound = session.prepare(s).bind(recipient);
		ResultSet rs = session.execute(bound);
		rs.forEach(row -> {
			notifications.add(row.getString("message"));
		});
		return notifications;
	}

}
