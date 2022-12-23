package agenda;

import javax.swing.JOptionPane;

/* @author radoi ovidiu*/
public class NrMobil extends NrTel {

    private String nrMobil;

    public NrMobil(String nr) {
        super(nr);
        nrMobil = nr;
    }

    @Override
    public void validareNumar() {
        try {
            if (!nrMobil.startsWith("07")) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Numarul introdus nu este unul de telefon mobil ");
        }
    }

}
