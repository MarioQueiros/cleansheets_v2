/*
 * Copyright (c) 2005 Einar Pehrson <einar@pehrson.nu>.
 *
 * This file is part of
 * CleanSheets - a spreadsheet application for the Java platform.
 *
 * CleanSheets is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * CleanSheets is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CleanSheets; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package csheets.io;

import csheets.HibernateUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import csheets.core.Workbook;
import csheets.ext.ExtensionManager;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * A codec for the native CleanSheets format that uses Java Serialization.
 *
 * @author Einar Pehrson
 */
public class CLSCodec implements Codec {

    String[] annotations;

    /**
     * Creates a new CleanSheets codec.
     */
    public CLSCodec() {
    }

    private int versionControlWindow(List list) {
        DefaultListModel dlm = new DefaultListModel();

        int index = 1;
        String strAux = null;
        VersionControl xml = null;
        dlm.add(0, "Load the version from hard-drive");

        annotations = new String[list.size()];


        int j = 0;
        for (Iterator<VersionControl> i = list.iterator(); i.hasNext();) {
            xml = i.next();
            annotations[j] = xml.getM_annotation();
            j++;
        }


        j = 0;
        for (Iterator<VersionControl> i = list.iterator(); i.hasNext();) {
            xml = i.next();
            dlm.add(index, "Version: " + xml.getM_id() + " - Timestamp: " + xml.getM_key().getM_timestamp());

            index++;
            j++;
        }

        JList jlist = new JList(dlm) {

            public String getToolTipText(MouseEvent e) {
                int index = locationToIndex(e.getPoint());
                if (-1 < index) {
                    if (index == 0) {
                        return null;
                    } else {
                        String item = annotations[index - 1];
                        return item;
                    }
                } else {
                    return null;
                }
            }
        };
        JScrollPane scrollPane = new JScrollPane(jlist);

        JPanel jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.PAGE_AXIS));

        jp.add(scrollPane);
        JLabel jl = new JLabel("(Mouse over one item to see the annotations)");
        jp.add(jl);

        JOptionPane.showMessageDialog(null, jp, "Choose a version", JOptionPane.INFORMATION_MESSAGE);

        return jlist.getSelectedIndex();
    }

    public Workbook read(InputStream stream, File file) throws IOException, ClassNotFoundException {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        org.hibernate.Transaction tx = session.beginTransaction();

        org.hibernate.Query query = session.createQuery("from csheets.io.VersionControl where filename=:fn order by timestampfile DESC");
        query.setParameter("fn", file.getName());

        List list = query.list();

        int position = versionControlWindow(list);
        while (position == -1) {
            position = versionControlWindow(list);
        }
        VersionControl xml = null;
        if (position != 0) {
            xml = (VersionControl) list.get(position - 1);

            String value = null;
            java.sql.Blob blob = null;
            try {
                blob = xml.getM_blob();

                stream = blob.getBinaryStream();

            } catch (Exception ex) {
            }
        }

        ObjectInputStream ois = new DynamicObjectInputStream(stream, ExtensionManager.getInstance().getLoader());
        tx.commit();
        session.close();
        return (Workbook) ois.readObject();
    }

    public void write(Workbook workbook, OutputStream stream, File file) throws IOException {

        ObjectOutputStream oos = new ObjectOutputStream(stream);
        oos.writeObject(workbook);
        oos.flush();
        oos.close();
        databaseSave(file);
    }

    private void databaseSave(File file) throws FileNotFoundException, IOException {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        org.hibernate.Transaction tx = session.beginTransaction();

        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

        org.hibernate.Query query = session.createQuery("from csheets.io.VersionControl where filename=:idg order by id DESC");
        query.setParameter("idg", file.getName());

        List list = query.list();

        FileInputStream fs = new FileInputStream(file);
        java.sql.Blob blob = Hibernate.createBlob(fs);
        VersionControlID xml = new VersionControlID(file.getName(), currentTimestamp);
        VersionControl xmlvc = null;
        VersionControl vc = null;

        String annotation = annotationWindow();

        if (!annotation.equals("")) {
            if (list.isEmpty()) {
                vc = new VersionControl(xml, 1, blob, annotation);
            } else {
                xmlvc = (VersionControl) list.get(0);
                int id = xmlvc.getM_id();
                vc = new VersionControl(xml, ++id, blob, annotation);
            }
        } else {
            if (list.isEmpty()) {
                vc = new VersionControl(xml, 1, blob);
            } else {
                xmlvc = (VersionControl) list.get(0);
                int id = xmlvc.getM_id();
                vc = new VersionControl(xml, ++id, blob);
            }
        }

        session.flush();
        session.save(vc);
        tx.commit();

        session.close();
    }

    private String annotationWindow() {
        String aux = "";
        JPanel jp;
        JLabel jl = new JLabel("Insert an annotation about the file:");
        JTextField jtf = new JTextField(15);
        jp = new JPanel();
        jp.setLayout(new GridLayout(2, 1));
        jp.add(jl);
        jp.add(jtf);

        JOptionPane.showMessageDialog(null, jp, "Insert an annotation", JOptionPane.INFORMATION_MESSAGE);

        aux = jtf.getText();
        aux = aux.trim();

        if (!aux.equals("")) {
            return aux;
        } else {
            return aux;
        }
    }
}