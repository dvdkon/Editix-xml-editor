package com.japisoft.editix.action.file;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import com.japisoft.editix.ui.EditixFrame;
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
public class SaveAllAction extends AbstractAction {

	public static boolean RETURN_STATUS = true;

	public void actionPerformed(ActionEvent e) {
		if ( EditixFrame.THIS.isEmpty() )
			return;

		boolean modified = false;
		for ( int i = 0; i < EditixFrame.THIS.getXMLContainerCount(); i++ ) {
			XMLContainer container = EditixFrame.THIS.getXMLContainer( i );
			if ( container != null && 
					container.getEditor().isDocumentModified() ) {
				modified = true;
				break;
			}
		}

		if ( !modified ) {
			return;
		}

		SaveAllDialog cad = new SaveAllDialog();
		cad.setVisible(true);

		if (cad.isOk()) {
			RETURN_STATUS = true;
			// save the selection
			for (int i = 0; i < cad.getSelections().size(); i++) {
				boolean ok =
					((Boolean) cad.getSelections().get(i)).booleanValue();
				if (ok) {
					EditixFrame.THIS.activeXMLContainer(
						i);
					SaveAction.save_action();
				}
			}
		} else
			RETURN_STATUS = false;
	}

}
