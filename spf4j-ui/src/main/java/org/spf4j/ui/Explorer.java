/*
 * Copyright (c) 2001-2017, Zoltan Farkas All Rights Reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 * Additionally licensed with:
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
package org.spf4j.ui;
//CHECKSTYLE:OFF
import com.google.protobuf.CodedInputStream;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.filechooser.FileFilter;
import org.spf4j.base.AbstractRunnable;
import org.spf4j.stackmonitor.SampleNode;
import org.spf4j.stackmonitor.proto.Converter;
import org.spf4j.stackmonitor.proto.gen.ProtoSampleNodes;

/**
 * @author zoly
 */
@SuppressFBWarnings({"FCBL_FIELD_COULD_BE_LOCAL", "UP_UNUSED_PARAMETER"})
public class Explorer extends javax.swing.JFrame {

  private static final long serialVersionUID = 1L;

  private File folder;

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
   * content of this method is always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktopPane = new javax.swing.JDesktopPane();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        cutMenuItem = new javax.swing.JMenuItem();
        copyMenuItem = new javax.swing.JMenuItem();
        pasteMenuItem = new javax.swing.JMenuItem();
        deleteMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        contentMenuItem = new javax.swing.JMenuItem();
        aboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        desktopPane.setAutoscrolls(true);
        desktopPane.setDoubleBuffered(true);
        desktopPane.setPreferredSize(new java.awt.Dimension(800, 600));

        fileMenu.setMnemonic('f');
        fileMenu.setText("File");

        openMenuItem.setMnemonic('o');
        openMenuItem.setText("Open");
        openMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(openMenuItem);

        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        editMenu.setMnemonic('e');
        editMenu.setText("Edit");

        cutMenuItem.setMnemonic('t');
        cutMenuItem.setText("Cut");
        editMenu.add(cutMenuItem);

        copyMenuItem.setMnemonic('y');
        copyMenuItem.setText("Copy");
        editMenu.add(copyMenuItem);

        pasteMenuItem.setMnemonic('p');
        pasteMenuItem.setText("Paste");
        editMenu.add(pasteMenuItem);

        deleteMenuItem.setMnemonic('d');
        deleteMenuItem.setText("Delete");
        editMenu.add(deleteMenuItem);

        menuBar.add(editMenu);

        helpMenu.setMnemonic('h');
        helpMenu.setText("Help");

        contentMenuItem.setMnemonic('c');
        contentMenuItem.setText("Contents");
        helpMenu.add(contentMenuItem);

        aboutMenuItem.setMnemonic('a');
        aboutMenuItem.setText("About");
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(desktopPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 1149, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(desktopPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 668, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

  /**
   * Creates new form Explorer
   */
  public Explorer(final File... openFiles) throws IOException {
    folder = null;
    initComponents();
    for (File file : openFiles) {
      openFile(file);
    }
  }

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
      System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void openMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuItemActionPerformed
      JFileChooser chooser = new JFileChooser();
      chooser.setDialogType(JFileChooser.OPEN_DIALOG);
      chooser.setFileFilter(new Spf4jFileFilter());
      if (folder != null) {
        chooser.setCurrentDirectory(folder);
      }

      int returnVal = chooser.showOpenDialog(this);

      if (returnVal == JFileChooser.APPROVE_OPTION) {
        File file = chooser.getSelectedFile();
        folder = file.getParentFile();
        try {
          openFile(file);
        } catch (IOException ex) {
          throw new UncheckedIOException(ex);
        }
      }
    }//GEN-LAST:event_openMenuItemActionPerformed

  private void openFile(final File file) throws IOException {
    String fileName = file.getName();
    JInternalFrame frame;
    if (fileName.endsWith("tsdb")) {
      frame = new TSDBViewJInternalFrame(file);
      frame.setVisible(true);
      desktopPane.add(frame, javax.swing.JLayeredPane.DEFAULT_LAYER);
    } else if (fileName.endsWith("tsdb2")) {
      frame = new TSDB2ViewJInternalFrame(file);
      frame.setVisible(true);
      desktopPane.add(frame, javax.swing.JLayeredPane.DEFAULT_LAYER);
    } else if (fileName.endsWith("ssdump")) {
      SampleNode samples = loadLegacyFormat(file);
      setFrames(samples, fileName);
    } else if (fileName.endsWith("ssdump2")) {
      SampleNode samples = org.spf4j.ssdump2.Converter.load(file);
      setFrames(samples, fileName);
    } else if (fileName.endsWith("ssdump3")) {
      Map<String, SampleNode> loadLabeledDumps = org.spf4j.ssdump2.Converter.loadLabeledDumps(file);
      for (Map.Entry<String, SampleNode> entry : loadLabeledDumps.entrySet()) {
        setFrames(entry.getValue(), fileName + ':' + entry.getKey());
      }
    } else {
      throw new IOException("Unsupported file format " + fileName);
    }
  }

  private static SampleNode loadLegacyFormat(final File file) throws IOException {
    try (BufferedInputStream bis = new BufferedInputStream(
            Files.newInputStream(file.toPath()))) {
      final CodedInputStream is = CodedInputStream.newInstance(bis);
      is.setRecursionLimit(Short.MAX_VALUE);
     return Converter.fromProtoToSampleNode(ProtoSampleNodes.SampleNode.parseFrom(is));
    }
  }

  private void setFrames(SampleNode samples, String fileName) throws IOException {
    JInternalFrame frame = new StackDumpJInternalFrame(samples, fileName, true);
    frame.setVisible(true);
    desktopPane.add(frame, javax.swing.JLayeredPane.DEFAULT_LAYER);
    frame = new StackDumpJInternalFrame(samples, fileName, false);
    frame.setVisible(true);
    desktopPane.add(frame, javax.swing.JLayeredPane.DEFAULT_LAYER);
  }




  /**
   * @param args the command line arguments
   */
  public static void main(final String[] args) {
    System.setProperty("spf4j.tsdb2.lenientRead", "true");
    /* Set the Nimbus look and feel */
    //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
    /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
     */
    try {
      for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          javax.swing.UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    } catch (ClassNotFoundException ex) {
      java.util.logging.Logger.getLogger(Explorer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
      java.util.logging.Logger.getLogger(Explorer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      java.util.logging.Logger.getLogger(Explorer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(Explorer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /* Create and display the form */
    java.awt.EventQueue.invokeLater(new AbstractRunnable(false) {
      @Override
      @SuppressFBWarnings("PATH_TRAVERSAL_IN")
      public void doRun() throws IOException {
        File[] files = new File[args.length];
        for (int i = 0; i < args.length; i++) {
          files[i] = new File(args[i]);
        }
        new Explorer(files).setVisible(true);
      }
    });
  }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenuItem contentMenuItem;
    private javax.swing.JMenuItem copyMenuItem;
    private javax.swing.JMenuItem cutMenuItem;
    private javax.swing.JMenuItem deleteMenuItem;
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JMenuItem pasteMenuItem;
    // End of variables declaration//GEN-END:variables

  private static class Spf4jFileFilter extends FileFilter {

    @Override
    public boolean accept(final File f) {
      if (f.isDirectory()) {
        return true;
      } else if (f.isFile()) {
        String name = f.getName();
        return (name.endsWith("tsdb") || name.endsWith("tsdb2")
                || name.endsWith("ssdump") || name.endsWith("ssdump2"));
      } else {
        return false;
      }
    }

    @Override
    public String getDescription() {
      return "spf4j dumps";
    }
  }
}
