package com.japisoft.framework.xml.refactor.elements;

import org.w3c.dom.EntityReference;
import org.w3c.dom.Node;

/**
This program is available under two licenses : 

1. GPL license for non commercial and commercial usage : 

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

2. For commercial usage :

You need to get a commercial license for source usage at : 

http://www.editix.com/buy.html

Copyright (c) 2018 Alexandre Brillant - JAPISOFT SARL - http://www.japisoft.com

@author Alexandre Brillant - abrillant@japisoft.com
@author JAPISOFT SARL - http://www.japisoft.com

*/
public class EntityRefactor extends AbstractRefactor {
	private static final String RENAME_ACTION = "(V1) RENAME TO (V2)";
	private static final String DELETE_ACTION = "DELETE (V1)";
	private static final String CONVERT_VALUE_ACTION = "CONVERT (V1) TO DTD VALUE";

	public static String[] ACTIONS = new String[] { 
		RENAME_ACTION,
		DELETE_ACTION
		// CONVERT_VALUE_ACTION 
	};

	public EntityRefactor() {
		super(Node.ENTITY_REFERENCE_NODE);
	}

	public String[] getActions() {
		return ACTIONS;
	}

	public String getName() {
		return "Entity";
	}

	protected Node refactorIt( Node node, RefactorAction ra ) {
		if ( DELETE_ACTION.equals( ra.getAction() ) ) {
			if ( ra.matchOldValue( node.getNodeName() ) )
				return null;
		} else if ( RENAME_ACTION.equals( ra.getAction() ) ) {
			if ( ra.matchOldValue(node.getNodeName() ) ) {
				if ( !ra.isNewValueEmpty() )
					return new EntityProxyNode((EntityReference) node, ra.getNewValue());
			}
		} else if ( CONVERT_VALUE_ACTION.equals( ra.getAction() ) ) {
			// Create a text node with the entity value
			if ( ra.matchOldValue( node.getNodeName() ) ) {
				EntityReference er = ( EntityReference )node;				
				return node.getOwnerDocument().createTextNode( node.getNodeValue() );
			}
		}
		return node;
	}

}
