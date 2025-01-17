package com.japisoft.framework.xml.refactor2.elements.schema;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.japisoft.framework.xml.parser.node.FPNode;
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
public class AttributeRefactor extends AbstractRefactor {

	public boolean requireNewValue() {
		return true;
	}

	public boolean process(FPNode node) {
		// For global definition only
		if ( node.getFPParent() != null && 
				node.getFPParent().matchContent( "schema" ) ) {

			if  
				( node.matchContent( "attribute" ) && 
					( node.hasAttribute( "name" ) ) ) {
				oldValue = node.getAttribute( "name" );
				return true;
			}
		}

		// For all references
		if ( node.matchContent( "attribute" )
				&& node.hasAttribute( "ref" ) ) {
			oldValue = node.getAttribute( "ref" );			
			return true;
		}

		return false;
	}

	public String getTitle(FPNode node) {
		String name = node.getAttribute( "name" );
		if ( name == null )
				name = node.getAttribute( "ref" );
		return "The attribute's name '" + name + "'";
	}

	public void startElement(
			String uri, 
			String localName, 
			String qName, 
			Attributes atts ) throws SAXException {

		if ( "attribute".equals( localName ) ) {
			atts = new AttributesProxy( atts );
		}
		super.startElement(uri, localName, qName, atts);
	}

	// ---------------------------------------------------------------
	
	class AttributesProxy implements Attributes {

		private Attributes ref;
		
		AttributesProxy( Attributes ref ) {
			this.ref = ref;
		}

		public int getIndex(String uri, String localName) {
			return ref.getIndex( uri, localName );
		}

		public int getIndex(String qName) {
			return ref.getIndex( qName );
		}

		public int getLength() {
			return ref.getLength();
		}

		public String getLocalName(int index) {
			return ref.getLocalName( index );
		}

		public String getQName(int index) {
			return ref.getQName( index );
		}

		public String getType(int index) {
			return ref.getType( index );
		}

		public String getType(String uri, String localName) {
			return ref.getType( uri, localName );
		}

		public String getType(String qName) {
			return ref.getType( qName );
		}

		public String getURI(int index) {
			return ref.getURI( index );
		}

		private String processValue( String name, String value ) {
			if ( "name".equals( name ) && 
					value.equals( oldValue ) )
				return newValue;
			else
			if ( "ref".equals( name )
					 && 
						value.equals( oldValue ) )
				return newValue;
			return value;
		}

		public String getValue(int index) {
			return processValue( ref.getQName( index ), ref.getValue( index ) );
		}

		public String getValue(String uri, String localName) {
			return processValue( localName, ref.getValue( uri, localName ) );
		}

		public String getValue(String qName) {
			return processValue( qName, ref.getValue( qName ) );
		}

	}

}
