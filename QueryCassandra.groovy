@Grab('com.datastax.cassandra:cassandra-driver-core:2.1.10.3')

import com.datastax.driver.core.Cluster

def cluster = new Cluster.Builder()
	.addContactPoints('127.0.0.1', '127.0.0.2', '127.0.0.2')
	.withPort(9042)
	.build();

cluster.withCloseable({
		def session = cluster.newSession()
		session.withCloseable({
			session.execute('select * from testv2.books;').all().each({ r ->
						println String.join(" | ", r.getString(0), r.getString(1), r.getInt(2) as String)
					}
				)
			}
		)
	}
)

