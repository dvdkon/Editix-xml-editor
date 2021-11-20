package com.japisoft.editix.wizard.link;

import java.util.List;

import javax.swing.JTabbedPane;

import com.japisoft.editix.wizard.Wizard;
import com.japisoft.editix.wizard.WizardContext;

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
public class LinkWizard extends JTabbedPane implements Wizard, LinkWizardModel {

	private LinkPanel innerLinks = null;
	private LinkPanel externalLinks = null;
	
	public LinkWizard() {
		innerLinks = new LinkPanel( true );
		externalLinks = new LinkPanel();
		addTab( "External Links", externalLinks );
		addTab( "Inner Links", innerLinks );		
	}

	private WizardContext context;

	public FPNode getResult() {
		return context.getResult( this );
	}

	public void setContext(WizardContext context) {
		this.context = context;
	}
	
	public List<Link> getExternalLinks() {
		return externalLinks.getLinks();
	}

	public List<Link> getInternalLinks() {
		return innerLinks.getLinks();
	}

}
