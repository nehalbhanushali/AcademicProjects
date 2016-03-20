/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.AdminRole;

import BusinessLogic.EcoSystem;
import BusinessLogic.Employee.Employee;
import BusinessLogic.Employee.Expert;
import BusinessLogic.Employee.Farmer;
import BusinessLogic.Employee.Supplier;
import BusinessLogic.Enterprise.Enterprise;
import BusinessLogic.Network.Network;
import BusinessLogic.Organization.ExpertOrganization;
import BusinessLogic.Organization.FarmerOrganization;
import BusinessLogic.Organization.Organization;
import BusinessLogic.Organization.Organization.Type;
import BusinessLogic.Organization.OrganizationDirectory;
import BusinessLogic.Organization.SupplierOrganization;
import BusinessLogic.Role.ExpertRole;
import BusinessLogic.Role.FarmerRole;
import BusinessLogic.Role.Role;
import BusinessLogic.Role.SupplierRole;
import BusinessLogic.UserAccount.UserAccount;
import Util.Utility;
import static java.time.Clock.system;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author nehal
 */
public class AdminWorkAreaJPanel extends javax.swing.JPanel {

    /**
     * Creates new form AdminWorkAreaJPanel
     */
   private JPanel userProcessContainer;
   private UserAccount userAccount;
   private Enterprise enterprise;

   private OrganizationDirectory directory;
   private EcoSystem system;

    public AdminWorkAreaJPanel(JPanel userProcessContainer, UserAccount account,Enterprise enterprise, EcoSystem system) {
        initComponents();
        
        this.userProcessContainer = userProcessContainer;
        this.userAccount = account;
        this.enterprise = enterprise;
        this.directory = enterprise.getOrganizationDirectory();
        this.system = system;
        adminNameJlabel.setText("Hello, "+userAccount.getEmployee().getName());
        
        entValueLabel.setText(enterprise.getName());
        
       
        
        setImagesOnTabs();
        
        populateTable();
        populateCombo();
         populateOrganizationComboBox();
        populateOrganizationEmpComboBox();
          popOrganizationComboBox();
       // employeeJComboBox.removeAllItems();
        popData();
    }
    
 
    private void populateCombo(){
        organizationJComboBox.removeAllItems();
int numberOfOrgAddingPending = 0;
        for (Type type : Organization.Type.values()){
 
            if (!type.getValue().equals(Type.ADMIN.getValue()) && !isPresentOrganization(type))
            {
            numberOfOrgAddingPending++;
                 organizationJComboBox.addItem(type);   
        }
        }
        
        if(numberOfOrgAddingPending==0){
            
            errOrgsJLabel.setText("All the organizations have been added");
            addOrgJButton.setEnabled(false);
        }
        else{
           errOrgsJLabel.setText("");
            addOrgJButton.setEnabled(true); 
        }
    }
      
        private boolean isPresentOrganization(Type type){
        
     //   boolean isPresent = false;
         for(Organization o: directory.getOrganizationList()) {
             
                if(o.getName().equals(type.getValue())) { 
                    
            //  System.err.println("o list adding type>>  "+type.getValue()); 
            return true;
                }
               
        }
    return false;
    }
      
         private void populateTable(){
        DefaultTableModel model = (DefaultTableModel) organizationJTable.getModel();
        
        model.setRowCount(0);
        
        for (Organization organization : directory.getOrganizationList()){
            Object[] row = new Object[2];
            row[0] = organization.getOrganizationID();
            row[1] = organization.getName();
            
            model.addRow(row);
        }
    }
         
         
          public void populateOrganizationComboBox(){
        organizationJComboBox1.removeAllItems();
        
        for (Organization organization : directory.getOrganizationList()){
            
            if(!organization.getName().equals("Admin Organization")){
              organizationJComboBox1.addItem(organization);  
            }
            
        }
    }
    
