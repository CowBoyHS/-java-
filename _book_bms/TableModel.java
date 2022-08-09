package book_bms;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class TableModel extends AbstractTableModel {//数据模型，用于展示从数据库中查出来的数据的一种模型，结合Jtable使用
      private Connection connection;
      private Statement statement;
      private ResultSet resultSet;
      private ResultSetMetaData metaData;
      private int ColumnNumber;//列数
      private int RowNumber;//行数



      public TableModel(String url,String uname,String pswd,String sql) throws SQLException {
            this.connection=DriverManager.getConnection(url,uname,pswd);
            this.statement=this.connection.createStatement();
            setQuery(sql);
      }

      
      public int getRowCount() {
            try {
                  resultSet.last();
            } catch (SQLException e) {
                  throw new RuntimeException(e);
            }
            try {
                  return resultSet.getRow();
            } catch (SQLException e) {
                  throw new RuntimeException(e);
            }
      }

      @Override
      public int getColumnCount() {
            try {
                  return metaData.getColumnCount();
            } catch (SQLException e) {
                  throw new RuntimeException(e);
            }
      }

      @Override
      public Object getValueAt(int rowIndex, int columnIndex) {
            try {
                  resultSet.absolute( rowIndex + 1 );
            } catch (SQLException e) {
                  throw new RuntimeException(e);
            }
            try {
                  return resultSet.getObject( columnIndex + 1 );
            } catch (SQLException e) {
                  throw new RuntimeException(e);
            }
      }

      @Override
      public String getColumnName(int column) {
            try {
                  return metaData.getColumnName(column + 1);
            } catch (SQLException e) {
                  throw new RuntimeException(e);
            }
      }

      @Override
      public boolean isCellEditable(int rowIndex, int columnIndex) {
            return true;
      }
      public void setQuery(String sql) throws SQLException {
            resultSet=statement.executeQuery(sql);
            metaData=resultSet.getMetaData();
            fireTableStructureChanged();
      }
//------------------------------------------测试
    public static void main(String[] args) throws SQLException {
            //
          String url="jdbc:mysql://localhost:3306。?useSSL = false & &serverTimezone = GMT";
          String username="root";
          String password="159357HH";
          String sql="select * from admin";
          JFrame jf=new JFrame();
          TableModel tableModel=new TableModel(url,username,password,sql);
          JTable jTable=new JTable(tableModel);
          JScrollPane jsp=new JScrollPane(jTable);
          jf.add(jsp, BorderLayout.SOUTH);

          JTextArea jTextArea=new JTextArea();
          JButton jButton_submit=new JButton("提交");
          JPanel jPanel=new JPanel();
//          jPanel.setVisible(false);

          jPanel.add(jTextArea);
          jPanel.add(jButton_submit);

          jf.add(jPanel);

          jButton_submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                      String sql=jTextArea.getText();
                      try {
                            tableModel.setQuery(sql);
                      } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                      }
                }
          });

          jf.setBounds(200,300,200,300);
          jf.setVisible(true);
          jf.pack();
          jf.setDefaultCloseOperation(1);


    }
    //--------------------------------------------------
}
