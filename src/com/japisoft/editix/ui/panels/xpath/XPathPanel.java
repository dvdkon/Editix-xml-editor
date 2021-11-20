package com.japisoft.editix.ui.panels.xpath;

import javax.swing.JComponent;

import com.japisoft.editix.ui.panels.AbstractPanel;

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
public class XPathPanel extends AbstractPanel {

	protected JComponent buildView() {
		return new XPathUI();
	}

	protected String getTitle() {
		return "XPath";
	}

	public void stop() {
	}

	@Override
	protected void preShow() {
//		if ( !isShown() ) {
			// Run directly XPath expression
			if ( params != null ) {
				runXPath( params );
			}
//		}
		super.preShow();
	}

	@Override
	public void setParams( String params ) {
		super.setParams( params );
//		if ( isVisible() ) {
//			runXPath( params );
//		}
	}

	private void runXPath( String xpath ) {
		( ( XPathUI )getView() ).setXPath( xpath );
		( ( XPathUI )getView() ).runFromRoot();
	}

}
