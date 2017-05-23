/*
 * Copyright 2007 - 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sf.jailer.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;

import net.sf.jailer.database.SQLDialect;
import net.sf.jailer.datamodel.DataModel;
import net.sf.jailer.datamodel.Table;
import net.sf.jailer.entitygraph.EntityGraph;

/**
 * Some utility methods.
 * 
 * @author Ralf Wisser
 */
public class SqlUtil {
	
	/**
	 * Change alias A to B and B to A in a SQL-condition.
	 * 
	 * @param condition the condition
	 * @return condition with revered aliases
	 */
	public static String reversRestrictionCondition(String condition) {
		final String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_";
		StringBuffer reversed = new StringBuffer("");
		for (int i = 0; i < condition.length(); ++i) {
			char c = condition.charAt(i);
			if (c == 'A' || c == 'B' || c == 'a' || c == 'b') {
				if (i + 1 < condition.length() && condition.charAt(i + 1) == '.') {
					if (i == 0 || chars.indexOf(condition.charAt(i - 1)) < 0) {
						reversed.append(c == 'A' || c == 'a'? 'B' : 'A');
						continue;
					}
				}
			}
			reversed.append(c);
		}
		return reversed.toString();
	}
	
	/**
	 * Replaces the aliases A and B with given aliases in a SQL-condition.
	 * 
	 * @param condition the condition
	 * @param aliasA alias for A
	 * @param aliasB alias for B
	 * @return condition with replaced aliases
	 */
	public static String replaceAliases(String condition, String aliasA, String aliasB) {
		final String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_";
		StringBuffer result = new StringBuffer("");
		for (int i = 0; i < condition.length(); ++i) {
			char c = condition.charAt(i);
			if (c == 'A' || c == 'B' || c == 'a' || c == 'b') {
				if (i + 1 < condition.length() && condition.charAt(i + 1) == '.') {
					if (i == 0 || chars.indexOf(condition.charAt(i - 1)) < 0) {
						String alias = c == 'A' || c == 'a'? aliasA : aliasB;
						if (alias == null) {
							++i; // skip '.'
						} else {
							result.append(alias);
						}
						continue;
					}
				}
			}
			result.append(c);
		}
		return result.toString();
	}
	
	/**
	 * Replaces the alias T with given alias in a SQL-condition.
	 * 
	 * @param condition the condition
	 * @param alias alias for T
	 * @return condition with replaced aliases
	 */
	public static String replaceAlias(String condition, String alias) {
		final String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_";
		StringBuffer result = new StringBuffer("");
		for (int i = 0; i < condition.length(); ++i) {
			char c = condition.charAt(i);
			if (c == 'T' || c == 't') {
				if (i + 1 < condition.length() && condition.charAt(i + 1) == '.') {
					if (i == 0 || chars.indexOf(condition.charAt(i - 1)) < 0) {
						result.append(alias);
						continue;
					}
				}
			}
			result.append(c);
		}
		return result.toString();
	}
	
	/**
	 * Resolves the pseudo-columns in a restriction condition.
	 * 
	 * @param condition the condition
	 * @param entityAAlias alias for entity table joined with A
	 * @param entityBAlias alias for entity table joined with B
	 * @param birthdayOfSubject birthday of subject
	 * @param today today
	 * @param inDeleteMode 
	 */
	public static String resolvePseudoColumns(String condition, String entityAAlias, String entityBAlias, int today, int birthdayOfSubject, boolean inDeleteMode) {
		return resolvePseudoColumns(condition, entityAAlias, entityBAlias, today, birthdayOfSubject, "birthday", inDeleteMode);
	}

	/**
	 * Resolves the pseudo-columns in a restriction condition.
	 * 
	 * @param condition the condition
	 * @param entityAAlias alias for entity table joined with A
	 * @param entityBAlias alias for entity table joined with B
	 * @param birthdayOfSubject birthday of subject
	 * @param today today
	 * @param birthdayColumnName name of the column which holds the birthday of an entity ('birthday' or 'orig_birthday')
	 * @param inDeleteMode 
	 */
	public static String resolvePseudoColumns(String condition, String entityAAlias, String entityBAlias, int today, int birthdayOfSubject, String birthdayColumnName, boolean inDeleteMode) {
		String aBirthday = entityAAlias == null? "" + (today - birthdayOfSubject) : ("(" + entityAAlias + "." + birthdayColumnName + " - " + birthdayOfSubject + ")");
		String bBirthday = entityBAlias == null? "" + (today - birthdayOfSubject) : ("(" + entityBAlias + "." + birthdayColumnName + " - " + birthdayOfSubject + ")");
		String aIsSubject = entityAAlias == null? "(" + (today - birthdayOfSubject) + " = 0)" : ("(" + entityAAlias + "." + birthdayColumnName + " - " + birthdayOfSubject + " = 0)");
		String bIsSubject = entityBAlias == null? "(" + (today - birthdayOfSubject) + " = 0)" : ("(" + entityBAlias + "." + birthdayColumnName + " - " + birthdayOfSubject + " = 0)");

		condition = condition.replaceAll("(?i:a\\s*\\.\\s*\\$distance)", Matcher.quoteReplacement(aBirthday));
		condition = condition.replaceAll("(?i:b\\s*\\.\\s*\\$distance)", Matcher.quoteReplacement(bBirthday));
		condition = condition.replaceAll("(?i:a\\s*\\.\\s*\\$is_subject)", Matcher.quoteReplacement(aIsSubject));
		condition = condition.replaceAll("(?i:b\\s*\\.\\s*\\$is_subject)", Matcher.quoteReplacement(bIsSubject));
		
		condition = condition.replaceAll("(?i:\\$in_delete_mode)", Matcher.quoteReplacement(inDeleteMode? "(1=1)" : "(1=0)"));
		
		return condition;
	}