    public void populateOrganizationEmpComboBox(){
        organizationEmpJComboBox.removeAllItems();
        
        for (Organization organization : directory.getOrganizationList()){
              if(!organization.getName().equals("Admin Organization")){
            organizationEmpJComboBox.addItem(organization);
              }
        }
    }

    private void populateTable(Organization organization){
        DefaultTableModel model = (DefaultTableModel) organizationEmpJTable.getModel();
           System.err.println(">bh>>>");
        model.setRowCount(0);
        
        for (Employee employee : organization.getEmployeeDirectory().getEmployeeList()){
            System.err.println(">>>>");
            Object[] row = new Object[2];
            row[0] = employee.getId();
            row[1] = employee.getName();
            model.addRow(row);
        }
    }

    public void popOrganizationComboBox() {
        orgUserJComboBox.removeAllItems();

        for (Organization organization : enterprise.getOrganizationDirectory().getOrganizationList()) {
            
              if(!organization.getName().equals("Admin Organization")){
              orgUserJComboBox.addItem(organization);
              }
        }
    }
    boolean flagEmpUserNotCreated = true;
    public void populateEmployeeComboBox(Organization organization){
        employeeJComboBox.removeAllItems();
        int userAccPendingCount =0;
        for (Employee employee : organization.getEmployeeDirectory().getEmployeeList()){
            
            if(!userAccountExists(employee, organization)){
               employeeJComboBox.addItem(employee);
               userAccPendingCount++;
            }
           
        }
        if(userAccPendingCount == 0){
            
                errEmployeeJLabel.setText("All employees in this organization already have user accounts");
                flagEmpUserNotCreated = false;

            }
            else{
                errEmployeeJLabel.setText("");
                flagEmpUserNotCreated = true;         
                
            }
        
    }
    public boolean userAccountExists(Employee employee,Organization organization){
        boolean userAccountExists = false;
      for(UserAccount ua: organization.getUserAccountDirectory().getUserAccountList()){
        
          if( ua.getEmployee().getName() == null ? employee.getName() == null : ua.getEmployee().getName().equals(employee.getName())){
   
          userAccountExists = true;
          
      }
      
      }  
     return userAccountExists;   
    }
    
    private void populateRoleComboBox(Organization organization){
        roleJComboBox.removeAllItems();
        
   
        for (Role role : organization.getSupportedRole()){
            roleJComboBox.addItem(role);
        }
    }

    public void popData() {

        DefaultTableModel model = (DefaultTableModel) userJTable.getModel();

        model.setRowCount(0);

        for (Organization organization : enterprise.getOrganizationDirectory().getOrganizationList()) {
            for (UserAccount ua : organization.getUserAccountDirectory().getUserAccountList()) {
                Object row[] = new Object[2];
                row[0] = ua;
                row[1] = ua.getRole();
                ((DefaultTableModel) userJTable.getModel()).addRow(row);
            }
        }
    }

    
    
