package databasegui;
import com.sun.glass.events.KeyEvent;
import java.awt.Color;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.util.Locale;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.text.PlainDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
/**
 *
 * @author zNova
 */

//Fix the update code
class JTextFieldLimit extends PlainDocument {
  private int limit;
  JTextFieldLimit(int limit) {
    super();
    this.limit = limit;
  }

  JTextFieldLimit(int limit, boolean upper) {
    super();
    this.limit = limit;
  }
  @Override
  public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
    if (str == null)
      return;
    if ((getLength() + str.length()) <= limit) {
      super.insertString(offset, str, attr);
    }
  }
}

public class DataBasePalette extends javax.swing.JFrame {
    Connection con = null;//Will have a value at InitDataBase function
    DefaultTableModel tableModel = null;

    
    public TableModel resultSetToTableModel(ResultSet rs) {
        try {
            ResultSetMetaData metaData = rs.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            Vector columnNames = new Vector();

            // Get the column names
            for (int column = 0; column < numberOfColumns; column++) {
                columnNames.addElement(metaData.getColumnLabel(column + 1));
            }

            // Get all rows.
            Vector rows = new Vector();

            while (rs.next()) {
                Vector newRow = new Vector();

                for (int i = 1; i <= numberOfColumns; i++) {
                    newRow.addElement(rs.getObject(i));
                }

                rows.addElement(newRow);
            }

            return new DefaultTableModel(rows, columnNames);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private void UpdateTable()
      throws SQLException {
    Statement stmt = null;
    String query = "select * from information";
    try {
        stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
       jTable1.setModel(resultSetToTableModel(rs)); 
       
       rs.close();
    } catch (SQLException e ) {
        throw e;
    } finally {
        if (stmt != null) { stmt.close(); }
    }  
    }
    
    public int getNumberRows(){
        int returnval = -1;
    try{
       Statement statement = con.createStatement();
       String query =
            "select NAME, ADDRESS, GENDER,CONTACT,UID " +
            "from " + "gradeict" + ".information";
       ResultSet resultset = statement.executeQuery(query);
       if(resultset.last()){
           returnval = resultset.getRow();
       } else {
           returnval = 0; //just cus I like to always do some kinda else statement.
       }
    } catch (Exception e){
       e.printStackTrace();
    }
    
    return returnval;
}
    
    Vector<String> dataList = new Vector<>();
    
    void SetValueToVector()throws SQLException{
     String[] tmp = new String[getNumberRows() + 1];
    Statement stmt = null;
    String query =
            "select NAME, ADDRESS, GENDER,CONTACT,UID " +
            "from " + "gradeict" + ".information";
    try {
        stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        
        while (rs.next()) {
            String Name = rs.getString("NAME");
            tmp[rs.getRow()] += Name;
            tmp[rs.getRow()] = tmp[rs.getRow()].replace("null", "");
            dataList.add(tmp[rs.getRow()]);
        }
    } catch (SQLException e ) {
        System.out.println("Caught an exception parsing vector");
    } 
    }
   
    
    
     public void batchUpdate(String squery) throws SQLException {
     Statement stmt = null;
     try {
        con.setAutoCommit(false);
        stmt = con.createStatement();

        stmt.addBatch(squery);
        stmt.executeBatch();
        con.commit();

     } catch(BatchUpdateException b) {
       throw b;
     } catch(SQLException ex) {
        throw ex;
     } finally {
        if (stmt != null) { stmt.close(); }
        con.setAutoCommit(true);
    }
    }
    void InitDataBase(){
         try{
            Class.forName("com.mysql.jdbc.Driver");//Loads the driver
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gradeict","dogmatism","123");//Makes connection to the database
            
            String query =
            "select NAME, ADDRESS, GENDER,CONTACT,UID " +
            "from " + "gradeict" + ".information";

            Statement se = con.createStatement();
            ResultSet rs =se.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            tableModel = new DefaultTableModel();
            int cCount = rsmd.getColumnCount();
            for(int i = 1; i <= cCount; i++){
            String cName = rsmd.getColumnLabel(i);
            tableModel.addColumn(cName);
            rs.close();
            }
           SetValueToVector();
        }catch(Exception ex){
            System.out.println("Start the database First");
            //ex.printStackTrace();
        }    
    }
    
    void OverwriteComponents(){
        this.setLocationRelativeTo(null);
        JTableHeader head = jTable1.getTableHeader();
        head.setBackground(Color.red);
       // head.setForeground(Color.GREEN);
        jButton4.setVisible(false);
         jTable1.setModel(tableModel);
         jTextField5.setDocument(new JTextFieldLimit(11));
         jTextField2.setDocument(new JTextFieldLimit(1));
         try {
        UpdateTable();
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    public DataBasePalette() {
        InitDataBase();//First call you cant set value for jTable1 as it gets overwritten in the InitComponent method
        initComponents();//Sets value to the jTable and other components i have
        OverwriteComponents();//Sets the jTable1 value
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Assassination Bot Simulator 1.0.0");
        setBackground(new java.awt.Color(0, 0, 0));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setBackground(new java.awt.Color(0, 0, 0));
        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 255, 0));
        jLabel6.setText("Victims :");
        jLabel6.setToolTipText("List of dead people");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, 38));

        jTable1.setBackground(new java.awt.Color(0, 0, 0));
        jTable1.setForeground(new java.awt.Color(102, 255, 0));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setGridColor(new java.awt.Color(51, 255, 0));
        jTable1.setSelectionBackground(new java.awt.Color(102, 102, 102));
        jTable1.setSelectionForeground(new java.awt.Color(51, 255, 0));
        jTable1.getTableHeader().setResizingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTable1MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTable1MouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 568, 350));

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setBackground(new java.awt.Color(0, 0, 0));
        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 255, 0));
        jLabel8.setText("This is only a simulation program and all names are fictional");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 400, 38));

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setForeground(new java.awt.Color(51, 255, 0));
        jLabel2.setText("Gender :");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(244, 97, 51, -1));

        jTextField2.setBackground(new java.awt.Color(153, 153, 153));
        jTextField2.setToolTipText("Gender of target person");
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField2KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField2KeyTyped(evt);
            }
        });
        jPanel2.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(299, 94, 120, -1));

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setForeground(new java.awt.Color(51, 255, 0));
        jLabel3.setText("Name :");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 58, -1, -1));

        jTextField3.setBackground(new java.awt.Color(153, 153, 153));
        jTextField3.setToolTipText("Name of target person");
        jPanel2.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 55, 156, -1));

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setForeground(new java.awt.Color(51, 255, 0));
        jLabel4.setText("Address :");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        jTextField4.setBackground(new java.awt.Color(153, 153, 153));
        jTextField4.setToolTipText("Address of target person");
        jPanel2.add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 94, 156, -1));

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setForeground(new java.awt.Color(51, 255, 0));
        jLabel5.setText("Contact :");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(244, 58, -1, -1));

        jTextField5.setBackground(new java.awt.Color(153, 153, 153));
        jTextField5.setToolTipText("Contact Number of target person");
        jTextField5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField5KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField5KeyTyped(evt);
            }
        });
        jPanel2.add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(299, 55, 120, -1));

        jLabel7.setBackground(new java.awt.Color(0, 0, 0));
        jLabel7.setForeground(new java.awt.Color(51, 255, 0));
        jLabel7.setText("ID :");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, -1, -1));

        jTextField7.setBackground(new java.awt.Color(153, 153, 153));
        jTextField7.setToolTipText("ID of target person");
        jPanel2.add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 137, 156, -1));

        jButton1.setBackground(new java.awt.Color(0, 0, 0));
        jButton1.setFont(new java.awt.Font("思源黑体M", 0, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(51, 255, 51));
        jButton1.setText("Assassinate");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 130, 140, -1));

        jButton3.setBackground(new java.awt.Color(0, 0, 0));
        jButton3.setForeground(new java.awt.Color(51, 255, 51));
        jButton3.setText("Refresh List");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 190, -1, -1));

        jButton4.setBackground(new java.awt.Color(0, 0, 0));
        jButton4.setForeground(new java.awt.Color(51, 255, 51));
        jButton4.setText("Edit Target Info");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 190, -1, -1));

        jButton6.setBackground(new java.awt.Color(0, 0, 0));
        jButton6.setForeground(new java.awt.Color(51, 255, 51));
        jButton6.setText("Remove selected dead person");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, -1, 30));

        jButton7.setBackground(new java.awt.Color(0, 0, 0));
        jButton7.setForeground(new java.awt.Color(51, 255, 51));
        jButton7.setText("Remove all Dead Persons");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 240, 170, 30));

        jButton2.setBackground(new java.awt.Color(0, 0, 0));
        jButton2.setForeground(new java.awt.Color(51, 255, 51));
        jButton2.setText("Kill random person");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 130, -1));

        jLabel9.setBackground(new java.awt.Color(0, 0, 0));
        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 255, 0));
        jLabel9.setText("Target Info:");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 6, -1, 38));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String[] bd = {jTextField3.getText(),jTextField4.getText(),jTextField2.getText(),jTextField5.getText(),jTextField7.getText()};
        String squery = Insert(bd); 
        try {
        batchUpdate(squery);//Update the database first
        DefaultTableModel modeltable = tableModel;//Parse the data
        modeltable.addRow(new Object[]{jTextField2.getText(),jTextField3.getText(),jTextField4.getText(),jTextField5.getText(),jTextField7.getText(),modeltable.getRowCount()});//Add rows on jTable
        UpdateTable();
        } catch(BatchUpdateException b) {
        JOptionPane.showMessageDialog(null,  "Looks like this person has been cured","Cured already",0);
        } catch(SQLException ex) {
       JOptionPane.showMessageDialog(null,  "Looks like this person has been cured","Cured already",0);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);//Set this to prevent the window closing even if the user did not select yes
        int returnval = JOptionPane.showOptionDialog(null,
                    "Are you sure ?",
                    "Just checking", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, null, null);
        if(returnval == JOptionPane.YES_OPTION)
            this.dispose();     
    }//GEN-LAST:event_formWindowClosing

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    }//GEN-LAST:event_formWindowClosed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
        UpdateTable();
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }//GEN-LAST:event_jButton3ActionPerformed
   
    private String format(String txt){//returns the formatted string for mysql syntax
        String escape = "\"";//String escape sequence
        return escape + txt + escape;
    }
    private String formatComma(String txt){//returns the formatted string for mysql syntax but with comma
        String escape = "\"";
        String comma = ",";
        return comma + escape + txt + escape;
    }
    
    private String Update(String[] arr){
        int row = jTable1.getSelectedRow();
        String id = jTable1.getModel().getValueAt(row, 0).toString();
        return "update `information` set `NAME` = '" +arr[0]+ "', `ADDRESS` = '" + arr[1]+"', `GENDER` = '" +arr[2]+ "', `CONTACT` = '" +arr[3]+"', `UID` = '"+arr[4]+"'  where `id` = '"+id+"'";
    }
   
    private String Insert(String[] arr){
        String tmp = null;
        tmp = "insert into information (NAME,ADDRESS,GENDER,CONTACT,UID) ";
        tmp += "values (";
        for(int i = 0 ; i < arr.length; i++){
            if(i == 0){
            tmp += format(arr[i]);//The first element in array must not have comma since its at zero
            }else{
            tmp += formatComma(arr[i]);
            }
        }
        tmp += ")";
        return tmp;
    }
    
    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        int row = jTable1.getSelectedRow();
        try{
        String id = jTable1.getModel().getValueAt(row, 0).toString();
        PreparedStatement ps = null;
        String delRow = "delete from information where id='"+id+"'";
        try {
            ps = con.prepareStatement(delRow);
            ps.execute();
            ps.close();
            UpdateTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,  e.getMessage());
        }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,  "Select something","Error",0);
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
       for (int i = jTable1.getRowCount() - 1; i >= 0; i--) {
        String id = jTable1.getModel().getValueAt(i, 0).toString();
        PreparedStatement ps = null;
        String delRow = "delete from information where id='"+id+"'";
        try {
            ps = con.prepareStatement(delRow);
            ps.execute();
            ps.close();
            UpdateTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,  e.getMessage());
        }
        } 
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyTyped
        int len = jTextField2.getKeyListeners().length;
        if(len > 0 && len < 2){
        if(evt.getKeyChar() == 'M' || evt.getKeyChar() == 'F')
           evt.consume();
       }
    }//GEN-LAST:event_jTextField2KeyTyped
    
    public String removeLast(String str) {
        if(str.length() > 0){
        str = str.substring(0, str.length() - 1);
        }
        return str;
    }
    
    private void jTextField5KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField5KeyTyped
            
    }//GEN-LAST:event_jTextField5KeyTyped

    private int randomint(int min,int max){
        return ThreadLocalRandom.current().nextInt(min, max);
    }
    
    private long randomlong(long min,long max){
        return ThreadLocalRandom.current().nextLong(min, max);
    }
    
    private String GetCountry(){
    String[] countryCodes = Locale.getISOCountries();
    String countryCode = countryCodes[randomint(0,countryCodes.length)];
    Locale obj = new Locale("", countryCode);
    return obj.getDisplayCountry();
    }
    private boolean CheckDataVectorAgainstName(String name){
        for(int i = 0; i < dataList.size(); i++){
            if(dataList.elementAt(i).contains(name))return true;
        }
        return false;
    }  
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String[] Names = {"Meliodas","Goku","Naruto","Luffy","Ash","Aang","Bobby","Duterte","Digong","Dexter","Mark","John","Angelo","James","Jasper","Douglas","Archie","Dodong"};
        String[] Gender = {"M","F"};
        String[] ID = {"999-1551","VIP","Diamond","Gold","Black VIP","God"};
        String fName = Names[randomint(0,Names.length)];
        String fAddress = GetCountry();
        String fGender = Gender[randomint(0,Gender.length)];
        String fContact = String.valueOf(randomlong(11111111111L,99999999999L));
        String fID = ID[randomint(0,ID.length)];
        String[] bd = {fName,fAddress,fGender,fContact,fID};
        String squery = Insert(bd);
        try {  
        if(!CheckDataVectorAgainstName(fName)){//First Check
        SetValueToVector();//Set val
        if(!CheckDataVectorAgainstName(fName)){//Reverify
        //System.out.println(squery);
        batchUpdate(squery);//Update the database first
        DefaultTableModel modeltable = tableModel;//Parse the data
        modeltable.addRow(new Object[]{jTextField2.getText(),jTextField3.getText(),jTextField4.getText(),jTextField5.getText(),jTextField7.getText(),modeltable.getRowCount()});//Add rows on jTable
        UpdateTable();
        }else{
            //System.out.println("Already there");
        }
        }else{
            //System.out.println("Already there");
        }
        } catch(BatchUpdateException b) {
             System.out.println(b.getMessage());
      // JOptionPane.showMessageDialog(null,  "Looks like this person has been cured","Cured already v1",0);
        } catch(SQLException ex) {
       // JOptionPane.showMessageDialog(null,  "Looks like this person has been cured","Cured already v2",0);
        System.out.println(ex.getMessage());
        }
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        DefaultTableModel dtm = (DefaultTableModel)jTable1.getModel();
        int row = jTable1.getSelectedRow();
        jTextField3.setText(dtm.getValueAt(row, 1).toString());
        jTextField4.setText(dtm.getValueAt(row, 2).toString());
        jTextField2.setText(dtm.getValueAt(row, 3).toString());
        jTextField5.setText(dtm.getValueAt(row, 4).toString());
        jTextField7.setText(dtm.getValueAt(row, 5).toString());
       
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTextField5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField5KeyPressed
        if(evt.getExtendedKeyCode() == KeyEvent.VK_BACKSPACE){
                jTextField5.setText(removeLast(jTextField5.getText()));
            }   else {
               jTextField5.setEditable(false);
            } 
            if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9') {
               jTextField5.setEditable(true);
            } else {
               jTextField5.setEditable(false);
            }
