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

import java.awt.Color;
import java.awt.Window;

import javax.swing.Icon;
import javax.swing.UIManager;

import net.sf.jailer.ui.UIUtil;

/**
 * Shows SbE dialog.
 * 
 * @author Ralf Wisser
 */
public class SbEDialog extends javax.swing.JDialog {

	/** Creates new form SbEDialog 
	 * @param message 
	 * @param message2 */
	public SbEDialog(Window window, String message, String message2) {
		super(window, ModalityType.DOCUMENT_MODAL);
		initComponents();
		textLabel.setText(message);
		textLabel2.setText(message2);
		textLabel2.setForeground(Color.red);
		try {
			messageLabel.setText(null);
			   Icon errorIcon = UIManager.getIcon("OptionPane.informationIcon");
			   messageLabel.setIcon(errorIcon);
		} catch (Throwable t) {
			// ignore
		}
		pack();
		setSize(Math.max(getWidth(), 400), Math.min(getHeight(), 400));
		if (window == null) {
			setLocation(200, 100);
		} else {
			setLocation(getParent().getX() + (getParent().getWidth() - getWidth()) / 2, getParent().getY() + (getParent().getHeight() - getHeight()) / 2);
		}
		UIUtil.fit(this);
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {
		java.awt.GridBagConstraints gridBagConstraints;

		jPanel1 = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		browserTree = new javax.swing.JTree();
		messageLabel = new javax.swing.JLabel();
		messagePanel = new javax.swing.JPanel();
		textLabel = new javax.swing.JLabel();
		textLabel2 = new javax.swing.JLabel();
		jPanel2 = new javax.swing.JPanel();
		jButton1 = new javax.swing.JButton();
		jButton3 = new javax.swing.JButton();
		regardButton = new javax.swing.JCheckBox();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Create Extraction Model");
		getContentPane().setLayout(new java.awt.GridBagLayout());

		jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Model Structure"));
		jPanel1.setLayout(new java.awt.BorderLayout());

		jScrollPane1.setViewportView(browserTree);

		jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(8, 0, 0, 0);
		getContentPane().add(jPanel1, gridBagConstraints);

		messageLabel.setText("jLabel1");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new java.awt.Insets(8, 12, 0, 12);
		getContentPane().add(messageLabel, gridBagConstraints);

		messagePanel.setLayout(new java.awt.GridBagLayout());

		textLabel.setText("  ");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(0, 0, 8, 0);
		messagePanel.add(textLabel, gridBagConstraints);

		textLabel2.setText("  ");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		messagePanel.add(textLabel2, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(20, 0, 12, 0);
		getContentPane().add(messagePanel, gridBagConstraints);

		jPanel2.setLayout(new java.awt.GridBagLayout());

		jButton1.setText("  OK  ");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
		gridBagConstraints.weightx = 1.0;
		jPanel2.add(jButton1, gridBagConstraints);

		jButton3.setText("  Cancel  ");
		jButton3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton3ActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
		jPanel2.add(jButton3, gridBagConstraints);

		regardButton.setSelected(true);
		regardButton.setText("Regard parent tables  ");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		jPanel2.add(regardButton, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
		getContentPane().add(jPanel2, gridBagConstraints);

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
		ok = true;
		setVisible(false);
}//GEN-LAST:event_jButton1ActionPerformed

	private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
		ok = false;
		setVisible(false);
	}//GEN-LAST:event_jButton3ActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	javax.swing.JTree browserTree;
	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton3;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JLabel messageLabel;
	private javax.swing.JPanel messagePanel;
	javax.swing.JCheckBox regardButton;
	private javax.swing.JLabel textLabel;
	private javax.swing.JLabel textLabel2;
	// End of variables declaration//GEN-END:variables
	
	private static final long serialVersionUID = 2348041387629826138L;
	boolean ok = false;
	
}