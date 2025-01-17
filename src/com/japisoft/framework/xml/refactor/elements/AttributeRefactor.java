package com.japisoft.framework.xml.refactor.elements;

import org.w3c.dom.Attr;
import org.w3c.dom.Node;

import com.japisoft.framework.xml.parser.node.FPNode;
import com.japisoft.framework.xml.refactor.ui.RefactorTable;

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
public class AttributeRefactor extends AbstractRefactor {
	private static final String RENAME_ACTION = "(V1) RENAME TO (V2)";
	private static final String DELETE_ACTION = "DELETE (V1)";

	public static String[] ACTIONS = new String[] { RENAME_ACTION,
			DELETE_ACTION, };

	public AttributeRefactor() {
		super(Node.ATTRIBUTE_NODE);
	}

	public String[] getActions() {
		return ACTIONS;
	}

	public String getName() {
		return "Attribute";
	}
	
	protected Node refactorIt(Node node, RefactorAction ra) {

		if ( DELETE_ACTION.equals( ra.getAction() ) ) {
			if ( ra.matchOldValue( node.getNodeName() ) )
				return null;
		} else
		if ( RENAME_ACTION.equals( ra.getAction() ) ) {
			if ( ra.matchOldValue( node.getNodeName() ) ) {
				if ( !ra.isNewValueEmpty() )
					return new AttrProxyNode( ( Attr )node, ra.getNewValue() );
			}
		}
		return node;
	}

	public void initTable(RefactorTable table, FPNode context) {
		for ( int i = 0; i < context.getViewAttributeCount() ; i++ ) {
			table.init( i, context.getViewAttributeAt( i ) );
		}
	}	

}