     public boolean  checkUsernameExists(String userName){
        boolean ifExists = false;
        
        
       for(UserAccount ua : system.getUserAccountDirectory().getUserAccountList()){
           
           if(ua.getUsername().equals(userName)){
              ifExists =true;  
           }
           
           else{

        for(Network  n : system.getNetworkList()){
            
            for(Enterprise enterprise : n.getEnterpriseDirectory().getEnterpriseList()){
                
            
        for(UserAccount u: enterprise.getUserAccountDirectory().getUserAccountList()){
            
            if(u.getUsername().equals(userName)){
                ifExists =true;
            }
            
            else {
                
                for(Organization o : enterprise.getOrganizationDirectory().getOrganizationList()){
                    for(UserAccount u2 : o.getUserAccountDirectory().getUserAccountList()){
                      if(u2.getUsername().equals(userName)){
                ifExists =true;
            }   
                        
                    }
                }
            }
            
        }
        }
        
        }
        
         }
           
       }
        return ifExists;
    }
    
    
    
//    public boolean  checkUsernameExists(String userName){
//        boolean ifExists = false;
//        
//        
//        for(UserAccount u: enterprise.getUserAccountDirectory().getUserAccountList()){
//            
//            if(u.getUsername().equals(userName)){
//                ifExists =true;
//            }
//            
//            else {
//                
//                for(Organization o : enterprise.getOrganizationDirectory().getOrganizationList()){
//                    for(UserAccount u2 : o.getUserAccountDirectory().getUserAccountList()){
//                      if(u2.getUsername().equals(userName)){
//                ifExists =true;
//            }   
//                        
//                    }
//                }
//            }
//            
//            
//        }
//        
//        
//        return ifExists;
//    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        adminTabbedPane = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        enterpriseLabel = new javax.swing.JLabel();
        entValueLabel = new javax.swing.JLabel();
        addOrgIconButton = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        addEmpIconButton = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        createUserIconButton = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        manageOrgTab = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        addOrgJButton = new javax.swing.JButton();
        organizationJComboBox = new javax.swing.JComboBox();
        errOrgsJLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        organizationJTable = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        manageEmpTab = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        organizationEmpJTable = new javax.swing.JTable();
        addEmployeeJButton = new javax.swing.JButton();
        organizationJComboBox1 = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        empNameJTextField = new javax.swing.JTextField();
        organizationEmpJComboBox = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        errEmpNameJLabel = new javax.swing.JLabel();
        manageUserTab = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        orgUserJComboBox = new javax.swing.JComboBox();
        employeeJComboBox = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        roleJComboBox = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        userNameJTextField = new javax.swing.JTextField();
        passwordJTextField = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        createUserJButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        userJTable = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        errPasswordJlabel = new javax.swing.JLabel();
        errEmployeeJLabel = new javax.swing.JLabel();
        errUserNameJLabel1 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        adminNameJlabel = new javax.swing.JLabel();

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("ADMIN HOME");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 80, -1, -1));

        enterpriseLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        enterpriseLabel.setText("EnterPrise :");
        jPanel1.add(enterpriseLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 170, 120, 30));

        entValueLabel.setText("<value>");
        jPanel1.add(entValueLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 170, 220, -1));

        addOrgIconButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UserInterface/AdminRole/images/addOrg64.png"))); // NOI18N
        addOrgIconButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addOrgIconButtonActionPerformed(evt);
            }
        });
        jPanel1.add(addOrgIconButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 260, 100, 80));

        jLabel19.setText("Add organization");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 350, -1, -1));

        addEmpIconButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UserInterface/AdminRole/images/add_user64.png"))); // NOI18N
        addEmpIconButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addEmpIconButtonActionPerformed(evt);
            }
        });
        jPanel1.add(addEmpIconButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 260, 100, 80));

        jLabel20.setText("Add Employee");
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 350, -1, -1));

        createUserIconButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UserInterface/AdminRole/images/userAcc68.png"))); // NOI18N
        createUserIconButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createUserIconButtonActionPerformed(evt);
            }
        });
        jPanel1.add(createUserIconButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 260, -1, 80));

        jLabel21.setText("Create User Account");
        jPanel1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 350, -1, -1));

        adminTabbedPane.addTab("Home", jPanel1);

        manageOrgTab.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("Organization Type ");
        jPanel5.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 70, -1, -1));

        addOrgJButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UserInterface/AdminRole/images/addOrgImg.png"))); // NOI18N
        addOrgJButton.setText("Add Organization");
        addOrgJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addOrgJButtonActionPerformed(evt);
            }
        });
        jPanel5.add(addOrgJButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 180, 250, 50));

        organizationJComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel5.add(organizationJComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 60, 240, 40));

        errOrgsJLabel.setForeground(new java.awt.Color(255, 51, 102));
        jPanel5.add(errOrgsJLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 120, 270, 30));

        organizationJTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(organizationJTable);

        jPanel5.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 480, 220));

        jLabel13.setText("Organizations");
        jPanel5.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        manageOrgTab.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 87, 1070, 343));

        adminTabbedPane.addTab("Manage Organization", manageOrgTab);

        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        organizationEmpJTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(organizationEmpJTable);

        jPanel6.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, 530, 230));

        addEmployeeJButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UserInterface/AdminRole/images/add_user32.png"))); // NOI18N
        addEmployeeJButton.setText("Create Employee");
        addEmployeeJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addEmployeeJButtonActionPerformed(evt);
            }
        });
        addEmployeeJButton.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                addEmployeeJButtonPropertyChange(evt);
            }
        });
        jPanel6.add(addEmployeeJButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 260, 220, 40));

        organizationJComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        organizationJComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                organizationJComboBox1ActionPerformed(evt);
            }
        });
        jPanel6.add(organizationJComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 70, 190, 40));

        jLabel3.setText("Select organization to view its employees");
        jPanel6.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, 350, -1));

        jLabel4.setText("Name");
        jPanel6.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 150, -1, -1));

        empNameJTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                empNameJTextFieldFocusLost(evt);
            }
        });
        jPanel6.add(empNameJTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 150, 220, 40));

        organizationEmpJComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        organizationEmpJComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                organizationEmpJComboBoxActionPerformed(evt);
            }
        });
        jPanel6.add(organizationEmpJComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 70, 220, 40));

        jLabel5.setText("Organization");
        jPanel6.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 70, -1, -1));

        errEmpNameJLabel.setForeground(new java.awt.Color(255, 51, 102));
        jPanel6.add(errEmpNameJLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 210, 330, 30));

        javax.swing.GroupLayout manageEmpTabLayout = new javax.swing.GroupLayout(manageEmpTab);
        manageEmpTab.setLayout(manageEmpTabLayout);
        manageEmpTabLayout.setHorizontalGroup(
            manageEmpTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manageEmpTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 1110, Short.MAX_VALUE)
                .addContainerGap())
        );
        manageEmpTabLayout.setVerticalGroup(
            manageEmpTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manageEmpTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        adminTabbedPane.addTab("Manage Employee", manageEmpTab);

        manageUserTab.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setText("Organization");
        manageUserTab.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 50, -1, -1));

        orgUserJComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        orgUserJComboBox.setPreferredSize(new java.awt.Dimension(200, 40));
        orgUserJComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orgUserJComboBoxActionPerformed(evt);
            }
        });
        manageUserTab.add(orgUserJComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 50, 230, -1));

        employeeJComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        employeeJComboBox.setMinimumSize(new java.awt.Dimension(200, 40));
        employeeJComboBox.setPreferredSize(new java.awt.Dimension(200, 40));
        employeeJComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeeJComboBoxActionPerformed(evt);
            }
        });
        manageUserTab.add(employeeJComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 120, 230, -1));

        jLabel7.setText("Employee");
        manageUserTab.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 120, -1, -1));

        jLabel8.setText("Role");
        manageUserTab.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 210, -1, -1));

        roleJComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        roleJComboBox.setPreferredSize(new java.awt.Dimension(200, 40));
        roleJComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roleJComboBoxActionPerformed(evt);
            }
        });
        manageUserTab.add(roleJComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 210, 230, -1));

        jLabel9.setText("User Name");
        manageUserTab.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 280, -1, -1));

        userNameJTextField.setPreferredSize(new java.awt.Dimension(200, 40));
        userNameJTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                userNameJTextFieldFocusLost(evt);
            }
        });
        manageUserTab.add(userNameJTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 280, 230, -1));

        passwordJTextField.setPreferredSize(new java.awt.Dimension(200, 40));
        passwordJTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                passwordJTextFieldFocusLost(evt);
            }
        });
        manageUserTab.add(passwordJTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 360, 230, -1));

        jLabel10.setText("Password");
        manageUserTab.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 360, -1, -1));

        createUserJButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UserInterface/AdminRole/images/userAcc32.png"))); // NOI18N
        createUserJButton.setText("Create User Account");
        createUserJButton.setPreferredSize(new java.awt.Dimension(200, 40));
        createUserJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createUserJButtonActionPerformed(evt);
            }
        });
        manageUserTab.add(createUserJButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 450, 230, 49));

        userJTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "User Name", "Role"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(userJTable);

        manageUserTab.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 375, 435));

        jLabel11.setText("Create New User");
        manageUserTab.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 10, -1, -1));

        errPasswordJlabel.setForeground(new java.awt.Color(255, 51, 102));
        manageUserTab.add(errPasswordJlabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 410, 400, 20));

        errEmployeeJLabel.setForeground(new java.awt.Color(255, 51, 102));
        manageUserTab.add(errEmployeeJLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 170, 460, 20));

        errUserNameJLabel1.setForeground(new java.awt.Color(255, 51, 102));
        manageUserTab.add(errUserNameJLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 320, 430, 30));

        jLabel12.setText("User Accounts");
        manageUserTab.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, -1, -1));

        adminTabbedPane.addTab("Manage User", manageUserTab);

        adminNameJlabel.setText("<admin Name>");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(adminTabbedPane, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(adminNameJlabel)
                .addGap(208, 208, 208))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(adminNameJlabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 88, Short.MAX_VALUE)
                .addComponent(adminTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 552, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addOrgJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addOrgJButtonActionPerformed

        Type type = (Type) organizationJComboBox.getSelectedItem();
        Organization org = directory.createOrganization(type);

        if (org instanceof FarmerOrganization){
            org.getSupportedRole().add(new FarmerRole());

        }

        if (org instanceof ExpertOrganization){
            org.getSupportedRole().add(new ExpertRole());

        }

        if (org instanceof SupplierOrganization){
            org.getSupportedRole().add(new SupplierRole());

        }

        populateTable();
        populateCombo();
         populateOrganizationEmpComboBox();
    }//GEN-LAST:event_addOrgJButtonActionPerformed

    private void addEmployeeJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addEmployeeJButtonActionPerformed

        boolean flag = true;
        String empName = empNameJTextField.getText().trim();
        
        
        if(empName.isEmpty()){
            
            errEmpNameJLabel.setText("Employee name can not be empty");
            flag = false;
        }
        
        if(!flagEmpNameIsValid){
            flag= false;
        }
        
        if(flag){
        Organization organization = (Organization) organizationEmpJComboBox.getSelectedItem();
        
        Employee employee =null;
        if(organization instanceof FarmerOrganization)
        {
            employee = new Farmer();
        }
        else if (organization instanceof ExpertOrganization)
        {
            employee = new Expert();
        }
        else if (organization instanceof SupplierOrganization)
        {
            employee = new Supplier();
        }

        employee.setName(empName);
        organization.getEmployeeDirectory().createEmployee(employee);

        JOptionPane.showMessageDialog(this, "Employee created successfully");
        popOrganizationComboBox();
        refreshEmpForm();
        }
        //  addJButton.setEnabled(false);
    }//GEN-LAST:event_addEmployeeJButtonActionPerformed

    private void organizationJComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_organizationJComboBox1ActionPerformed
        Organization organization = (Organization) organizationJComboBox1.getSelectedItem();
        if (organization != null){
            
            populateTable(organization);
        }
    }//GEN-LAST:event_organizationJComboBox1ActionPerformed

    private void organizationEmpJComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_organizationEmpJComboBoxActionPerformed
         refreshEmpForm();
    }//GEN-LAST:event_organizationEmpJComboBoxActionPerformed

    private void orgUserJComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orgUserJComboBoxActionPerformed
        Organization organization = (Organization) orgUserJComboBox.getSelectedItem();
        if (organization != null){
            populateEmployeeComboBox(organization);
            populateRoleComboBox(organization);
            refreshUserAccForm();
        }
    }//GEN-LAST:event_orgUserJComboBoxActionPerformed

    public void refreshUserAccForm(){
        
        userNameJTextField.setText("");
        passwordJTextField.setText("");
        errUserNameJLabel1.setText("");
        errPasswordJlabel.setText("");
        
    }
    
    public void  refreshEmpForm(){
        
        empNameJTextField.setText("");
        errEmpNameJLabel.setText("");
    }
    private void createUserJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createUserJButtonActionPerformed
       
        boolean flag = true;
        
      
        
        
        String userName = userNameJTextField.getText();
        String password = passwordJTextField.getText();
        
      if(userName.isEmpty()){
        errUserNameJLabel1.setText("UserName can not be empty");
        flag = false;
        
      }
      if(password.isEmpty()){
         errPasswordJlabel.setText("Password can not be empty");
         flag = false;
         
          
      }  
       if(flagEmpUserNotCreated && flagPasswordIsValid && flagUserNameDoesNotExist){
           
           
       } 
       else{
           flag = false;
       } 

       
       
       if(flag){
            Organization organization = (Organization) orgUserJComboBox.getSelectedItem();
            Employee employee = (Employee) employeeJComboBox.getSelectedItem();
         
            
            
            if(! userAccountExists(employee, organization)){

          
                
                 Role role = (Role) roleJComboBox.getSelectedItem();

                organization.getUserAccountDirectory().createUserAccount(userName, password, employee, role);

                JOptionPane.showMessageDialog(this, "User account for "+ employee.getName()+ " created successfully");
                popData();  
                refreshUserAccForm();
            }
            else{

                JOptionPane.showMessageDialog(this, "User Account already Exists for this employee");
                
            }
               
           
       }
       
       
    }//GEN-LAST:event_createUserJButtonActionPerformed
    boolean flagUserNameDoesNotExist = true;
    private void userNameJTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_userNameJTextFieldFocusLost
         String userName = userNameJTextField.getText().trim();
        
         if(!userName.isEmpty()){
             
             if(Utility.notLongerThanRequired(userName.length(),10)){
                 
             
             if(Utility.isAlphanumeric(userName)){ // for validusername

        if(checkUsernameExists(userName)){
            errUserNameJLabel1.setText("UserName Already Exists. Please choose another username");
           flagUserNameDoesNotExist = false;
          //  JOptionPane.showMessageDialog(this, "UserName Already Exists. Please choose another username");
        }else{
           errUserNameJLabel1.setText("");
           flagUserNameDoesNotExist = true; 
        }
             }else{
                 
                  errUserNameJLabel1.setText("User name can be only alphanumeric. No special Characters and no spaces allowed.");
                  flagUserNameDoesNotExist = false;  
             }
             }else{
             errUserNameJLabel1.setText("User name can not be longer than 10 places.");
                  flagUserNameDoesNotExist = false;  
         }
         }
         else{
             errUserNameJLabel1.setText("Please enter a userName");
           flagUserNameDoesNotExist = false;  
         }
    }//GEN-LAST:event_userNameJTextFieldFocusLost
