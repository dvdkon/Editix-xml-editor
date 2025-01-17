package com.japisoft.framework.xml.refactors.elements.xslt;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.japisoft.framework.xml.parser.node.FPNode;
import com.japisoft.framework.xml.refactor.elements.AbstractRefactor;
import com.japisoft.framework.xml.refactor.elements.RefactorAction;
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
public class VariableRefactor extends AbstractRefactor {
	private static final String RENAME_ACTION = "(V1) RENAME TO (V2)";
	private static final String DELETE_ACTION = "DELETE (V1)";

	public static String[] ACTIONS = new String[] {
		RENAME_ACTION,
		DELETE_ACTION,
	};

	public VariableRefactor( ) {
		super( Node.ELEMENT_NODE );
	}

	public String[] getActions() {
		return ACTIONS;
	}

	public String getName() {
		return "xslt Variable";
	}

	public boolean isDefault() {
		return false;
	}

	boolean variableFound = false;
	
	protected Node refactorIt( Node node, RefactorAction ra ) {
		if ( "variable".equals( node.getLocalName() ) ) {
			Element e = ( Element )node;
			if ( ra.matchOldValue( e.getAttribute( "name" ) ) ) {
				variableFound = true;
				if ( RENAME_ACTION.equals( ra.getAction() ) ) {
					if ( !ra.isNewValueEmpty() )
						e.setAttribute( "name", ra.getNewValue() );					
				} else 
				if ( DELETE_ACTION.equals( ra.getAction() ) ) {
					return null;
				}
			}
		} else {
			if ( variableFound ) {
				if ( "value-of".equals( node.getLocalName() ) || 
					"variable".equals( node.getLocalName() ) ) {
					Element e = ( Element )node;
					String value = e.getAttribute( "select" );
					if ( value != null ) {
						String newValue = ( "$" + ra.getNewValue() );
						if ( DELETE_ACTION.equals( ra.getAction() ) )
							newValue = "";
						value = value.replace( 
								"$" + ra.getOldValue(), 
								newValue );
						e.setAttribute( "select", value );
					}
				}
			}
		}
		return node;
	}

	public void stop() {
		variableFound = false;
	}

	public void initTable(RefactorTable table, FPNode context) {
		if ( context.matchContent( "variable" ) ) {
			if ( context.hasAttribute( "name" ) )
				table.init( 0, context.getAttribute( "name" ) );
		}
	}
		
}
