package agenda;

import javax.swing.JOptionPane;

/* @author radoi ovidiu*/
public abstract class NrTel implements Comparable {

    private String nrTel;

    public NrTel(String nr) {
        nrTel = nr;
        try {
            if (nr.length() != 10) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Numarul de telefon introdus nu are 10 cifre.");
        }
    }

    @Override
    public int compareTo(Object o) {
        return 2;
    }

    public abstract void validareNumar();

    public boolean equals(NrTel nr) {
        if (nr.toString().equals(nrTel)) {
            return true;
        }
        return false;

    }

    @Override
    public String toString() {

        return nrTel;
    }

    public boolean isNrFix() {
        if (nrTel.startsWith("02") || nrTel.startsWith("03")) {
            return true;
        }
        return false;
    }

    public boolean isNrMobil() {
        if (nrTel.startsWith("07")) {
            return true;
        }
        return false;
    }
}
