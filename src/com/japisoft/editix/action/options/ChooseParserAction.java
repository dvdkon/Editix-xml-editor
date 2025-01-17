package com.japisoft.editix.action.options;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.japisoft.editix.ui.EditixFactory;
import com.japisoft.editix.ui.EditixFrame;
import com.japisoft.framework.dialog.DialogManager;
import com.japisoft.framework.xml.XMLConfigPanel;
import com.japisoft.framework.xml.XMLParser;

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
public class ChooseParserAction extends AbstractAction {

	public void actionPerformed( ActionEvent e ) {
		XMLConfigPanel panel = null;
		if ( DialogManager.showDialog(
				EditixFrame.THIS,
				"JAXP Parser",
				"Install a JAXP Compatible parser",
				"Add your java jars and select a java JAXP compatible class for changing the EditiX parser",
				null,
				panel = new XMLConfigPanel( 
						javax.xml.parsers.SAXParserFactory.class,
						XMLParser.CONFIG_FILE ), new Dimension( 400, 500 ) ) == 
								DialogManager.OK_ID ) {
			if ( panel.save() ) {
				EditixFactory.buildAndShowInformationDialog( "Restart EditiX for using the new parser" );
			}
		}
	}

}
