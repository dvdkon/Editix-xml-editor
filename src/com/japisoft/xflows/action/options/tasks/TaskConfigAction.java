package com.japisoft.xflows.action.options.tasks;

import java.awt.event.ActionEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.AbstractAction;

import com.japisoft.framework.ApplicationModel;
import com.japisoft.framework.dialog.DialogManager;
import com.japisoft.framework.xml.parser.node.FPNode;
import com.japisoft.xflows.task.ui.XFlowsFactory;

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
public class TaskConfigAction extends AbstractAction {

	public void actionPerformed( ActionEvent e ) {
		
		TasksConfigPanel panel = new TasksConfigPanel(); 
		
		if ( DialogManager.showDialog(
				ApplicationModel.MAIN_FRAME,
				"Tasks manager",
				"Tasks manager",
				"Manage your own XFlows Task here",
				null,
				panel ) ==
					DialogManager.OK_ID ) {

			FPNode node = panel.getRootNode();
			URL documentUrl = ClassLoader.getSystemClassLoader().getResource( "tasks.xml" );

			try {
				String fileName = documentUrl.toExternalForm();
				fileName = fileName.replaceAll("%20", " ");
				fileName = fileName.substring(5);				
				node.getDocument().write( new FileOutputStream( fileName ) );
				XFlowsFactory.buildAndShowInformationDialog( "Restart XFlows for using the new tasks" );
			} catch (IOException e1) {
				XFlowsFactory.buildAndShowErrorDialog( "Can't write tasks.xml : " + e1.getMessage() );
			}
		}
	}

}
