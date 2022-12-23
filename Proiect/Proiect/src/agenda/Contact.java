package agenda;

import agenda.exceptions.FormatGresitException;
import agenda.exceptions.NumeInvalidException;
import javax.swing.JOptionPane;

/* @author radoi ovidiu*/
public class Contact {

    private String nume, prenume, data_nasterii;
    private NrTel nrtel;

    public Contact(String nume, String prenume, String data_nasterii, NrTel nrtel) {

        this.nume = nume.trim();
        this.prenume = prenume.trim();
        this.data_nasterii = data_nasterii.trim();
        this.nrtel = nrtel;

        NumeValid(nume);
        PrenumeValid(prenume);
        DataValida(data_nasterii);

    }

    private void DataValida(String data) {
        // in format AAAA-LL-ZZ

        String[] elem = data.split("-");
        if (elem.length != 3) {
            throw new FormatGresitException(data);
        }
        int an = Integer.parseInt(elem[0]);
        int luna = Integer.parseInt(elem[1]);
        int zi = Integer.parseInt(elem[2]);

        if (zi <= 0 || zi > StringUtils.zilePerLuna(an, luna)) {
            JOptionPane.showMessageDialog(null, "Data de nastere introdusa este gresita");
        }
        if (luna < 1 || luna > 12) {
            JOptionPane.showMessageDialog(null, "Data de nastere introdusa este gresita");
        }
        if (an <= 0) {
            JOptionPane.showMessageDialog(null, "Data de nastere introdusa este gresita");
        }
    }

    private void NumeValid(String num) {
        try {
            if (num.length() <= 2) {
                throw new NumeInvalidException(num);
            }
            if (StringUtils.onlyLetters(num) == false) {
                throw new NumberFormatException();
            }

        } catch (NumeInvalidException e) {
            JOptionPane.showMessageDialog(null, "Numele introdus nu este corect");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Numele introdus contine cifre");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }

    private void PrenumeValid(String num) {

        if (prenume.length() <= 2) {
            JOptionPane.showMessageDialog(null, "Prenumele nu este valid ");
        }
        if (StringUtils.onlyLetters(num) == false) {
            JOptionPane.showMessageDialog(null, "Prenumele nu contine doar litere");
        }

    }

    public boolean equals(Contact c) {
        if (c.nume.toLowerCase().trim().equals(nume.toLowerCase().trim()) && c.prenume.toLowerCase().trim().equals(prenume.toLowerCase().trim())
                && c.data_nasterii.trim().equals(data_nasterii.trim()) && c.nrtel.equals(nrtel)) {

            return true;
        }
        return false;
    }

    public String toString() {
        return nume + " " + prenume + ", " + nrtel + ", " + data_nasterii;
    }

    public NrTel getNrtel() {
        return nrtel;
    }

    public String getData_nasterii() {
        return data_nasterii;
    }

    public int getAnNastere() {
        String[] elem = data_nasterii.split("-");
        return Integer.parseInt(elem[0]);
    }

    public int getZiNastere() {
        String[] elem = data_nasterii.split("-");
        return Integer.parseInt(elem[2]);
    }

    public int getLunaNastere() {
        String[] elem = data_nasterii.split("-");
        return Integer.parseInt(elem[1]);
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public void setData_nasterii(String data_nasterii) {
        this.data_nasterii = data_nasterii;
    }

    public void setNrtel(NrTel nrtel) {
        this.nrtel = nrtel;
    }

}
