package com.japisoft.editix.action.fop;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.japisoft.editix.ui.EditixFrame;
import com.japisoft.editix.wizard.WizardContext;
import com.japisoft.editix.wizard.WizardContextFactory;
import com.japisoft.editix.wizard.link.LinkWizard;
import com.japisoft.framework.dialog.DialogManager;
import com.japisoft.framework.xml.parser.node.FPNode;

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
public class FOLinkAction extends AbstractAction {

	public void actionPerformed(ActionEvent e) {
		LinkWizard tw = new LinkWizard();
		tw.setContext( getContext() );
		if ( DialogManager.showDialog( 
				EditixFrame.THIS, 
				"New Links", 
				"Create a set of links", 
				"Generate a set of inner or external links", 
				null, 
				tw ) == DialogManager.OK_ID ) {
			FPNode node = tw.getResult();
			EditixFrame.THIS.getSelectedContainer().insertText( node.getRawXML( 1 ) );
		}
	}

	protected WizardContext getContext() {
		return WizardContextFactory.getInstance().getContext( "FO" );
	}	
	
}