	/**
	 * Resolves the pseudo-columns in a restriction condition.
	 * 
	 * @param condition the condition
	 * @param birthdayOfSubject birthday of subject
	 * @param today today
	 * @param reversed 
	 * @param inDeleteMode 
	 */
	public static String resolvePseudoColumns(String condition, int today, int birthdayOfSubject, boolean reversed, boolean inDeleteMode) {
		int da = reversed? 0 : 1;
		int db = reversed? 1 : 0;
		String aBirthday = "" + (today - birthdayOfSubject - da);
		String bBirthday = "" + (today - birthdayOfSubject - db);
		String aIsSubject = "(" + (today - birthdayOfSubject - da) + " = 0)";
		String bIsSubject = "(" + (today - birthdayOfSubject - db) + " = 0)";

		condition = condition.replaceAll("(?i:a\\s*\\.\\s*\\$distance)", Matcher.quoteReplacement(aBirthday));
		condition = condition.replaceAll("(?i:b\\s*\\.\\s*\\$distance)", Matcher.quoteReplacement(bBirthday));
		condition = condition.replaceAll("(?i:a\\s*\\.\\s*\\$is_subject)", Matcher.quoteReplacement(aIsSubject));
		condition = condition.replaceAll("(?i:b\\s*\\.\\s*\\$is_subject)", Matcher.quoteReplacement(bIsSubject));

		condition = condition.replaceAll("(?i:$in_delete_mode)", Matcher.quoteReplacement(inDeleteMode? "(1=1)" : "(1=0)"));
		
		return condition;
	}

	/**
	 * Reads a table-list from CSV-file.
	 * 
	 * @param dataModel to get tables from
	 * @param tableFile the file containing the list
	 * @return set of tables, empty list if file contains no tables
	 */
	public static Set<Table> readTableList(CsvFile tableFile, DataModel dataModel, Map<String, String> sourceSchemaMapping) {
		Set<Table> tabuTables = new HashSet<Table>();
		
		if (tableFile != null) {
			for (CsvFile.Line line: tableFile.getLines()) {
				String name = mappedSchema(sourceSchemaMapping, line.cells.get(0));
				Table table = dataModel.getTable(name);
				if (table == null) {
					throw new RuntimeException(line.location + ": unknown table: '" + name + "'");
				}
				tabuTables.add(table);
			}
		}
		return tabuTables;
	}

	/**
	 * Replaces schema of qualified table name according to a schema-map. 
	 * 
	 * @param schemaMapping the mapping
	 * @param tableName the table name
	 * @return table name with replaced schema
	 */
	public static String mappedSchema(Map<String, String> schemaMapping, String tableName) {
		if (schemaMapping == null) {
			return tableName;
		}
		Table t = new Table(tableName, null, false, false);
		String schema = t.getOriginalSchema("");
		String mappedSchema = schemaMapping.get(schema);
		if (mappedSchema != null) {
			schema = mappedSchema;
		}
		if (schema.length() == 0) {
			return t.getUnqualifiedName();
		}
		return schema + "." + t.getUnqualifiedName();
	}

	/**
	 * List of all jailer tables (upper case).
	 */
	public static final List<String> JAILER_TABLES;
	static {
		JAILER_TABLES = new ArrayList<String>();
		JAILER_TABLES.add(EntityGraph.ENTITY_GRAPH);
		JAILER_TABLES.add(EntityGraph.ENTITY_SET_ELEMENT);
		JAILER_TABLES.add(EntityGraph.ENTITY);
		JAILER_TABLES.add(EntityGraph.DEPENDENCY);
		JAILER_TABLES.add(SQLDialect.CONFIG_TABLE_);
		JAILER_TABLES.add(SQLDialect.DUAL_TABLE);
		JAILER_TABLES.add(SQLDialect.TMP_TABLE_);
	}
	
