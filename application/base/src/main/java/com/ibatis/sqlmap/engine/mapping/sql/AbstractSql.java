/*
 *  Copyright 2004 Clinton Begin
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.ibatis.sqlmap.engine.mapping.sql;

import com.bench.app.stockmanage.base.database.DatabaseConstant;
import com.bench.app.stockmanage.base.utils.StringUtils;
import com.ibatis.sqlmap.engine.scope.StatementScope;

public abstract class AbstractSql implements Sql {

	public String getFormatedSql(String sql, StatementScope statementScope, Object parameterObject) {
		if (statementScope == null) {
			return sql;
		}
		// Ìæ»»schema
		try {
			String schema = statementScope.getSession().getSqlMapTxMgr().getCurrentConnection().getSchema();
			if (StringUtils.isEmpty(schema)) {
				schema = statementScope.getSession().getSqlMapTxMgr().getCurrentConnection().getCatalog();
			}
			return StringUtils.replace(sql, DatabaseConstant.SCHEMA_PLACEHODER, schema);
		} catch (Exception e) {
			throw new RuntimeException("ÎÞ·¨Ìæ»»schema", e);
		}
	}

}
