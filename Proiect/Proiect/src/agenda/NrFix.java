package agenda;

import javax.swing.JOptionPane;

/* @author radoi ovidiu*/
public class NrFix extends NrTel {

    private String nrFix;

    public NrFix(String nr) {
        super(nr);
        nrFix = nr;
        validareNumar();

    }

    @Override
    public void validareNumar() {
        try {
            if (nrFix.startsWith("02") || nrFix.startsWith("03")) {
            } else {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Numarul introdus nu este unul de telefon fix ");
        }
    }

}
