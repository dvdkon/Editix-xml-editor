package com.japisoft.xflows.task.xquery;

import java.io.File;

import com.japisoft.xflows.task.FilesTaskRunner;
import com.japisoft.xflows.task.TaskContext;

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
public class XQueryRunner extends FilesTaskRunner {

	public XQueryRunner() {
		super( new XQueryFileRunner(), true );
	}

	public boolean run( TaskContext context ) {
		
		String xquery = context.getParam( XQueryUI.XQUERY );
		if ( xquery == null ) {
			context.addError( "No XQuery file" );
			return ERROR;
		} else {

			File fxquery = new File( xquery );
			if ( !fxquery.exists() ) {
				context.addError( "XQuery file " + fxquery + " not found ");
				return ERROR;
			}

			super.run( context );
		}

		return OK;
	}

}
