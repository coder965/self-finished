/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
 *
 *  Licensed under the GPL, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/gpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.tinygroup.database.sequence.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.tinygroup.database.config.sequence.Sequence;
import org.tinygroup.database.sequence.SequenceSqlProcessor;

public abstract class AbstractSequenceSqlProcessor implements
		SequenceSqlProcessor {


	public String getDropSql(Sequence sequence) {
		return "DROP SEQUENCE "+sequence.getName();
	}
	
	public boolean checkSequenceExist(Sequence sequence, Connection connection)throws SQLException{
		
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			String sql=getQuerySql(sequence);
			resultSet = statement.executeQuery(sql);
			if(resultSet.next()){
				return true;
			}
		} finally{
			if(statement!=null){
				statement.close();
			}
			if(resultSet!=null){
				resultSet.close();
			}
		}

		return false;
		
	}

	protected abstract String getQuerySql(Sequence sequence);

}
