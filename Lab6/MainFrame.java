
 
import java.awt.BorderLayout; 
import java.awt.Toolkit; 
import java.awt.event.ActionEvent; 
import javax.swing.AbstractAction; 
import javax.swing.Action; 
import javax.swing.JFrame; 
import javax.swing.JMenu; 
import javax.swing.JMenuBar; 
import javax.swing.JMenuItem; 
 
@SuppressWarnings("serial") 
public class MainFrame extends JFrame { 
	

	private static final int WIDTH = 700; 
	private static final int HEIGHT = 500; 
	
	private JMenuItem pauseMenuItem; 
	private JMenuItem pause2MenuItem; 
	private JMenuItem pause1MenuItem; 
	private JMenuItem resumeMenuItem; 
	

	private Field field = new Field(); 
	

	public MainFrame() { 
		super("Программирование и синхронизация потоков"); 
		setSize(WIDTH, HEIGHT); 
		Toolkit kit = Toolkit.getDefaultToolkit();     
 
		setLocation((kit.getScreenSize().width - WIDTH)/2, (kit.getScreenSize().height - HEIGHT)/2); 

		

		JMenuBar menuBar = new JMenuBar(); 
		setJMenuBar(menuBar); 
		JMenu ballMenu = new JMenu("Мячи"); 
		Action addBallAction = new AbstractAction("Добавить мяч") { 
			public void actionPerformed(ActionEvent event) { 
				field.addBall(); 
				if (!pauseMenuItem.isEnabled() && !resumeMenuItem.isEnabled()) {  
					pauseMenuItem.setEnabled(true);
					pause2MenuItem.setEnabled(true); 
					pause2MenuItem.setEnabled(true); 
					pause1MenuItem.setEnabled(false);
					} 
				} 
			};
		
		menuBar.add(ballMenu); 
		ballMenu.add(addBallAction);  
		
		JMenu controlMenu = new JMenu("Управление"); 
		menuBar.add(controlMenu); 
		Action pause2Action = new AbstractAction("Приостановить движение мячей малого радиуса"){ 
			public void actionPerformed(ActionEvent event) { 
				field.pause(); 
				pauseMenuItem.setEnabled(true);
				pause2MenuItem.setEnabled(false);
				resumeMenuItem.setEnabled(true);  
				pause1MenuItem.setEnabled(true);
				}       	
			}; 
			
		pause2MenuItem = controlMenu.add(pause2Action); 
		pause2MenuItem.setEnabled(false); 

		Action pauseAction = new AbstractAction("Приостановить движение мячей"){ 
			public void actionPerformed(ActionEvent event) { 
				field.pause1(); 
				pauseMenuItem.setEnabled(false); 
				pause2MenuItem.setEnabled(false); 
				resumeMenuItem.setEnabled(true); 
				pause1MenuItem.setEnabled(true);
				}       	
			};
			
		pauseMenuItem = controlMenu.add(pauseAction); 
		pauseMenuItem.setEnabled(false); 
		
		Action resumeAction = new AbstractAction("Возобновить движение мячей") { 
			public void actionPerformed(ActionEvent event) { 
				field.resume(); 
				pauseMenuItem.setEnabled(true); 
				pause2MenuItem.setEnabled(true); 
				resumeMenuItem.setEnabled(false);  
				pause1MenuItem.setEnabled(false);
				}             
			}; 
			
			resumeMenuItem = controlMenu.add(resumeAction); 
			resumeMenuItem.setEnabled(false);    
			
			Action pause1Action = new AbstractAction("Возобновить движение мячей малого радиуса"){ 
				public void actionPerformed(ActionEvent event) { 
					field.resumeLol(); 
					pauseMenuItem.setEnabled(true);
					pause2MenuItem.setEnabled(true);
					pause1MenuItem.setEnabled(false);
					resumeMenuItem.setEnabled(true);  
					}       	
				}; 
				
			pause1MenuItem = controlMenu.add(pause1Action); 
			pause1MenuItem.setEnabled(false); 
			

			getContentPane().add(field, BorderLayout.CENTER);     
		} 
	
	public static void main(String[] args) { 
		MainFrame frame = new MainFrame(); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setVisible(true); 
		} 
	} 