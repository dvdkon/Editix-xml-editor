package com.japisoft.editix.action.file.export;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

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
public class JavaMetaClass {
	private String name;
	
	public JavaMetaClass( String name ) {
		this.name = Tools.toClassName( name );
	}

	private Properties p;

	public void setAttribute( String name, String value ) {
		if ( p == null )
			p = new Properties();
		p.setProperty( Tools.convertIfKeyWord( name ), value );
	}

	private ArrayList al = null;

	public void addContentElement( String name ) {
		if ( al == null )
			al = new ArrayList();
		if ( !al.contains( Tools.toClassName( name ) ) )
			al.add( Tools.toClassName( name ) );
	}
		
	private boolean textContent = false;

	public void setText() {
		textContent = true;
	}

	public void write( PrintWriter pw, String pack ) {
		pw.println( "package " + pack + ";" );
		pw.println();
		pw.println( "import java.util.ArrayList;" );
		pw.println();
		pw.println( "/**" );
		pw.println(	"*\tGenerated by EditiX" );
		pw.println( "*\thttp://www.editix.com" );
		pw.println( "*/");
		pw.println( "public class " + name + " extends AbstractElement {" );

		// Attributs
		if ( p != null ) {
			pw.println();
			Enumeration e = p.propertyNames();
			while ( e.hasMoreElements() ) {
				String att = ( String )e.nextElement();
				pw.println( "\tprivate String " + att + ";" );
			}
			pw.println();
			e = p.propertyNames();
			while ( e.hasMoreElements() ) {
				String att = ( String )e.nextElement();
				// Setter
				pw.println( "\tpublic void " + Tools.toSetName( att ) + "( String _value ) {" );
				pw.println( "\t\tthis." + att + " = _value;" );
				pw.println( "\t}" );
				pw.println();
				// Getter
				pw.println( "\tpublic String " + Tools.toGetName( att ) + "() {" );
				pw.println( "\t\treturn " + att + ";" );
				pw.println( "\t}" );
				pw.println();
			}			
		}

		// Content
		if ( al != null ) {
			pw.println();
			for ( int i = 0; i < al.size(); i++ ) {
				String name = ( String )al.get( i );
				pw.println( "\tprivate ArrayList" + " " + name.toLowerCase() + ";" );
			}
			pw.println();
			for ( int i = 0; i < al.size(); i++ ) {
				String name = ( String )al.get( i );
				// Adder
				pw.println( "\tpublic void " + Tools.toAddName( name.toLowerCase() ) + "( " + name + " _value ) {" );
				pw.println( "\t\tif ( " + name.toLowerCase() + "==null ) " + name.toLowerCase() + " = new ArrayList();" );
				pw.println( "\t\tthis." + name.toLowerCase() + ".add( _value );" );
				pw.println( "\t}" );
				pw.println();
				// Getter
				pw.println( "\tpublic " + "ArrayList " + Tools.toGetName( name ) + "() {" );
				pw.println( "\t\treturn " + name.toLowerCase() + ";" );
				pw.println( "\t}" );
				pw.println();
				// Has
				pw.println( "\tpublic " + "boolean " + Tools.toAnyName( "has", name ) + "() {" );
				pw.println( "\t\treturn " + name.toLowerCase() + " != null;" );
				pw.println( "\t}" );
				pw.println();
				// First
				pw.println( "\tpublic " + name + " " + Tools.toAnyName( "getFirst", name ) + "() {" );
				pw.println( "\t\tif ( " + name.toLowerCase() + "!= null && " + name.toLowerCase() + ".size() > 0 ) {" );
				pw.println( "\t\t\treturn (" + name + ")" + name.toLowerCase() + ".get(0);" );
				pw.println( "\t\t} else return null;" );
				pw.println( "\t}" );
				pw.println();
			}
		}
		
		if ( textContent ) {
			pw.println();
			pw.println( "\tprivate String value;" );
			pw.println( "\tpublic void setValue( String value ) {" );
			pw.println( "\t\tthis.value = value;" );
			pw.println( "\t}" );
			pw.println();
			pw.println( "\tpublic String getValue() {" );
			pw.println( "\t\treturn value;" );
			pw.println( "\t}" );
		}
		
		pw.println( "}" );
	}
}
