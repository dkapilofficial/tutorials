/**
 * This class is generated by jOOQ
 */
package com.baeldung.jooq.introduction.db.information_schema.tables;


import com.baeldung.jooq.introduction.db.information_schema.InformationSchema;
import com.baeldung.jooq.introduction.db.information_schema.tables.records.DomainsRecord;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Domains extends TableImpl<DomainsRecord> {

	private static final long serialVersionUID = -742107682;

	/**
	 * The reference instance of <code>INFORMATION_SCHEMA.DOMAINS</code>
	 */
	public static final Domains DOMAINS = new Domains();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<DomainsRecord> getRecordType() {
		return DomainsRecord.class;
	}

	/**
	 * The column <code>INFORMATION_SCHEMA.DOMAINS.DOMAIN_CATALOG</code>.
	 */
	public final TableField<DomainsRecord, String> DOMAIN_CATALOG = createField("DOMAIN_CATALOG", org.jooq.impl.SQLDataType.VARCHAR.length(2147483647), this, "");

	/**
	 * The column <code>INFORMATION_SCHEMA.DOMAINS.DOMAIN_SCHEMA</code>.
	 */
	public final TableField<DomainsRecord, String> DOMAIN_SCHEMA = createField("DOMAIN_SCHEMA", org.jooq.impl.SQLDataType.VARCHAR.length(2147483647), this, "");

	/**
	 * The column <code>INFORMATION_SCHEMA.DOMAINS.DOMAIN_NAME</code>.
	 */
	public final TableField<DomainsRecord, String> DOMAIN_NAME = createField("DOMAIN_NAME", org.jooq.impl.SQLDataType.VARCHAR.length(2147483647), this, "");

	/**
	 * The column <code>INFORMATION_SCHEMA.DOMAINS.COLUMN_DEFAULT</code>.
	 */
	public final TableField<DomainsRecord, String> COLUMN_DEFAULT = createField("COLUMN_DEFAULT", org.jooq.impl.SQLDataType.VARCHAR.length(2147483647), this, "");

	/**
	 * The column <code>INFORMATION_SCHEMA.DOMAINS.IS_NULLABLE</code>.
	 */
	public final TableField<DomainsRecord, String> IS_NULLABLE = createField("IS_NULLABLE", org.jooq.impl.SQLDataType.VARCHAR.length(2147483647), this, "");

	/**
	 * The column <code>INFORMATION_SCHEMA.DOMAINS.DATA_TYPE</code>.
	 */
	public final TableField<DomainsRecord, Integer> DATA_TYPE = createField("DATA_TYPE", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>INFORMATION_SCHEMA.DOMAINS.PRECISION</code>.
	 */
	public final TableField<DomainsRecord, Integer> PRECISION = createField("PRECISION", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>INFORMATION_SCHEMA.DOMAINS.SCALE</code>.
	 */
	public final TableField<DomainsRecord, Integer> SCALE = createField("SCALE", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>INFORMATION_SCHEMA.DOMAINS.TYPE_NAME</code>.
	 */
	public final TableField<DomainsRecord, String> TYPE_NAME = createField("TYPE_NAME", org.jooq.impl.SQLDataType.VARCHAR.length(2147483647), this, "");

	/**
	 * The column <code>INFORMATION_SCHEMA.DOMAINS.SELECTIVITY</code>.
	 */
	public final TableField<DomainsRecord, Integer> SELECTIVITY = createField("SELECTIVITY", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>INFORMATION_SCHEMA.DOMAINS.CHECK_CONSTRAINT</code>.
	 */
	public final TableField<DomainsRecord, String> CHECK_CONSTRAINT = createField("CHECK_CONSTRAINT", org.jooq.impl.SQLDataType.VARCHAR.length(2147483647), this, "");

	/**
	 * The column <code>INFORMATION_SCHEMA.DOMAINS.REMARKS</code>.
	 */
	public final TableField<DomainsRecord, String> REMARKS = createField("REMARKS", org.jooq.impl.SQLDataType.VARCHAR.length(2147483647), this, "");

	/**
	 * The column <code>INFORMATION_SCHEMA.DOMAINS.SQL</code>.
	 */
	public final TableField<DomainsRecord, String> SQL = createField("SQL", org.jooq.impl.SQLDataType.VARCHAR.length(2147483647), this, "");

	/**
	 * The column <code>INFORMATION_SCHEMA.DOMAINS.ID</code>.
	 */
	public final TableField<DomainsRecord, Integer> ID = createField("ID", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * Create a <code>INFORMATION_SCHEMA.DOMAINS</code> table reference
	 */
	public Domains() {
		this("DOMAINS", null);
	}

	/**
	 * Create an aliased <code>INFORMATION_SCHEMA.DOMAINS</code> table reference
	 */
	public Domains(String alias) {
		this(alias, DOMAINS);
	}

	private Domains(String alias, Table<DomainsRecord> aliased) {
		this(alias, aliased, null);
	}

	private Domains(String alias, Table<DomainsRecord> aliased, Field<?>[] parameters) {
		super(alias, InformationSchema.INFORMATION_SCHEMA, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Domains as(String alias) {
		return new Domains(alias, this);
	}

	/**
	 * Rename this table
	 */
	public Domains rename(String name) {
		return new Domains(name, null);
	}
}
