package ${class.typePackage};



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.SQLException;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.util.ArrayList;
import java.util.List;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.TextField;
import javax.swing.JCheckBox;
<#list properties as property>
<#if property.upper == 1 && property.foreignKey == true >   
import dao.${property.packagePath}.${property.name?cap_first}HibernateDao;
import ejb.${property.packagePath}.${property.name?cap_first};
import standardForm.${property.packagePath}.${property.name?cap_first}Panel;
</#if>  
</#list>
import enumerations.*;
import framework.*;

import net.miginfocom.swing.MigLayout;

${class.visibility} class ${class.name}Panel extends JPanel{

	
	<#list properties as property>
		<#if property.upper == 1 && property.foreignKey == false && property.isBoolean == false && property.isEnumeration == false && property.isTextArea == false > 
			<#if property.isTextField == true >
				private  JTextField tf${property.name} = new JTextField(${property.textWidth});
			<#else>
				private  JTextField tf${property.name} = new JTextField(10);
			</#if>
	   		private  JLabel     lbl${property.name} = new JLabel("${property.lblName}<#if property.required == true >*</#if>");
	    </#if> 
	    
	    <#if property.upper == 1 && property.foreignKey == true >   
	   		private JComboBox  combo${property.name} = new JComboBox();
	   		private  JLabel     lbl${property.name} = new JLabel("${property.lblName}<#if property.required == true >*</#if>");
	    </#if>    
	    
	   <#if property.upper == 1 && property.isBoolean == true>   
	  		private JCheckBox check${property.name} = new JCheckBox();
	   		private  JLabel     lbl${property.name} = new JLabel("${property.lblName}<#if property.required == true >*</#if>");
	    </#if> 
	    
	    <#if property.upper == 1  && property.isEnumeration == true >   
	  		private JComboBox  combo${property.name} = new JComboBox(${property.type}.values());
	   		private  JLabel     lbl${property.name} = new JLabel("${property.lblName}<#if property.required == true >*</#if>");
	    </#if>
	    
	    <#if property.upper == 1  && property.isTextArea == true >   
	  		private JTextArea  textArea${property.name} = new JTextArea();
	   		private  JLabel     lbl${property.name} = new JLabel("${property.lblName}<#if property.required == true >*</#if>");
	    </#if>
	</#list>
	
	private JButton btnCancel = new JButton();
	
	private JButton btnCommit = new JButton();
	
	public ${class.name}Panel(){
		
		btnCancel.setIcon(new ImageIcon("images/remove.gif"));
		btnCommit.setIcon(new ImageIcon("images/commit.gif"));

		setLayout(new MigLayout("", "[align l][align l, grow]", ""));
		add(new JLabel(""),"wrap 20");
		<#list properties as property>
			<#if property.upper == 1 && property.foreignKey == false && property.isBoolean == false && property.isEnumeration == false  && property.isTextArea == false>   
					add(lbl${property.name},"gapleft 30");
					<#if property.isTextField == true >
						lbl${property.name}.setName("${property.name}");
						tf${property.name}.addKeyListener(new TextFieldWidth(${property.textWidth}));
						tf${property.name}.setSize(new Dimension(${property.textWidth}, tf${property.name}.getHeight()));
						tf${property.name}.setName("${property.name}");
						add(tf${property.name},"grow 0,wrap 20");
						<#if property.isInteger == true >
							TextField text${property.name} = new TextField(1);
							text${property.name}.setName("${property.name}");
							text${property.name}.setText("I");
							text${property.name}.setVisible(false);
							add(text${property.name},"grow 0,hidemode 3");
						<#elseif property.isReal == true>
							TextField text${property.name} = new TextField(1);
							text${property.name}.setName("${property.name}");
							text${property.name}.setText("R");
							text${property.name}.setVisible(false);
							add(text${property.name},"grow 0,hidemode 3");
						</#if>
					<#elseif property.isDate == true>
						lbl${property.name}.setName("${property.name}");
						UtilDateModel model${property.name} = new UtilDateModel();
						JDatePanelImpl datePanel${property.name} = new JDatePanelImpl(model${property.name});
						JDatePickerImpl datePicker${property.name} = new JDatePickerImpl(datePanel${property.name});
						datePicker${property.name}.setName("${property.name}");
						add(datePicker${property.name},"grow 0,wrap 20");
					</#if>
		    </#if> 
		    <#if property.upper == 1 && property.foreignKey == true >   
				add(lbl${property.name},"gapleft 30");
				lbl${property.name}.setName("${property.name}");
				combo${property.name}.setName("${property.name}");
				final ${property.name?cap_first}HibernateDao dao${property.name} = new ${property.name?cap_first}HibernateDao();
				for(${property.name?cap_first} c : dao${property.name}.findAll()){
					combo${property.name}.addItem(c);
				}
				add(combo${property.name},"gapleft 30");
				JButton btnZoom${property.name} = new JButton("...");
				btnZoom${property.name}.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
				${property.name?cap_first} ${property.name}	= new ${property.name?cap_first}();
						GenericStandardForm form = new GenericStandardForm((IEntity)${property.name}, dao${property.name}, new ${property.name?cap_first}Panel());
						form.setCmbForZoom(combo${property.name});
						form.setModal(true);
						form.setLocationRelativeTo(null);
						form.setVisible(true);
					}
				});
				
				
				add( btnZoom${property.name},"grow 0,wrap 20");
		    </#if> 
		    
		    <#if property.upper == 1 && property.isBoolean == true >   
				add(lbl${property.name},"gapleft 30");
				lbl${property.name}.setName("${property.name}");
				check${property.name}.setName("${property.name}");
				add(check${property.name},"grow 0,wrap 20");
		    </#if> 
		    
	      	<#if property.upper == 1 && property.isEnumeration == true >   
				add(lbl${property.name},"gapleft 30");
				lbl${property.name}.setName("${property.name}");
				combo${property.name}.setName("${property.name}");
				add(combo${property.name},"grow 0,wrap 20");
		    </#if> 
		    
		    <#if property.upper == 1 && property.isTextArea == true >   
				add(lbl${property.name},"gapleft 30");
				lbl${property.name}.setName("${property.name}");
				textArea${property.name}.addKeyListener(new TextFieldWidth(${property.textWidth}));
				textArea${property.name}.setLineWrap(true);
				textArea${property.name}.setWrapStyleWord(true);
				textArea${property.name}.setName("${property.name}");
				JScrollPane areaScrollPane${property.name} = new JScrollPane(textArea${property.name});
				areaScrollPane${property.name}.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				areaScrollPane${property.name}.setPreferredSize(new Dimension(400, 100));
				add(areaScrollPane${property.name},"grow 0,wrap 20");
		    </#if> 
		</#list>
		
	}
	 public List<String> getTableColumns(){
		 List<String> retVal = new ArrayList<String>();
		 <#list properties as property>
		 <#if property.upper == 1 >
		 retVal.add("${property.name}");
		 </#if>
		 </#list>
		 return retVal;
	 }
}
