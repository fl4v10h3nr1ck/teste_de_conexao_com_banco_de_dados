import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;






public class Form extends JFrame{

	
	

private static final long serialVersionUID = 1L;




public JComboBox combo_BD;
public JPanel fundo;
public JTextField   campo_url;
public JTextField   campo_usuario;
public JPasswordField   campo_senha;
public JButton   bt_conetar;
public JPanel  log;
public JScrollPane scrollpane;



public int largura, altura;


Font font = new Font ("TimesRoman", Font.BOLD, 14);  




public String BDs[] = {"MYSQL","POSTGRESQL"};




/****
 * 
 * 
 * 
 * [0]  = driver do mysql  
 * [1]  = driver do postgresql
 * 
 * 
 ***/

public String drivers[] = {"com.mysql.jdbc.Driver", "org.postgresql.Driver"}; 









boolean status = false;
List<String> string_log;




	public Form(){
		
		this.largura = 438;
		this.altura = 480;
		
		
	this.setSize( this.largura, this.altura);	
	this.setResizable(true);
	this.setLocationRelativeTo(null);
	this.setLayout(null);
	this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	initComponentes();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void initComponentes(){
		
	
	
	fundo = new JPanel(){
	private static final long serialVersionUID = 1L;
	public void paintComponent( Graphics g){	
		    	
		 	    
	super.paintComponent(g);		
	Graphics2D g2 = (Graphics2D) g.create();	
	g2.setPaint( new Color(255, 250, 205));
	g2.fillRect(  0 , 0 ,  largura ,  altura  );

	}};	
	fundo.setSize(this.largura, this.altura);
	fundo.setLocation(0 , 0);	
	fundo.setLayout(null);
	add(fundo);
		
	 combo_BD = new JComboBox(BDs);	
	 combo_BD.setSize( 250, 20);
	 combo_BD.setLocation( 150, 20);	
	 fundo.add(combo_BD);
		
	campo_url = new JTextField("localhost");
	campo_url.setSize( 250, 20);
	campo_url.setLocation( 150,
			this.combo_BD.getY() +this.combo_BD.getHeight()+50);	
	fundo.add(campo_url);
	
	
	campo_usuario = new JTextField();
	campo_usuario.setSize( 250, 20);
	campo_usuario.setLocation(  150,
			this.campo_url.getY() +this.campo_url.getHeight()+15);	
	fundo.add(campo_usuario);
	
	
	campo_senha = new JPasswordField();
	campo_senha.setSize( 250, 20);
	campo_senha.setLocation(  150,
			this.campo_usuario.getY() +this.campo_usuario.getHeight()+15);	
	fundo.add(campo_senha);

	 
	
	
	JLabel  lb1 = new JLabel("Banco de dados:");
	lb1.setSize( 100, 15);
	lb1.setLocation(  10, combo_BD.getY());
	fundo.add(lb1);
	
	 
	JLabel  lb2 = new JLabel("IP:");
	lb2.setSize( 100, 15);
	lb2.setLocation(  10, campo_url.getY());
	fundo.add(lb2);
	
	JLabel  lb3 = new JLabel("Usuário:");
	lb3.setSize( 100, 15);
	lb3.setLocation(  10, campo_usuario.getY());
	fundo.add(lb3);
	
	JLabel  lb4 = new JLabel("Senha:");
	lb4.setSize( 100, 15);
	lb4.setLocation(  10, campo_senha.getY());
	fundo.add(lb4);
		
		
	
	bt_conetar = new JButton("Conectar");   
	bt_conetar.setSize( 100, 20);
	bt_conetar.setLocation(  (largura - bt_conetar.getWidth())/2, this.campo_senha.getY() +this.campo_senha.getHeight()+15);
	fundo.add(bt_conetar);
	
	
	
	log = new JPanel(){
	private static final long serialVersionUID = 1L;
	public void paintComponent( Graphics g){	
	    	
	 	    
	super.paintComponent(g);	
	Graphics2D g2 = (Graphics2D) g.create();	
	g2.setPaint( Color.white);
	g2.fillRect(  0 , 0 ,  this.getWidth() ,  this.getHeight()  );
	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	g2.setPaint( Color.black);
	g2.setFont(font);
	g2.drawString( "LOG DE CONEXÃO:",  20, 20);
	
	if(status)
	g2.setPaint( Color.green);
	else
	g2.setPaint( Color.red);
	
	
	if( string_log == null )
	return;
	
	for( int i = 0; i < string_log.size(); i++)
	g2.drawString( string_log.get(i),  20, 50+ ( i* 20));
	
	
	
	}};
	log.setPreferredSize(new Dimension( this.campo_senha.getX()+ this.campo_senha.getWidth(), 200) );
	scrollpane = new JScrollPane( log , ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
    		ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	scrollpane.setSize( this.campo_senha.getX()+ this.campo_senha.getWidth(), 200);
	scrollpane.setLocation( 10 , bt_conetar.getY() + bt_conetar.getHeight() + 20 );
	fundo.add( scrollpane);


	
	
	
	
	
	bt_conetar.addActionListener( new ActionListener(){
    public void actionPerformed( ActionEvent event ){
    
    if( campo_url.getText().length() < 1 || campo_usuario.getText().length() < 1 ||
		campo_senha.getPassword().length < 1)	
    JOptionPane.showMessageDialog(  null, "Há campos inválidos!", "ERRO", JOptionPane.ERROR_MESSAGE);
    	
    	
    	if(string_log != null ){
    	string_log.clear();
    	string_log = null;
    	}
   
    	
    conecta();
   log.repaint();
   }});
	

	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void mostrar(){
	this.setVisible(true);
	}
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void conecta(){	
		try{	
		
		
		Connection conector = null;
		String driver = null;
		String url = null;	


			if(String.valueOf( combo_BD.getSelectedItem()).compareTo( BDs[0] ) == 0 ){
			driver	 = drivers[ 0 ];
			url = "jdbc:mysql://"+ this.campo_url.getText()+"/?user="+	this.campo_usuario.getText()+"&password="
			+String.valueOf( this.campo_senha.getPassword());
			}
			else{
			driver	 = drivers[ 1 ];
			url = "jdbc:postgresql://"+ this.campo_url.getText()+":5432/?user="+this.campo_usuario.getText()+"&password="
			+String.valueOf( this.campo_senha.getPassword());
			}
		
			
		string_log = new ArrayList<String>();	
			
		Class.forName( driver );
		conector = DriverManager.getConnection( url );
		
	
	    DatabaseMetaData metaData = conector.getMetaData();  
	    ResultSet listaDeBD = metaData.getCatalogs();  
	   
	    
	    
	 
	    
	    
	    string_log.add(" BANCO DE DADOS CONECTADO!");
	    string_log.add("--- "+ String.valueOf( combo_BD.getSelectedItem()) +"------");
	    while (listaDeBD.next())
	    	string_log.add(listaDeBD.getString(1));	
	    
	    
	    this.status = true;    
	    conector.close();
		}
		catch(ClassNotFoundException driveNaoEncontrado ){
		
		string_log.add( "NÃO CONECTADO, DRIVER NÃO ENCONTRADO!");
		this.status = false;
		return;
		}
		
	
		catch(SQLException erroSQL){ 
		
		string_log.add(  "NÃO CONECTADO, CONEXÃO NEGADA!");
		 this.status = false;
		return;
		}

	}
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main( String args[]){
		
		
	Form form = new Form();
	form.mostrar();	
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
