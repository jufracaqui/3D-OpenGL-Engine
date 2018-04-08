package aa_pruebaMotor;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JScrollBar;
import javax.swing.JSlider;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.lwjgl.opengl.GL;
import org.lwjglx.util.vector.Vector3f;

import motorRenderizado.ManagerVentana;

import javax.swing.event.ChangeEvent;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

public class ControlesCamara extends JDialog{
	
	JCheckBox cbEjeX, cbEjeY ,cbEjeZ;
	boolean isCbEjeX = false;
	boolean isCbEjeY = false;
	boolean isCbEjeZ = false;
	boolean isTransparencia = false;
	boolean isLuzFalsa = false;

	private final JPanel contentPanel = new JPanel();
	private JLabel lblNewLabel, lColorLuz;
	
	Color color = Color.WHITE;
	
	float reflectividad = 0.5f;
	float escala = 0.5f;
	
	String modelo3D, texturaModelo;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			ControlesCamara dialog = new ControlesCamara();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public ControlesCamara() {
		setBounds(100, 100, 231, 556);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		cbEjeX = new JCheckBox("Eje X");
		cbEjeX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!isCbEjeX)
					isCbEjeX = true;
				else isCbEjeX = false;
			}
		});
		cbEjeX.setBounds(29, 43, 97, 23);
		contentPanel.add(cbEjeX);
		
		cbEjeY = new JCheckBox("Eje Y");
		cbEjeY.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!isCbEjeY)
					isCbEjeY = true;
				else isCbEjeY = false;
			}
		});
		cbEjeY.setBounds(29, 69, 97, 23);
		contentPanel.add(cbEjeY);
		
		cbEjeZ = new JCheckBox("Eje Z");
		cbEjeZ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!isCbEjeZ)
					isCbEjeZ = true;
				else isCbEjeZ = false;
			}
		});
		cbEjeZ.setBounds(29, 95, 97, 23);
		contentPanel.add(cbEjeZ);
		
		JLabel lblRotacionDelModelo = new JLabel("Rotacion del modelo:");
		lblRotacionDelModelo.setBounds(10, 22, 165, 14);
		contentPanel.add(lblRotacionDelModelo);
		
		JButton btnColorDeLa = new JButton("Color de la luz");
		btnColorDeLa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JColorChooser Selectorcolor=new JColorChooser();
                color=JColorChooser.showDialog(null, "Seleccione un Color", Color.WHITE);
                lColorLuz.setOpaque(true);
                lColorLuz.setBackground(color);
			}
		});
		btnColorDeLa.setBounds(10, 148, 165, 23);
		contentPanel.add(btnColorDeLa);
		
		lColorLuz = new JLabel("");
		lColorLuz.setBackground(Color.WHITE);
		lColorLuz.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		lColorLuz.setBounds(10, 182, 165, 14);
		contentPanel.add(lColorLuz);
		
		JButton btnCerrarVisor = new JButton("Cerrar Visor");
		btnCerrarVisor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ManagerVentana.cerrarVentana();
				ManagerVentana.cerrarVentana();
			}
		});
		btnCerrarVisor.setBounds(53, 483, 157, 23);
		contentPanel.add(btnCerrarVisor);
		
		JSlider sReflectividad = new JSlider();
		sReflectividad.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				reflectividad = (float)sReflectividad.getValue() / 100;
			}
		});
		sReflectividad.setBounds(10, 271, 200, 26);
		contentPanel.add(sReflectividad);
		
		JLabel lblReflectividad = new JLabel("Reflectividad");
		lblReflectividad.setBounds(10, 246, 116, 14);
		contentPanel.add(lblReflectividad);
		
		JLabel lblEscala = new JLabel("Escala");
		lblEscala.setBounds(10, 308, 46, 14);
		contentPanel.add(lblEscala);
		
		JSlider sEscala = new JSlider();
		sEscala.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				escala = (float)sEscala.getValue() / 100;
			}
		});
		sEscala.setBounds(10, 333, 200, 26);
		contentPanel.add(sEscala);
	
		
		
	}

	public boolean isCbEjeX() {
		return isCbEjeX;
	}

	public boolean isCbEjeY() {
		return isCbEjeY;
	}

	public boolean isCbEjeZ() {
		return isCbEjeZ;
	}

	public Color getColor() {
		return color;
	}

	public float getReflectividad() {
		return reflectividad;
	}

	public float getEscala() {
		return escala;
	}

	public boolean isTransparencia() {
		return isTransparencia;
	}

	public boolean isLuzFalsa() {
		return isLuzFalsa;
	}
}