	/**
	 * List of all jailer tables (upper case).
	 */
	public static final List<String> JAILER_MH_TABLES;
	static {
		JAILER_MH_TABLES = new ArrayList<String>();
		JAILER_MH_TABLES.add(EntityGraph.ENTITY_GRAPH);
		JAILER_MH_TABLES.add(EntityGraph.ENTITY_SET_ELEMENT);
		JAILER_MH_TABLES.add(EntityGraph.ENTITY);
		JAILER_MH_TABLES.add(EntityGraph.DEPENDENCY);
		JAILER_MH_TABLES.add(SQLDialect.CONFIG_TABLE_);
	}
	
	/**
	 * Gets type of column from result-set.
	 * 
	 * @param resultSet result-set
	 * @param i column index
	 * @param typeCache for caching types
	 * @return type according to {@link Types}
	 */
	public static int getColumnType(ResultSet resultSet, ResultSetMetaData resultSetMetaData, int i, Map<Integer, Integer> typeCache) throws SQLException {
		Integer type = typeCache.get(i);
		if (type == null) {
			try {
				type = resultSetMetaData.getColumnType(i);
			} catch (Exception e) {
				type = Types.OTHER;
			}
			typeCache.put(i, type);
		}
		return type;
	};
	
	/**
	 * Gets type of column from result-set.
	 * 
	 * @param resultSet result-set
	 * @param columnName column name
	 * @param typeCache for caching types
	 * @return object
	 */
	public static int getColumnType(ResultSet resultSet, ResultSetMetaData resultSetMetaData, String columnName, Map<String, Integer> typeCache) throws SQLException {
		Integer type = typeCache.get(columnName);
		if (type == null) {
			try {
				type = Types.OTHER;
				for (int i = resultSetMetaData.getColumnCount(); i > 0; --i) {
					if (columnName.equalsIgnoreCase(resultSetMetaData.getColumnLabel(i))) {
						type = resultSetMetaData.getColumnType(i);
						break;
					}
				}
			} catch (Exception e) {
			}
			typeCache.put(columnName, type);
		}
		return type;
	}
	
	/**
	 * Splits a DML statement into several lines with limited length.
	 * 
	 * @param sql the DML statement
	 * @param maxLength maximum line length
	 * @return DML statement with line breaks
	 */
	public static String splitDMLStatement(String sql, int maxLength) {
		if (sql.length() <= maxLength) {
			return sql;
		}
		StringBuilder sb = new StringBuilder();
		int lastBreak = -1;
		int currentLength = 0;
		boolean inLiteral = false;
		for (int i = 0; i < sql.length(); ++i) {
			char c = sql.charAt(i);
			
			if (currentLength >= maxLength) {
				if (inLiteral && lastBreak <= 0) {
					if (i + 1 < sql.length() && sql.charAt(i + 1) != '\'') {
						sb.append("'||\n'");
						currentLength = 3;
						lastBreak = -1;
					}
				} else if (lastBreak > 0) {
					sb.insert(lastBreak + 1, "\n");
					currentLength = sb.length() - lastBreak - 2;
					lastBreak = -1;
				}
			}
			
			if ((!inLiteral) && (c == ' ' || c == ',')) {
				lastBreak = sb.length();
			} else if (c == '\n') {
				currentLength = 0;
				lastBreak = -1;
			}
			
			++currentLength;
			sb.append(c);
			if (c == '\'') {
				inLiteral = !inLiteral;
			}
		}
		return sb.toString();
	}

	public static final String LETTERS_AND_DIGITS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_0123456789";

	/**
	 * Maps SQL types from {@link java.sql.Types} to clear text types.
	 */
	public final static Map<Integer, String> SQL_TYPE;
	static {
		SQL_TYPE = new HashMap<Integer, String>();
		SQL_TYPE.put(Types.BIGINT, "BIGINT");
		SQL_TYPE.put(Types.BINARY, "BINARY");
		SQL_TYPE.put(Types.BIT, "BIT");
		SQL_TYPE.put(Types.CHAR, "CHAR");
		SQL_TYPE.put(Types.DATE, "DATE");
		SQL_TYPE.put(Types.DECIMAL, "DECIMAL");
		SQL_TYPE.put(Types.DOUBLE, "DOUBLE");
		SQL_TYPE.put(Types.FLOAT, "FLOAT");
		SQL_TYPE.put(Types.INTEGER, "INTEGER");
		SQL_TYPE.put(Types.NUMERIC, "NUMERIC");
		SQL_TYPE.put(Types.TIME, "TIME");
		SQL_TYPE.put(Types.TIMESTAMP, "TIMESTAMP");
		SQL_TYPE.put(Types.TINYINT, "TINYINT");
		SQL_TYPE.put(Types.VARCHAR, "VARCHAR");
		SQL_TYPE.put(Types.SMALLINT, "SMALLINT");
		SQL_TYPE.put(Types.CLOB, "CLOB");
		SQL_TYPE.put(Types.NCLOB, "NCLOB");
		SQL_TYPE.put(Types.BLOB, "BLOB");
	}
	
}