boolean flagPasswordIsValid = true;
    private void passwordJTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passwordJTextFieldFocusLost
     String password = passwordJTextField.getText();
     
     if(!password.isEmpty()){
         
      if(Utility.notLongerThanRequired(password.length(), 20))   {
         if(Utility.validPassword(password)){
              errPasswordJlabel.setText("");
             flagPasswordIsValid = true;
             
         }else{
             
             errPasswordJlabel.setText("Password Invalid. (Format : A-Z a-z 0-9 _&^%$#!~@- and no spaces)");
             flagPasswordIsValid = false;
         }
      }
      else{
           errPasswordJlabel.setText("Password can have only upto 20 characters");
             flagPasswordIsValid = false; 
      }
         
     }
       
    }//GEN-LAST:event_passwordJTextFieldFocusLost

    private void employeeJComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employeeJComboBoxActionPerformed

        refreshUserAccForm();
    }//GEN-LAST:event_employeeJComboBoxActionPerformed

    private void roleJComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roleJComboBoxActionPerformed
     refreshUserAccForm();
    }//GEN-LAST:event_roleJComboBoxActionPerformed
boolean flagEmpNameIsValid = true;
    private void empNameJTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_empNameJTextFieldFocusLost
      String empName = empNameJTextField.getText().trim();
      
      if(!empName.isEmpty()){
      if(Utility.notLongerThanRequired(empName.length(), 20)){
      if(!Utility.isValidName(empName)){
          
          errEmpNameJLabel.setText("Name can contain only alphabets and spaces.");
          flagEmpNameIsValid= false;
          
      }else{
          
          errEmpNameJLabel.setText("");
          flagEmpNameIsValid= true; 
      }
      
      }
      else{
           errEmpNameJLabel.setText("Name can not be greater than 20 alphabets");
        //   empNameJTextField.setText("");
          flagEmpNameIsValid= false; 
      }
      }
      else{
          
           errEmpNameJLabel.setText("Name can not be empty");
          flagEmpNameIsValid= false;  
      }
          
    }//GEN-LAST:event_empNameJTextFieldFocusLost

    private void addEmployeeJButtonPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_addEmployeeJButtonPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_addEmployeeJButtonPropertyChange

    private void addOrgIconButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addOrgIconButtonActionPerformed
      
        
        adminTabbedPane.setSelectedIndex(1);
    }//GEN-LAST:event_addOrgIconButtonActionPerformed

    private void addEmpIconButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addEmpIconButtonActionPerformed
      adminTabbedPane.setSelectedIndex(2);
    }//GEN-LAST:event_addEmpIconButtonActionPerformed

    private void createUserIconButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createUserIconButtonActionPerformed
      adminTabbedPane.setSelectedIndex(3);
    }//GEN-LAST:event_createUserIconButtonActionPerformed

    
    public void setImagesOnTabs(){
Utility u = new Utility();
     
   
       adminTabbedPane.setIconAt(0, u.createImageIcon("images/home.png"));
        adminTabbedPane.setIconAt(1, u.createImageIcon("images/addOrgImg.png"));//expert
       adminTabbedPane.setIconAt(2, u.createImageIcon("images/add_user24.png"));
       adminTabbedPane.setIconAt(3, u.createImageIcon("images/userAcc24.png"));
       
        
       
 
    
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addEmpIconButton;
    private javax.swing.JButton addEmployeeJButton;
    private javax.swing.JButton addOrgIconButton;
    private javax.swing.JButton addOrgJButton;
    private javax.swing.JLabel adminNameJlabel;
    private javax.swing.JTabbedPane adminTabbedPane;
    private javax.swing.JButton createUserIconButton;
    private javax.swing.JButton createUserJButton;
    private javax.swing.JTextField empNameJTextField;
    private javax.swing.JComboBox employeeJComboBox;
    private javax.swing.JLabel entValueLabel;
    private javax.swing.JLabel enterpriseLabel;
    private javax.swing.JLabel errEmpNameJLabel;
    private javax.swing.JLabel errEmployeeJLabel;
    private javax.swing.JLabel errOrgsJLabel;
    private javax.swing.JLabel errPasswordJlabel;
    private javax.swing.JLabel errUserNameJLabel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel manageEmpTab;
    private javax.swing.JPanel manageOrgTab;
    private javax.swing.JPanel manageUserTab;
    private javax.swing.JComboBox orgUserJComboBox;
    private javax.swing.JComboBox organizationEmpJComboBox;
    private javax.swing.JTable organizationEmpJTable;
    private javax.swing.JComboBox organizationJComboBox;
    private javax.swing.JComboBox organizationJComboBox1;
    private javax.swing.JTable organizationJTable;
    private javax.swing.JTextField passwordJTextField;
    private javax.swing.JComboBox roleJComboBox;
    private javax.swing.JTable userJTable;
    private javax.swing.JTextField userNameJTextField;
    // End of variables declaration//GEN-END:variables
}
