package com.japisoft.xflows.task.ui.builder;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.japisoft.framework.dockable.action.ActionModel;
import com.japisoft.xflows.task.Task;
import com.japisoft.xflows.task.TaskTable;
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
public class TasksPanel extends JPanel {

	public TasksPanel( TaskTypeListener listener ) {
		super();
		init( listener );
	}

	TaskTable table;
	private TaskTypeListener listener;
	
	private void init( TaskTypeListener listener ) {
		setLayout( new BorderLayout() );
		add( new JScrollPane( 
				table = new TaskTable( this.listener = listener, true ) ), 
				BorderLayout.CENTER );
		table.setRowHeight( table.getRowHeight() + 5 );
	}

	public List<Task> getTasks() {
		return table.getTasks();
	}
	
	public void setTasks( List<Task> tasks ) {
		table.setTasks( tasks );
	}

	void fillActionModel( ActionModel model ) {
		model.addAction( new RunAction() );
		model.addAction( ActionModel.SEPARATOR );
		model.addAction( new DeleteAction() );
		model.addAction( ActionModel.SEPARATOR );		
		model.addAction( new DownAction() );
		model.addAction( new UpAction() );
	}

	//////////////////////////////////////////////////////////
	
	class UpAction extends AbstractAction {
		public UpAction() {
			super();
			putValue( Action.SMALL_ICON, 
					new ImageIcon( ClassLoader.getSystemResource( "images/element_up.png" )  ) );
			putValue( Action.SHORT_DESCRIPTION, "Move the task up" );
		}
		public void actionPerformed( ActionEvent e ) {
			table.upTask();
		}
	}

	class DownAction extends AbstractAction {
		public DownAction() {
			super();
			putValue( Action.SMALL_ICON, 
					new ImageIcon( ClassLoader.getSystemResource( "images/element_down.png" )  ) );
			putValue( Action.SHORT_DESCRIPTION, "Move the task down" );
		}
		public void actionPerformed( ActionEvent e ) {
			table.downTask();
		}
	}

	class DeleteAction extends AbstractAction {
		public DeleteAction() {
			super();
			putValue( Action.SMALL_ICON, 
					new ImageIcon( ClassLoader.getSystemResource( "images/element_delete.png" )  ) );
			putValue( Action.SHORT_DESCRIPTION, "Delete the selected task" );
		}
		public void actionPerformed( ActionEvent e ) {
			table.deleteTask();
		}
	}

	class RunAction extends AbstractAction {
		public RunAction() {
			super();
			putValue( Action.SMALL_ICON, 
					XFlowsFactory.getImageIcon( "images/element_run.png" ) );
			putValue( Action.SHORT_DESCRIPTION, "Run the task" );
		}

		public void actionPerformed( ActionEvent e ) {
			listener.update();
			table.runTask();
		}
	}

}

