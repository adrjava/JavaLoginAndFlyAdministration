package aplicatie.view;


import aplicatie.controller.MainControllerAudit;
import aplicatie.controller.MainControllerFly;
import aplicatie.model.Fly;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.*;


class TablePanel extends JPanel {

    MainControllerAudit mainControllerAudit = null;
    private JTable table;
    private JScrollPane scrollPane;
    private String[] columnNames;
    private String[][] dataValues;
    public JButton button = null;
    private MainControllerFly mainControllerFly = null;
    private List<Fly> listAllFly ;
    private Fly fly = null;
    private TableModel model;

    public TablePanel(){

        button = new JButton();
        mainControllerFly = MainControllerFly.getInstance();
        mainControllerAudit = MainControllerAudit.getInstance();
        getListFly();
        setLayout(new BorderLayout());
        columnNames=new String[] {"Sursa", "Destinatie", "Ora Plecare", "Ora Sosire", "Zile", "Pret", "button"};
        initContentTable();

        model=new myTableModel("table");

        table =new JTable( );

        table.setModel(model);

        table.getColumn("button").setCellRenderer(new ButtonRenderer());

        table.getColumn("button").setCellEditor(new ButtonEditor(new JCheckBox()));

        scrollPane=new JScrollPane(table);

        add(scrollPane,BorderLayout.CENTER);

        actionButton();

    }

    private void actionButton() {

        button.addActionListener(

                new ActionListener()

                {

                    public void actionPerformed(ActionEvent event)

                    {

                        mainControllerAudit.resetOraChangePage();

                        int input = JOptionPane.showConfirmDialog(null,"Esti sigur ca doresti stergerea zborului?",null, JOptionPane.YES_NO_OPTION);
                        if (input == JOptionPane.YES_OPTION){

                            if (mainControllerFly.deleteFly(getIdFly())){
                                JOptionPane.showMessageDialog(null,"Zborul a fost sters!!!");
                                teRogActualizeazaTabelul();

                            }else{
                                JOptionPane.showMessageDialog(null,"Verificati conexiunea la baza de date !!!");

                            }

                        }
                    }

                }

        );
    }

    public void teRogActualizeazaTabelul() {

        getListFly();
        initContentTable();
        model = new myTableModel("table");
        table.setModel(model);
        table.getColumn("button").setCellRenderer(new ButtonRenderer());
        table.getColumn("button").setCellEditor(new ButtonEditor(new JCheckBox()));

    }


    public  void initContentTable(){

        dataValues = new String[listAllFly.size()][7];

        for (int i=0; i<listAllFly.size(); i++ ){

            fly = listAllFly.get(i);
            dataValues[i][0] = fly.getSursa();
            dataValues[i][1] = fly.getDestinatie();
            dataValues[i][2] = fly.getOraPlecare();
            dataValues[i][3] = fly.getOraSosire();
            dataValues[i][4] = fly.getZile();
            dataValues[i][5] = String.valueOf(fly.getPret());
            dataValues[i][6] = "";

        }
    }



    class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {

            setOpaque(true);
            setBackground(Color.RED);

        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {

            setText((value == null) ? "" : value.toString());
            return this;

        }

    }

    class ButtonEditor extends DefaultCellEditor {

        private String label;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {

            label = (value == null) ? "" : value.toString();
            button.setText(label);
            return button;

        }

        public Object getCellEditorValue() {

            return new String(label);

        }

    }

    public class myTableModel extends DefaultTableModel {

        String dat;
        JButton button=new JButton("button");

        myTableModel(String tname){

            super(dataValues,columnNames);
            dat=tname;

        }

        public boolean isCellEditable(int row,int cols){

            if( dat=="table"){
                if(cols==0){return false;}
            }
            return true;

        }

    }


    public int getIdFly(){

        int row = table.getSelectedRow();
        System.out.println("rand sters " + row);
        fly = listAllFly.get(row);
        return fly.getId();

    }

    public void getListFly(){
        listAllFly =  mainControllerFly.getAllFly();
    }

}
