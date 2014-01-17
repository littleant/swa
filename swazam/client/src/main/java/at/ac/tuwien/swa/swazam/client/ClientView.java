package at.ac.tuwien.swa.swazam.client;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;

public class ClientView extends JFrame
{

	private ClientViewModel viewmodel;
	
	//Components
	private JPanel panel;
	
	private JLabel title;
	private JLabel resultLabel;


	private JButton sendButton;

	
	
	private JComboBox musicBox;
		
	private DefaultComboBoxModel cmodel;







	private ActionListener sendPressedListener;

	
//	private JFileChooser chooser;
	
	public ClientView(ClientViewModel viewmodel)
	{
		this.viewmodel = viewmodel;
		initListeners();
		initComponents();

		
	}
	
	
	/*
	 * initComponents initializes all Objects of the needed Panels 
	 */
	private void initListeners()
	{
		sendPressedListener = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("sendButton pressed !");
				viewmodel.startMusicRecognition(musicBox.getSelectedItem().toString());
			}
					
		};
		
		
	}
		
	
		private void initComponents()
		{	
//			tabChanged = new ChangeListener(){
//
//				@Override
//				public void stateChanged(ChangeEvent e) {
//					try {
//						if (tabControl.getSelectedComponent().equals(warepanel))
//							warepanel.updateData();
//						if(tabControl.getSelectedComponent().equals(rechPanel))
//							rechPanel.resetComponents();
//						if(tabControl.getSelectedComponent().equals(rechAPanel))
//							rechAPanel.resetComponents();
//					} catch (Exception e1) {
//							// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}// TODO Auto-generated method stub	
//					
//					// TODO Auto-generated method stub
//					
//				}
//				
//			};
			
			//init WareTab
			
			//init RechnungTab
//			try {
//			this.rechPanel = new RechnungPanel(service);
//			this.rechAPanel = new RechnungAnzPanel(service);
//			this.warepanel = new WarePanel(service);
			this.setSize(400, 200);
			this.setMinimumSize(new Dimension(400,300));
//			this.tabControl = new JTabbedPane();
//			this.tabControl.addTab("Warenverwaltung", warepanel);
//			this.tabControl.addTab("Rechnungsverwaltung", rechPanel);
//			this.tabControl.addTab("Rechungsanzeige", rechAPanel);
			
			
			
			panel = new JPanel();
			
			panel.setLayout(null);
			
			title = new JLabel("Choose Music:");
			resultLabel = new JLabel("Unknown Song");
			
			
			title.setBounds(30, 20, 120, 20);
			
			resultLabel.setBounds(30, 130, 300, 20);
			
			cmodel = new DefaultComboBoxModel();
			musicBox = new JComboBox(cmodel);

			musicBox.setBounds(30, 70, 300, 20);
			
			
			sendButton = new JButton("Recognize");
			sendButton.addActionListener(sendPressedListener);
			
			sendButton.setBounds(20, 100, 120, 20);
			
			
			panel.setBounds(300, 200, 300, 200);
			panel.add(title);
			panel.add(musicBox);
			panel.add(sendButton);
			panel.add(resultLabel);
			
		//	this.setBounds(200, 200, 100, 100);
			
			this.add(panel);
			//this.add(sendButton);
			
//			tabControl.addChangeListener(tabChanged);
//			
//			this.add(tabControl);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				String message = "Datenbank nicht vorhanden!";
//				JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",
//				        JOptionPane.ERROR_MESSAGE);
//			}
			
		}
		public JLabel getResultLabel() {
			return resultLabel;
		}


		public void setResultLabel(JLabel resultLabel) {
			this.resultLabel = resultLabel;
		}
		
		public JComboBox getMusicBox() {
			return musicBox;
		}


		public void setMusicBox(JComboBox musicBox) {
			this.musicBox = musicBox;
		}
		
		public DefaultComboBoxModel getCmodel() {
			return cmodel;
		}


		public void setCmodel(DefaultComboBoxModel cmodel) {
			this.cmodel = cmodel;
		}
		

		
}