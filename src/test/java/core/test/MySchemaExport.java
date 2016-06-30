package core.test;

import java.util.Properties;

import javax.persistence.spi.PersistenceUnitInfo;

import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.nhnnext.NextTrelloApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;



public class MySchemaExport {

	public static void main(String[] args) {
		
		AnnotationConfigApplicationContext acac = new AnnotationConfigApplicationContext(NextTrelloApplication.class);
		LocalContainerEntityManagerFactoryBean lcemfb = acac.getBean(LocalContainerEntityManagerFactoryBean.class);
		final Properties prop = new Properties();
		prop.put(AvailableSettings.DIALECT, "org.hibernate.dialect.H2Dialect");
		final PersistenceUnitInfo info = lcemfb.getPersistenceUnitInfo();
		final PersistenceUnitInfoDescriptor puid = new PersistenceUnitInfoDescriptor(info);
		final EntityManagerFactoryBuilderImpl emfbi = new EntityManagerFactoryBuilderImpl(puid, prop);
		final ServiceRegistry serviceRegistry = emfbi.buildServiceRegistry();
		final Configuration configuration = emfbi.buildHibernateConfiguration(serviceRegistry);
		final SchemaExport schemaExport = new SchemaExport(serviceRegistry, configuration);
		schemaExport.setFormat(true);
		schemaExport.setDelimiter(";");
		schemaExport.execute(true, false, false, true);
		acac.close();
		

	}

}
