package com.japisoft.framework.css;

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
public class CSSDim {

	private int value;
	private boolean percent = false;
	
	public static CSSDim ZERO = new CSSDim( 0, false );
	
	public CSSDim( String value ) {
		if ( value.endsWith( "%" ) ) {
			percent = true;
			value = value.substring( 0, value.length() - 1 );
		} else
		if ( value.endsWith( "px" ) ) {
			value = value.substring( 0, value.length() - 2 );
		}
		this.value = Integer.parseInt( value );
	}
	
	public CSSDim( int value, boolean percent ) {
		this.value = value;
		this.percent = percent;
	}

	public int getValue() {
		return value;
	}
	
	public boolean isPercent() {
		return this.percent;
	}
	
	public String toString() {
		return Integer.toString( value ) + ( percent ? "%" : "px" );
	}
	
}
