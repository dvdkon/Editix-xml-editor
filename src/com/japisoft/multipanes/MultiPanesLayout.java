package com.japisoft.multipanes;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;

import javax.swing.JComponent;

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
class MultiPanesLayout implements LayoutManager2 {

	MultiPanes pane;

	public MultiPanesLayout( MultiPanes pane ) {
		this.pane = pane;
	}

	public void addLayoutComponent(Component comp, Object constraints) {
	}

	public Dimension maximumLayoutSize(Container target) {
		return pane.getMaximumSize();
	}

	public float getLayoutAlignmentX(Container target) {
		return 0.5f;
	}

	public float getLayoutAlignmentY(Container target) {
		return 0.5f;
	}

	public void invalidateLayout(Container target) {
	}

	public void addLayoutComponent(String name, Component comp) {
	}

	public void removeLayoutComponent(Component comp) {
	}

	public Dimension preferredLayoutSize(Container parent) {
		return new Dimension( 100, 100 );
	}

	public Dimension minimumLayoutSize(Container parent) {
		return pane.getMinimumSize();
	}

	private int inset;
	
	public void setVerticalInset( int inset ) {
		this.inset = inset;
	}
	
	public int getVerticalInset() {
		return inset;
	}

	public void layoutContainer(Container parent) {
		TitledPane tp = pane.getOpenedTitledPane();
		JComponent toExpand = null;
		
		int expandedHeight = inset;

		if ( tp != null ) {
			toExpand = tp.getView();

			for ( int i = 0; i < parent.getComponentCount(); i++ ) {
				Component c = parent.getComponent( i );
				if ( c != toExpand ) {
					expandedHeight += c.getPreferredSize().height + inset;
				}
			}			
			
			expandedHeight = Math.max( 0, parent.getHeight() - expandedHeight + inset );
		}

		int y = inset;

		TitledPaneModel model = pane.getModel();

		for ( int i = 0; i < model.getTitledPaneCount(); i++ ) {

			Component c = parent.getComponent( i );

			Dimension d = c.getPreferredSize();
			c.setBounds( 0, y, parent.getWidth(), d.height );
			y += ( d.height );

			if ( model.getTitledPaneAt( i ) == tp ) {
				c = toExpand;
				c.setBounds( 0, y, parent.getWidth(), expandedHeight );
				y += expandedHeight;			
			} else
				y += inset;

		}
	}

}
