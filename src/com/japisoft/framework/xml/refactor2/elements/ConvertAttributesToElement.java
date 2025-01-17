package com.japisoft.framework.xml.refactor2.elements;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.japisoft.framework.xml.refactor2.AbstractRefactor;

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
public class ConvertAttributesToElement extends AbstractRefactor {

	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {

		String name = localName;
		if ( name == null ) {
			name = qName;
			int i = name.lastIndexOf( ":" );
			if ( i > -1 ) {
				name = name.substring( i + 1 );
			}
		}

		Attributes atts2 = atts;
		
		// Remove the attributes
		if ( name.equals( oldValue ) ) 
			atts = null;
		
		super.startElement(uri, localName, qName, atts);
		
		if ( atts2 != null && name.equals( oldValue ) ) {
			for ( int i = 0; i < atts2.getLength(); i++ ) {

				String n = atts2.getQName( i );
				String v = atts2.getValue( i );
				super.startElement(
						uri,
						n,
						n,
						null );
				super.characters(
						v.toCharArray(),
						0,
						v.length() );
				super.endElement(
						uri,
						n,
						n );				
			}
		}
	}
	
}
