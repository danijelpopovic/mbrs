package framework;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class GenericTableModel<T extends AbstractEntity> extends
		DefaultTableModel {

	private GenericDao<T> dao;

	public GenericTableModel(GenericDao<T> dao, String[] columnLabels) {
		super(columnLabels, 0);
		this.dao = dao;

	}

	public void fillData() {
		try {
			@SuppressWarnings("unchecked")
			List<AbstractEntity> allElements = (List<AbstractEntity>) dao
					.findAll();
			for (AbstractEntity e : allElements) {
				addRow(e.getValues());
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Greška",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

	}

	public void insertRow(T entity) {
		try {
			dao.save(entity);
			addRow(entity.getValues());
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Greška",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

	}

	public void deleteRow(int row) {
		try {
			Integer id = (Integer) getValueAt(row, 0);
			dao.deleteById(id);
			removeRow(row);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Greška",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

	public void updateRow(T entity, int row) {
		try {
			dao.merge(entity);
			Object[] values = entity.getValues();
			for (int i = 0; i < values.length; i++) {
				setValueAt(values[i], row, i);
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Greška",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

	public GenericDao<T> getDao() {
		return dao;
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
