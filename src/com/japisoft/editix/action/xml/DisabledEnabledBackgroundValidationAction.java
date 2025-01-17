package com.japisoft.editix.action.xml;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.Icon;

import com.japisoft.editix.ui.EditixFrame;
import com.japisoft.editix.ui.EditixStatusBar;
import com.japisoft.framework.actions.SynchronizableAction;
import com.japisoft.framework.preferences.Preferences;
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
public class DisabledEnabledBackgroundValidationAction extends AbstractAction {
	
	public void actionPerformed( ActionEvent e ) {
		Preferences.setPreference( "xmlconfig", "backgroundValidation", ( ( AbstractButton )e.getSource() ).isSelected() );
		if ( ( ( AbstractButton )e.getSource() ).isSelected() ) {
			( ( AbstractButton )e.getSource() ).setIcon(com.japisoft.framework.app.toolkit.Toolkit
					.getImageIcon( "images/check.png" ) );
		} else {
			( ( AbstractButton )e.getSource() ).setIcon( null );
		}
	}

}
