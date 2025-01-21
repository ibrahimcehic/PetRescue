package Korisnik;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author home
 */
@Getter   
@Setter
public class korisnikKontroler extends korisni.Kontroler{
    private korisnik Korisnik = new korisnik();

    public korisnikKontroler() {    }
    /**
     * Metoda koja vrši izmjenu korisnicke sifre
     * @param temp objekat korisnika koji mjenja sifru
     * @param New nova sifra
     * @param Rep ponovni unos nove sifre
     * @throws SQLException ukoliko nije bilo uspjesno povezivanje sa bazom podataka
     */
    public void promjenaPassworda(korisnik temp, String New, String Rep) throws SQLException {
        String sql = "UPDATE korisnik SET pass =' "+New+"' WHERE id =" + temp.getId();
        InsDelUpd(sql);        
    }

    /**
     * Metoda <code>azurirajKorisnika</code> vrši azuiriranje podataka korisnka
     * i to ime i prezime korisnika
     * @param temp objekat korisnika nad kojim se vrsi azuriranje
     * @throws SQLException ukoliko nije bilo uspjesno povezivanje sa bazom podataka
     */
    public void azurirajKorisnika(korisnik temp) throws SQLException {
        String sql = "UPDATE korisnik SET imeiprezime = '"+ temp.getImeiprezime()+
                "', tip = '"+temp.getTip()+"' WHERE id = " + temp.getId();
        InsDelUpd(sql);
    }

    /**
     *Metoda unesiKorisnika unosi sve podatke o novom korisniku sistema
     * @param temp predstavlja objekat korisnik koji se unosi u bazu
     * @throws SQLException ukoliko nije bilo uspjesno povezivanje sa bazom podataka
     */
    public void UnesiKorisnika(korisnik temp) throws SQLException {
        String sql = "INSERT INTO korisnik (imeiprezime,user,pass,tip) VALUES ('" + temp.getImeiprezime()+"','"
                +temp.getUser()+"','"+temp.getPass()+"','"+temp.getTip()+"')";
        InsDelUpd(sql);
    }

    /**
     * Metoda vraca objekat korisnika koji ima trazeni ID
     * @param ID primarni kljuc objekta korisnik
     * @return objekat korisnik koji ima taj ID, ukoliko ga ne nadje vraca null
     * @throws SQLException ukoliko nije bilo uspjesno povezivanje sa bazom podataka
     */
    public korisnik VratiKorisnikaPoID(int ID) throws SQLException {
        String sql = "SELECT * FROM korisnik WHERE id='" + Integer.toString(ID) + "'";
        korisnik kor = new korisnik();
        Statement st = getKon().createStatement();
        ResultSet rs1 = st.executeQuery(sql);
        while (rs1.next()) 
        {
            kor.setId(rs1.getInt("id"));
            kor.setImeiprezime(rs1.getString("imeiprezime"));
            kor.setUser(rs1.getString("user"));
            kor.setPass(rs1.getString("pass"));
            kor.setTip(rs1.getString("tip"));
        }
        zatvoriKonekciju();
        return kor;
    } 
    
     /**
     * Vracalistu korisnika koji zadovoljavaju uvijet pretrazivanja
     * @param uvjet jeste string prema kojem se vrsi pretrazivanje
     * @param opcija ukoliko je 0 pretrazivanje je po imenu, a ukoliko je 1 pretrazivanje je po prezimenu
     * @return lista korisnika koji zadovoljavaju uvijet pretrage
     * @throws SQLException ukoliko nije bilo uspjesno povezivanje sa bazom podataka
     */
    public ArrayList<korisnik> vratiKojiZadovoljavajuUvjet(String uvjet, int opcija) throws SQLException {
        ArrayList<korisnik> rezultat = new ArrayList();
        String sql1 = "SELECT * FROM korisnik WHERE ";
        String sql = (opcija == 0) ? sql1 + " imeiprezime LIKE '" + uvjet + "*'" :sql1 + " imeprezime LIKE '" + uvjet + "*'";       
        Statement st = getKon().createStatement();
        ResultSet rs1 = st.executeQuery(sql);
        while (rs1.next()) 
        {
            korisnik kor = new korisnik();
            kor.setId(rs1.getInt("id"));
            kor.setImeiprezime(rs1.getString("imeiprezime"));
            kor.setUser(rs1.getString("user"));
            kor.setPass(rs1.getString("pass"));
            kor.setTip(rs1.getString("tip"));
            rezultat.add(kor);
        }
        getKon().close();
        return rezultat;
    }

    /**
     * metoda login je zaduzena za logovanje na sistem ukoliko su uneseni parametri tacni
     * pored toga ucitava iz baze sve podatke o zadanom korisniku
     * @param log predstavlja korisnicko ime
     * @param pass predstavlja korisnicku sifru
     * @return true ukoliko su podaci ispravni
     * @throws SQLException ukoliko nije bilo uspjesno povezivanje sa bazom podataka
     */
    public boolean login(String log, String pass) throws SQLException {
        boolean zastavica = false;
        String sql = "SELECT * FROM korisnik WHERE user = ? AND pass = ? ";
        PreparedStatement st = getKon().prepareStatement(sql);
        st.setString(1, log);
        st.setString(2, pass);
        ResultSet rs1 = st.executeQuery();
        while (rs1.next()) 
        {
            getKorisnik().setId(rs1.getInt("id"));
            getKorisnik().setImeiprezime(rs1.getString("imeiprezime"));
            getKorisnik().setUser(rs1.getString("user"));
            getKorisnik().setPass(rs1.getString("pass"));
            getKorisnik().setTip(rs1.getString("tip"));
            zastavica = true;
        }
        kon.close();
        return zastavica;
    }
   
    
}