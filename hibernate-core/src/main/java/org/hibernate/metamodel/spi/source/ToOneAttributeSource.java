/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2011, Red Hat Inc. or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Inc.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package org.hibernate.metamodel.spi.source;

import java.util.List;

import org.hibernate.metamodel.internal.Binder;
import org.hibernate.metamodel.spi.binding.AttributeBinding;
import org.hibernate.metamodel.spi.binding.SingularAttributeBinding;
import org.hibernate.type.ForeignKeyDirection;

/**
 * Further contract for sources of {@code *-to-one} style associations.
 *
 * @author Steve Ebersole
 */
public interface ToOneAttributeSource
		extends SingularAttributeSource, ForeignKeyContributingSource, FetchableAttributeSource, CascadeStyleSource {

	/**
	 * Obtain the name of the referenced entity.
	 *
	 * @return The name of the referenced entity
	 */
	public String getReferencedEntityName();
	public boolean isUnWrapProxy();
	ForeignKeyDirection getForeignKeyDirection();
	public List<Binder.DefaultNamingStrategy> getDefaultNamingStrategies(final String entityName, final String tableName, final AttributeBinding referencedAttributeBinding);
}