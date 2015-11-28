/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 *
 */


package org.hibernate.dialect;
import org.hibernate.LockMode;
import org.hibernate.ScrollMode;
import org.hibernate.dialect.function.NoArgSQLFunction;
import org.hibernate.dialect.function.StandardJDBCEscapeFunction;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.dialect.lock.LockingStrategy;
import org.hibernate.dialect.lock.SelectLockingStrategy;
import org.hibernate.hql.spi.id.IdTableSupportStandardImpl;
import org.hibernate.hql.spi.id.MultiTableBulkIdStrategy;
import org.hibernate.hql.spi.id.global.GlobalTemporaryTableBulkIdStrategy;
import org.hibernate.hql.spi.id.local.AfterUseAction;
import org.hibernate.persister.entity.Lockable;
import org.hibernate.type.StandardBasicTypes;

import java.sql.Types;

/**
 * An CacheDialect dialect for Intersystem's Cache.
 * extension of Cache71Dialec
 *
 * intended for  Hibernate 4.3+  and jdk 1.7 or 1.8
 * 
 *  Hibernate works with Cachejdbc.jar located in cache intallation directory ..\dev\java\lib\JDK18 or \JDK17
 * 	Hibernate properties example
 * 		hibernate.dialect org.hibernate.dialect.CacheDialect
 *		hibernate.connection.driver_class com.intersys.jdbc.CacheDriver
 *		hibernate.connection.url jdbc:Cache://127.0.0.1:1972/USER/*
 *		hibernate.connection.username _SYSTEM*	
 *		hibernate.connection.password SYS*
 *   Change items marked by '*' to correspond to your system.
 *   	
 * 
 * @author Ralph Vater, Dmitry Umansky 
 * 
 **/

public class CacheDialect extends Cache71Dialect {
	 public CacheDialect()
	   {
	      super();
	      registerDatatypes();
	      registerFunctions();
	   }

	   private void registerFunctions()
	   {
	      registerFunction( "year", new StandardSQLFunction( "year", StandardBasicTypes.INTEGER ) );
	      registerFunction( "sqrt", new StandardSQLFunction( "sqrt", StandardBasicTypes.DOUBLE ) );
	      registerFunction( "log10", new StandardJDBCEscapeFunction( "log10", StandardBasicTypes.DOUBLE ) );
	      
	      registerFunction(	"current_timestamp", new NoArgSQLFunction( "current_timestamp", StandardBasicTypes.TIMESTAMP, false ));

	   }

	   /**
	    * map some undefined but common types
	    */
	   private void registerDatatypes()
	   {
	      registerColumnType(Types.BOOLEAN, "integer");
	      registerColumnType( Types.BINARY, "varbinary($l)" );
	   }

	   /**
	    * ddl like ""value" integer null check ("value">=2 AND "value"<=10)" isn't supported
	    */
	   @Override
	   public boolean supportsColumnCheck()
	   {
	      return false;
	   }

	   /**
	    * select count(distinct a,b,c) from hasi
	    * isn't supported ;)
	    */
	   @Override
	   public boolean supportsTupleDistinctCounts()
	   {
	      return false;
	   }

	   @Override
	   public boolean supportsTuplesInSubqueries() {
	      return false;
	   }

	   
	   
	   @Override
	   public LockingStrategy getLockingStrategy(Lockable lockable, LockMode lockMode)
	   {
	      // Just to make some tests happy, but Intersystems Cache doesn't really support this.
	      // need to use READ_COMMITTED as isolation level
	      if (LockMode.UPGRADE == lockMode)
	      {
	         return new SelectLockingStrategy(lockable, lockMode);
	      }
	      return super.getLockingStrategy(lockable, lockMode);
	   }

	   @Override
	   public ScrollMode defaultScrollMode()
	   {
	      return super.defaultScrollMode();
	   }
	   

	   @Override
		public MultiTableBulkIdStrategy getDefaultMultiTableBulkIdStrategy() {
			return new GlobalTemporaryTableBulkIdStrategy(
					new IdTableSupportStandardImpl() {
						@Override
						public String generateIdTableName(String baseName) {
							final String name = super.generateIdTableName( baseName );
							return name.length() > 25 ? name.substring( 1, 25 ) : name;
						}

						@Override
						public String getCreateIdTableCommand() {
							return "create global temporary table";
						}
					},
					AfterUseAction.CLEAN
			);
		}
		
		@Override
		public boolean supportsExistsInSelect() {
			return false;
		}
		
		@Override
		public String getNullColumnString() {
			return "";
		}
		
}
