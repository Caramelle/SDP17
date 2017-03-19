package vision.colorAnalysis;

import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JPanel;
/**
 * Created by Simon Rovder
 */
public class ColorCalibration extends JPanel implements ActionListener{

	private List list;
	
	
	public static final ColorCalibration colorCalibration = new ColorCalibration();
	
	private ColorCalibration() {
		super();
		this.setLayout(null);
		list = new List();
		list.setBounds(10, 10, 273, 350);
		this.add(list);
		
		JButton btnCalibrate = new JButton("Calibrate");
		btnCalibrate.setBounds(289, 10, 222, 33);
		btnCalibrate.addActionListener(this);
		this.add(btnCalibrate);
		ArrayList<String> names = new ArrayList<>();
		for(SDPColorInstance c : SDPColors.colors.values()){
		    names.add(c.name);
		}
	    Collections.sort(names);
        for(String name : names)
            this.list.add(name);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String selected = this.list.getSelectedItem();
		if(selected != null){
			SDPColors.colors.get(SDPColor.valueOf(selected)).setVisible(true);
			SDPColors.colors.get(SDPColor.valueOf(selected)).transferFocus();
		}
	}	
}
