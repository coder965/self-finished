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
package org.tinygroup.jsqlparser.statement.create.table;

import java.util.List;
import org.tinygroup.jsqlparser.schema.Table;
import org.tinygroup.jsqlparser.statement.select.PlainSelect;

/**
 * Foreign Key Index
 * @author toben
 */
public class ForeignKeyIndex extends NamedConstraint {
	private Table table;
	private List<String> referencedColumnNames;

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public List<String> getReferencedColumnNames() {
		return referencedColumnNames;
	}

	public void setReferencedColumnNames(List<String> referencedColumnNames) {
		this.referencedColumnNames = referencedColumnNames;
	}

	@Override
	public String toString() {
		return super.toString()
				+ " REFERENCES " + table + PlainSelect.getStringList(getReferencedColumnNames(), true, true);
	}
}
