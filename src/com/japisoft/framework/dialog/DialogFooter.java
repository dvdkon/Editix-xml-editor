package com.japisoft.framework.dialog;

import javax.swing.JComponent;
import com.japisoft.framework.dialog.actions.DialogActionModel;

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
public interface DialogFooter {

	/** Reset the model for the buttons */
	public void setModel( DialogActionModel model );

	/** @return the footer view with the buttons */
	public JComponent getView();

	/** Reset the dialog that will have this dialog footer */
	public void setDialogTarget( DialogComponent dialog );
	
	/** Called once when the dialog is shown */
	public void dialogShown();

	/** Called once when the dialog is hidden */
	public void dialogHidden();

	/** @return <code>true</code> if the following action is selected */
	public boolean isDialogActionSelected( int actionId );	

	/**
	 * Enabled/Disabled an action 
	 * @param actionId a dialog action id
	 * @param enabled enabled or disable this action */
	public void setEnabled( int actionId, boolean enabled );

	/** Invoke the following action. A runtime exception can be thrown for an unknown actionId */
	public void invokeAction( int actionId );

	/** Freeing any inner resource */
	public void dispose();

}
