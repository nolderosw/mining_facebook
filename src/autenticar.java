import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by wesley150 on 07/08/17.
 */
public class autenticar {
    public JPanel panel_autenticar;
    private JButton authButton;

    public autenticar() {
        authButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(null,"Cliquei");
            }
        });
    }
}