// TODO add your handling code here:
    }//GEN-LAST:event_jTextField5KeyPressed

    private void jTextField2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyPressed
        if(evt.getExtendedKeyCode() == KeyEvent.VK_BACKSPACE){
                jTextField2.setText(removeLast(jTextField2.getText()));
            }   else {
               jTextField2.setEditable(false);
            } 
            if (evt.getKeyChar() == 'F' || evt.getKeyChar() == 'M' || evt.getKeyChar() == 'f' || evt.getKeyChar() == 'm') {
               jTextField2.setEditable(true);
            } else {
               jTextField2.setEditable(false);
            }
    }//GEN-LAST:event_jTextField2KeyPressed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
        String[] arr = {jTextField3.getText(),jTextField4.getText(),jTextField2.getText(),jTextField5.getText(),jTextField7.getText()};
        String xD = Update(arr);
        batchUpdate(xD);
        DefaultTableModel modeltable = tableModel;//Parse the data
        modeltable.addRow(new Object[]{jTextField2.getText(),jTextField3.getText(),jTextField4.getText(),jTextField5.getText(),jTextField7.getText(),modeltable.getRowCount()});//Add rows on jTable
        UpdateTable();
        } catch(BatchUpdateException b) {
        JOptionPane.showMessageDialog(null,  "Looks like this person has been updated","Cured already",0);
        b.printStackTrace();
        } catch(SQLException ex) {
       JOptionPane.showMessageDialog(null,  "Looks like this person has been updated","Cured already",0);
       ex.printStackTrace();
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTable1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseReleased
        jButton4.setEnabled(false);
        jButton4.setVisible(false);
    }//GEN-LAST:event_jTable1MouseReleased

    private void jTable1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseExited
        jButton4.setEnabled(true);
        jButton4.setVisible(true);
    }//GEN-LAST:event_jTable1MouseExited

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(DataBasePalette.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DataBasePalette.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DataBasePalette.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DataBasePalette.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DataBasePalette().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField7;
    // End of variables declaration//GEN-END:variables
}
