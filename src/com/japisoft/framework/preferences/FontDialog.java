package com.japisoft.framework.preferences;

import java.awt.Dialog;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;

import com.japisoft.framework.dialog.BasicOKCancelDialogComponent;

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
public class FontDialog extends BasicOKCancelDialogComponent implements ItemListener {

	private JPanel content = null;
	
	public FontDialog( Dialog owner, Font init ) {
		super( 
				owner, 
				"Font", 
				"Font choice", 
				"Choose your font", 
				null );
		
		content = new JPanel();
		initComponents();
		
		cbFontName.setModel(
				new DefaultComboBoxModel(
						GraphicsEnvironment.getLocalGraphicsEnvironment()
				        .getAvailableFontFamilyNames()
				)
		);
		
		cbFontStyle.setModel(
			new DefaultComboBoxModel(
				new String[] {
					"PLAIN",
					"BOLD",
					"ITALIC"
				} 
			) 
		);

		cbFontSize.setModel(
			new DefaultComboBoxModel(
				new String[] {
					"10",
					"11",
					"12",
					"13",
					"14",
					"15",
					"16",
					"17",
					"18",
					"20"
				} 
			) 
		);
				
		if ( !FONT_NAME_UPDATABLE ) {
			cbFontName.setEnabled( false );
		}
		
		setValue( init );
		itemStateChanged( null );

		setUI( content );
		
		pack();
	}

	public void itemStateChanged(ItemEvent e) {
		txtPreview.setFont( getValue() );
		txtPreview.setText( "Font Preview" );
	}

	@Override
	public void addNotify() {
		super.addNotify();
		cbFontName.addItemListener( this );
		cbFontStyle.addItemListener( this );
		cbFontSize.addItemListener( this );
	}
	
	@Override
	public void removeNotify() {
		super.removeNotify();
		cbFontName.removeItemListener( this );
		cbFontStyle.removeItemListener( this );
		cbFontSize.removeItemListener( this );		
	}

	public static boolean FONT_NAME_UPDATABLE = true;


	private void setValue( Font font ) {
		cbFontName.setSelectedItem( font.getName() );
		
		if ( font.getStyle() == Font.PLAIN ) {
			cbFontStyle.setSelectedIndex( 0 );			
		} else
		if ( font.getStyle() == Font.BOLD ) {
			cbFontStyle.setSelectedIndex( 1 );
		} else
		if ( font.getStyle() == Font.ITALIC ) {
			cbFontStyle.setSelectedIndex( 2 );
		}
		cbFontSize.setSelectedIndex( font.getSize() - 10 );
	}
	public Font getValue() {
		return 
			new Font( 
				( String )cbFontName.getSelectedItem(),
				cbFontStyle.getSelectedIndex(),
				cbFontSize.getSelectedIndex() + 10 );
	}
	
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        lblFontName = new javax.swing.JLabel();
        cbFontName = new javax.swing.JComboBox();
        lblFontStyle = new javax.swing.JLabel();
        cbFontStyle = new javax.swing.JComboBox();
        lblFontSize = new javax.swing.JLabel();
        cbFontSize = new javax.swing.JComboBox();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtPreview = new javax.swing.JTextArea();

        lblFontName.setText("Font Name");
        lblFontName.setName("lblFontName"); // NOI18N

        cbFontName.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbFontName.setName("cbFontName"); // NOI18N

        lblFontStyle.setText("Font Style");
        lblFontStyle.setName("lblFontStyle"); // NOI18N

        cbFontStyle.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbFontStyle.setName("cbFontStyle"); // NOI18N

        lblFontSize.setText("Font Size");
        lblFontSize.setName("lblFontSize"); // NOI18N

        cbFontSize.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbFontSize.setName("cbFontSize"); // NOI18N

        jSeparator1.setName("jSeparator1"); // NOI18N

        jLabel4.setText("Preview");
        jLabel4.setName("jLabel4"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        txtPreview.setColumns(20);
        txtPreview.setRows(5);
        txtPreview.setName("txtPreview"); // NOI18N
        jScrollPane1.setViewportView(txtPreview);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout( content );
        content.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                    .addComponent(cbFontName, javax.swing.GroupLayout.Alignment.LEADING, 0, 256, Short.MAX_VALUE)
                    .addComponent(lblFontName, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFontStyle, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbFontStyle, javax.swing.GroupLayout.Alignment.LEADING, 0, 256, Short.MAX_VALUE)
                    .addComponent(lblFontSize, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbFontSize, javax.swing.GroupLayout.Alignment.LEADING, 0, 256, Short.MAX_VALUE)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFontName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbFontName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblFontStyle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbFontStyle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblFontSize)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbFontSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>


    // Variables declaration - do not modify
    private javax.swing.JComboBox cbFontName;
    private javax.swing.JComboBox cbFontSize;
    private javax.swing.JComboBox cbFontStyle;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblFontName;
    private javax.swing.JLabel lblFontSize;
    private javax.swing.JLabel lblFontStyle;
    private javax.swing.JTextArea txtPreview;
    // End of variables declaration

}
