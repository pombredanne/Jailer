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
package net.sf.jailer.ui;

import java.awt.Desktop;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URI;
import java.net.URLEncoder;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.UIManager;

/**
 * Shows SQL-Exception.
 * 
 * @author Ralf Wisser
 */
public class SqlErrorDialog extends javax.swing.JDialog {

	private static final long serialVersionUID = -6737420167295938488L;

	/** Creates new form SqlErrorDialog */
	public SqlErrorDialog(Window parent, String message, String sql, boolean sqlError, String title) {
		super(parent, ModalityType.APPLICATION_MODAL);
		this.sqlError = sqlError;
		this.message = message;
		initComponents();
		if (!sqlError) {
			if (title != null) {
				setTitle(title);
				sendButton.setVisible(false);
			} else {
				setTitle("Internal Error");
				sendButton.grabFocus();
				KeyListener keyListener = new KeyListener() {
					@Override
					public void keyTyped(KeyEvent e) {
						if (e.getKeyChar() == '\n') {
							sendButtonActionPerformed(null);
							setVisible(false);
						}
					}
	
					@Override
					public void keyReleased(KeyEvent e) {
					}
	
					@Override
					public void keyPressed(KeyEvent arg0) {
					}
				};
				sendButton.addKeyListener(keyListener);
				jButton1.setVisible(false);
			}
		}
		int y = 1;
		for (String line : message.trim().split("\n")) {
			JLabel label = new JLabel(line);
			java.awt.GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
			gridBagConstraints.gridx = 1;
			gridBagConstraints.gridy = y++;
			gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.weighty = 0;
			messagePanel.add(label, gridBagConstraints);
			if (y > 50) {
				break;
			}
		}
		if (sqlError) {
			sqlEditorPane.setContentType("text/sql");
		}
		sqlEditorPane.setText(sql.trim());
		sqlEditorPane.setCaretPosition(0);
		try {
			errorLabel.setText(null);
			Icon errorIcon = UIManager.getIcon("OptionPane.errorIcon");
			errorLabel.setIcon(errorIcon);
		} catch (Throwable t) {
			// ignore
		}
		pack();
		setSize(Math.max(700, Math.min(getWidth(), 1000)), Math.min(getHeight() + 32, 600));
		if (parent == null) {
			setLocation(200, 100);
		} else {
			setLocation(getParent().getX() + (getParent().getWidth() - getWidth()) / 2,
					getParent().getY() + (getParent().getHeight() - getHeight()) / 2);
		}
		UIUtil.fit(this);
		setVisible(true);
	}

	private final boolean sqlError;
	private final String message;

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {
		java.awt.GridBagConstraints gridBagConstraints;

		jPanel1 = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		sqlEditorPane = new javax.swing.JEditorPane();
		errorLabel = new javax.swing.JLabel();
		copyButton = new javax.swing.JButton();
		jScrollPane2 = new javax.swing.JScrollPane();
		messagePanel = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		jPanel2 = new javax.swing.JPanel();
		jButton1 = new javax.swing.JButton();
		sendButton = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("SQL Statement failed");
		getContentPane().setLayout(new java.awt.GridBagLayout());

		jPanel1.setLayout(new java.awt.BorderLayout());

		sqlEditorPane.setEditable(false);
		sqlEditorPane.setOpaque(false);
		jScrollPane1.setViewportView(sqlEditorPane);

		jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		getContentPane().add(jPanel1, gridBagConstraints);

		errorLabel.setText("jLabel1");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new java.awt.Insets(0, 12, 0, 12);
		getContentPane().add(errorLabel, gridBagConstraints);

		copyButton.setText("Copy to Clipboard");
		copyButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				copyButtonActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		getContentPane().add(copyButton, gridBagConstraints);

		messagePanel.setLayout(new java.awt.GridBagLayout());

		jLabel1.setText("  ");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 100;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		messagePanel.add(jLabel1, gridBagConstraints);

		jScrollPane2.setViewportView(messagePanel);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = 1;
		gridBagConstraints.weighty = 0.5;
		getContentPane().add(jScrollPane2, gridBagConstraints);

		jPanel2.setLayout(new java.awt.GridBagLayout());

		jButton1.setText(" Close ");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
		jPanel2.add(jButton1, gridBagConstraints);

		sendButton.setText("Report and Close");
		sendButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				sendButtonActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		jPanel2.add(sendButton, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
		getContentPane().add(jPanel2, gridBagConstraints);

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
		setVisible(false);
	}// GEN-LAST:event_jButton1ActionPerformed

	private void copyButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_copyButtonActionPerformed
		sqlEditorPane.selectAll();
		sqlEditorPane.copy();
	}// GEN-LAST:event_copyButtonActionPerformed

	private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_sendButtonActionPerformed
		setVisible(false);
		URI url;
		try {
			int MAX_LENGTH = 6000;
			String issue = (sqlError ? message + "\n\n" : "") + sqlEditorPane.getText();
			if (issue.length() > MAX_LENGTH) {
				issue = issue.substring(0, MAX_LENGTH);
			}
			url = new URI("http://jailer.sf.net/issueReport.php?type="
					+ URLEncoder.encode(sqlError ? "SQL" : "GUI", "UTF-8") + "&" + "issue="
					+ URLEncoder.encode(issue, "UTF-8"));
			Desktop.getDesktop().browse(url);
		} catch (Exception e) {
			// ignore
		}
	}// GEN-LAST:event_sendButtonActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton copyButton;
	private javax.swing.JLabel errorLabel;
	private javax.swing.JButton jButton1;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JPanel messagePanel;
	private javax.swing.JButton sendButton;
	private javax.swing.JEditorPane sqlEditorPane;
	// End of variables declaration//GEN-END:variables

}