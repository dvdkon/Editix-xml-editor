package com.japisoft.editix.mapper.xslt;

import com.japisoft.editix.mapper.AbstractMapper;
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
public class CallersMapper extends AbstractMapper {

	public boolean canMap( FPNode node ) {
		if ( "call-template".equals( node.getContent() ) )
			return true;
		if ( "apply-templates".equals( node.getContent() ) )
			return true;
		return false;
	}
	
	private static String cleanXPath( String xpath ) {
		if ( xpath == null )
			return "null";
		if ( "/".equals( xpath ) )
			return "/";
		int i = xpath.lastIndexOf( "/" );
		if ( i > -1 ) {
			xpath = xpath.substring( i + 1 );
		}
		i = xpath.lastIndexOf( "[" );
		if ( i > -1 ) {
			xpath = xpath.substring( 0, i );
		}
		return xpath;
	}

	public static boolean matchSelect( String s1, String s2 ) {
		return cleanXPath( s1 ).equals( cleanXPath( s2 ) );
	}

	@Override
	protected boolean isMatchingNode(
		FPNode sourceNode,
		FPNode walkingNode) {
		boolean template = "template".equals( walkingNode.getContent() );
		if ( template ) {
			String name = sourceNode.getAttribute( "name" );
			if ( name != null ) {
				if ( name.equals( walkingNode.getAttribute( "name" ) ) )
					return true;
			} else {
				String mode = sourceNode.getAttribute( "mode" );
				if ( mode != null ) {
					if ( !mode.equals( walkingNode.getAttribute( "mode" ) ) )
						return false;
				}
				String mustMatch = sourceNode.getAttribute( "select" );
				
				if ( mustMatch != null ) {
					if ( mode == null && 
							walkingNode.hasAttribute( "mode" ) ) {
						return false;
					}
					String currentMatch = walkingNode.getAttribute( "match" );
					return matchSelect( mustMatch, currentMatch );
				}
			}
		}
		return false;
	}

	public String[] getMapAttributes() {
		return new String[] { "name", "mode", "select" };
	}	
	
	@Override
	public String toString() {
		return "Find template references";
	}

}
