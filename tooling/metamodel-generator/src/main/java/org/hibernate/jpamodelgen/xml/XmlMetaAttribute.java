/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.jpamodelgen.xml;

import org.hibernate.jpamodelgen.model.MetaAttribute;
import org.hibernate.jpamodelgen.model.MetaEntity;

/**
 * @author Hardy Ferentschik
 */
public abstract class XmlMetaAttribute implements MetaAttribute {

	private final XmlMetaEntity hostingEntity;
	private final String propertyName;
	private final String type;

	XmlMetaAttribute(XmlMetaEntity parent, String propertyName, String type) {
		this.hostingEntity = parent;
		this.propertyName = propertyName;
		this.type = type;
	}

	@Override
	public String getDeclarationString() {
		return "public static volatile " + hostingEntity.importType( getMetaType() )
				+ "<" + hostingEntity.importType( hostingEntity.getQualifiedName() )
				+ ", " + hostingEntity.importType( getTypeDeclaration() )
				+ "> " + getPropertyName() + ";";
	}

	public String getPropertyName() {
		return propertyName;
	}

	public String getTypeDeclaration() {
		return type;
	}

	public MetaEntity getHostingEntity() {
		return hostingEntity;
	}

	@Override
	public abstract String getMetaType();

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append( "XmlMetaAttribute" );
		sb.append( "{propertyName='" ).append( propertyName ).append( '\'' );
		sb.append( ", type='" ).append( type ).append( '\'' );
		sb.append( '}' );
		return sb.toString();
	}
}
