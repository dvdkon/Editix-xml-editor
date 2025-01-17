package com.japisoft.editix.wizard;

import java.util.Properties;
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
public abstract class BasicWizardContext implements WizardContext {

	public BasicWizardContext() {
		super();
	}
	
	public BasicWizardContext( String[] content ) {
		this();
		for ( int i = 0; i < content.length; i += 2 ) {
			setProperty( content[ i ], content[ i + 1 ] );
		}
	}
	
	private Properties prop = null;
	
	public String getProperty(String name, String defaultValue) {
		if ( prop == null )
			return defaultValue;
		return prop.getProperty( name, defaultValue );
	}

	public void setProperty( String name, String value ) {
		if ( prop == null )
			prop = new Properties();
		prop.put( name, value );
	}
	
	protected boolean isEmpty( String value ) {
		return value == null || "".equals( value );
	}

	public abstract FPNode getResult(WizardModel model);

}
