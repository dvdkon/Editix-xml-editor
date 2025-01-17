package com.japisoft.editix.action.xml.refactor;

import com.japisoft.editix.action.xml.format.FormatAction;
import com.japisoft.editix.ui.EditixFactory;
import com.japisoft.editix.ui.EditixFrame;
import com.japisoft.framework.xml.parser.node.FPNode;
import com.japisoft.framework.xml.refactor2.AbstractRefactor;
import com.japisoft.framework.xml.refactor2.elements.RenameElementPrefix;
import com.japisoft.xmlpad.XMLContainer;

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
public class RefactorRenameElementPrefixAction extends FormatAction {

	protected boolean isIllegalName( String txt ) {
		if ( "".equals( txt ) )
			return true;
		for ( int i = 0; i < txt.length(); i++ )
			if ( Character.isWhitespace( txt.charAt( i ) ) )
				return true;
		return false;
	}

	private String oldValue;
	
	public void setName( String prefix ) {
		this.oldValue = prefix;
	}
	
	protected AbstractRefactor getRefactor() {
		
		XMLContainer container = 
			EditixFrame.THIS.getSelectedContainer();
		FPNode element = container.getCurrentElementNode();
		if ( element == null ) {
			throw new RuntimeException( "No current element" );
		}

		String newValue = 
			EditixFactory.buildAndShowInputDialog( "New name", oldValue );
		if ( newValue == null ) {
			throw new RuntimeException( "" );
		}
		
		RenameElementPrefix re = new RenameElementPrefix();
		re.setOldValue( oldValue );
		re.setNewValue( newValue );
		
		// Check for space
		if ( isIllegalName( newValue ) ) {
			throw new RuntimeException( "Illegal name" );
		}
		
		return re;
	}

}
