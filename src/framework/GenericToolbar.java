package framework;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

public class GenericToolbar extends JToolBar {
	
	private JButton btnSearch;
	private JButton btnRefresh;
	private JButton btnPickup;
	private JButton btnHelp;
	
	private JButton btnFirstRow;
	private JButton btnPreviousRow;
	private JButton btnNextRow;
	private JButton btnLastRow;
	
	private JButton btnAdd;
	private JButton btnDelete;
	private JButton btnNextForm;
	

	public GenericToolbar(final GenericStandardForm form) {
		
		btnSearch = new JButton(new ImageIcon("images/search.gif"));
		btnSearch.setToolTipText("Pretraži");
		btnSearch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				form.search();
			}
		});
		
		add(btnSearch);
		
		btnRefresh = new JButton(new ImageIcon("images/refresh.gif"));
		btnRefresh.setToolTipText("Osveži");
		btnRefresh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				form.refresh();
			}
		});
		add(btnRefresh);
		
		btnPickup = new JButton(new ImageIcon("images/zoom-pickup.gif"));
		btnPickup.setToolTipText("Prethodna forma");
		btnPickup.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				form.previousForm();
			}
		});
		
		add(btnPickup);
		
		btnHelp = new JButton(new ImageIcon("images/help.gif"));
		btnHelp.setToolTipText("Pomoć");
		btnHelp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				form.help();
			}
		});
		add(btnHelp);
		add(new Separator());
		
		btnFirstRow = new JButton(new ImageIcon("images/first.gif"));
		btnFirstRow.setToolTipText("Prvi red");
		btnFirstRow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				form.firstRow();
			}
		});
		add(btnFirstRow);
		
		btnPreviousRow = new JButton(new ImageIcon("images/prev.gif"));
		btnPreviousRow.setToolTipText("Prethodni red");
		btnPreviousRow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				form.previousRow();
			}
		});
		add(btnPreviousRow);
		
		btnNextRow = new JButton(new ImageIcon("images/next.gif"));
		btnNextRow.setToolTipText("Sledeći red");
		btnNextRow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				form.nextRow();
			}
		});
		add(btnNextRow);
		
		btnLastRow = new JButton(new ImageIcon("images/last.gif"));
		btnLastRow.setToolTipText("Poslednji red");
		btnLastRow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				form.lastRow();
			}
		});
		add(btnLastRow);
		add(new Separator());
		
		
		btnAdd = new JButton(new ImageIcon("images/add.gif"));
		btnAdd.setToolTipText("Dodaj");
		btnAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				form.add();
			}
		});
		add(btnAdd);
		
		btnDelete = new JButton(new ImageIcon("images/remove.gif"));
		btnDelete.setToolTipText("Obriši");
		btnDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				form.delete();
			}
		});
		add(btnDelete);
		add(new Separator());
		
		
		btnNextForm = new JButton(new ImageIcon("images/nextform.gif"));
		btnNextForm.setToolTipText("Sledeća forma");
		btnNextForm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				form.next(btnNextForm);
			}
		});
		add(btnNextForm);
		
	}

	public JButton getBtnSearch() {
		return btnSearch;
	}

	public JButton getBtnRefresh() {
		return btnRefresh;
	}

	public JButton getBtnPickup() {
		return btnPickup;
	}

	public JButton getBtnHelp() {
		return btnHelp;
	}

	public JButton getBtnFirstRow() {
		return btnFirstRow;
	}

	public JButton getBtnPreviousRow() {
		return btnPreviousRow;
	}

	public JButton getBtnNextRow() {
		return btnNextRow;
	}

	public JButton getBtnLastRow() {
		return btnLastRow;
	}

	public JButton getBtnAdd() {
		return btnAdd;
	}

	public JButton getBtnDelete() {
		return btnDelete;
	}

	public JButton getBtnNextForm() {
		return btnNextForm;
	}
	
		
}
