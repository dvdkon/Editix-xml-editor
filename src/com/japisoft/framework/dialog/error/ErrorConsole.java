package com.japisoft.framework.dialog.error;

import java.io.PrintWriter;
import java.io.StringWriter;
import com.japisoft.framework.ApplicationModel;

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
public class ErrorConsole {

	/** Show this exception with information to the user in the error output */
	public static void show( Throwable th ) {
		StringWriter sw = new StringWriter();
		th.printStackTrace(new PrintWriter( sw ) );
		StringBuffer sb = new StringBuffer();
		sb.append( ApplicationModel.getAppNameVersion() );
		sb.append( "\nHas met an unknown error, please send it to : " + ApplicationModel.MAIN_SUPPORT_EMAIL + "\n" );
		sb.append( "specifying your operating system version and java version\n" );
		sb.append( "* Java version : " ).append( System.getProperty( "java.version" ) ).append( "\n" );
		sb.append( "* OS : " ).append( System.getProperty( "os.name" ) ).append( "\n" );		
		sb.append( "\n----------------------------------------------------------------\n\n" );
		sb.append( sw.toString() );
		System.err.println( sb.toString() );		
	}
	
}
