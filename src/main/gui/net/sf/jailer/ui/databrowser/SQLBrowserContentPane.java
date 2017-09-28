/*
 * Copyright 2007 - 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sf.jailer.ui.databrowser;

import java.awt.GridBagConstraints;
import java.sql.SQLException;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;

import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import net.sf.jailer.database.Session;
import net.sf.jailer.datamodel.DataModel;
import net.sf.jailer.ui.databrowser.metadata.MetaDataSource;
import net.sf.jailer.ui.databrowser.sqlconsole.MetaDataBasedSQLCompletionProvider;
import net.sf.jailer.ui.syntaxtextarea.DataModelBasedSQLCompletionProvider;
import net.sf.jailer.ui.syntaxtextarea.RSyntaxTextAreaWithSQLSyntaxStyle;

/**
 * Content UI of a row browser frame in SQL mode (as {@link JInternalFrame}s). Contains a
 * table for rendering rows.
 * 
 * @author Ralf Wisser
 */
@SuppressWarnings("serial")
public class SQLBrowserContentPane extends javax.swing.JPanel {

	/** Creates new form SQLBrowserContentPane */
	public SQLBrowserContentPane(DataModel dataModel, Session session, MetaDataSource metaDataSource) {
		initComponents();
		this.sqlEditorPane = new RSyntaxTextAreaWithSQLSyntaxStyle() {
			protected void actionPerformed() {
				super.actionPerformed();
				reloadButton.doClick();
			}
		};
		JScrollPane jScrollPane1 = new JScrollPane();
		jScrollPane1.setViewportView(sqlEditorPane);
		
		try {
			CompletionProvider provider = null;
			if (metaDataSource != null) {
				provider = new MetaDataBasedSQLCompletionProvider(session, metaDataSource);
			} else if (dataModel != null) {
				provider = new DataModelBasedSQLCompletionProvider(session, dataModel);
			}
			
			if (provider != null) {
				AutoCompletion ac = new AutoCompletion(provider);
				ac.install(sqlEditorPane);
			}
		} catch (SQLException e1) {
		}
		
		GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridheight = 6;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		editorPanel.add(jScrollPane1, gridBagConstraints);
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jSplitPane1 = new javax.swing.JSplitPane();
        editorPanel = new javax.swing.JPanel();
        reloadButton = new javax.swing.JButton();
        detailsButton = new javax.swing.JButton();
        fetchLabel = new javax.swing.JLabel();
        rowListPanel = new javax.swing.JPanel();

        setLayout(new java.awt.GridBagLayout());

        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setOneTouchExpandable(true);

        editorPanel.setLayout(new java.awt.GridBagLayout());

        reloadButton.setText(" Reload ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        editorPanel.add(reloadButton, gridBagConstraints);

        detailsButton.setText(" Details ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        editorPanel.add(detailsButton, gridBagConstraints);

        fetchLabel.setFont(new java.awt.Font("DejaVu Sans", 1, 13)); // NOI18N
        fetchLabel.setText(" Row Limit  ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
        editorPanel.add(fetchLabel, gridBagConstraints);

        jSplitPane1.setTopComponent(editorPanel);

        rowListPanel.setLayout(new java.awt.BorderLayout());
        jSplitPane1.setRightComponent(rowListPanel);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jSplitPane1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton detailsButton;
    public javax.swing.JPanel editorPanel;
    private javax.swing.JLabel fetchLabel;
    private javax.swing.JSplitPane jSplitPane1;
    public javax.swing.JButton reloadButton;
    public javax.swing.JPanel rowListPanel;
    // End of variables declaration//GEN-END:variables

    public final RSyntaxTextArea sqlEditorPane;
}
