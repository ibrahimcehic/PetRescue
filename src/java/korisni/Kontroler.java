package korisni;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import lombok.Getter;

/**
 * 
 * @author Amel Dzanic
 */
@Getter
public class Kontroler {
    
     /**     
     * @param dbPath string za povezivanje sa bazom podataka
     */

    protected final String      dbPath = "jdbc:sqlite:C:/tutorial/tutorial.db";
    protected Statement         stmt;
    protected ResultSet         rs;
    protected PreparedStatement ps;
    protected Connection        kon;
    

    public Kontroler() {
        try  {
            Class.forName("org.sqlite.JDBC").newInstance();
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException  ex){
            System.err.println("Greska u ucitavanju sqlite drivera!!!");          
            System.exit(1);
        }        
    }
    
    
    /**
     * Metoda koja služi za povezivanje sa bazom podataka. Ako se ne poveže
     * izbacuje izuzetak. Kao argument za povezivanje koristi se varijabla 
     * <code>dbPath</code>
     * @return objecat Connection
     * @throws SQLException ukoliko nije uspješno povezivanje sa bazom podataka
     */
    protected Connection getKon() throws SQLException {        
        kon=DriverManager.getConnection(dbPath);
        System.out.println("Konekcija otvorena");
        return kon;        
    }    
    
    /**
     * Kreira objekat Statement na osnovu zadate konekcije. Znači veoma
     * je važno da se prvo kreira objekat konekcije pomoću metode <br>
     * {@link setKon() },pa tek onda da se poziva ova metoda.
     * Način upotrebe dat je :
     * <p><code>
     * db.setKon();//prvo kreirati objekat konekcije<br>
     * db.setStmt();//zatim kreirati objekat Statement<br>
     * </code> gdje je db instanca klase koja je podklasa ove klase.
     * Ova klasa je apstraktna tako da nemože imati direktnu instancu.
     * @throws SQLException
     */
    public void setStmt() throws SQLException {
        stmt= kon.createStatement();
    }
    /**
     * Metoda <b><code>InsDelUpd</code></b> obavlja sve operacije sa
     * bazom što se tiče - isključivo sa alfanumeričkim podacima:
     * insertovanja sloga,
     * brisanja sloga,
     * te ažuriranja sloga u bazi podataka.
     * <p><b>Bitan je samo tacan sql upit!!!</b>
     * Treba još napomenuti da ova metoda sama sebi otvara i zatvara
     * konekciju sa bazom,tako da je programer oslobođen toga.
     * <p>Evo primjer:<br>
     * Neka imamo bazu sa samo dva polja jedno int id, a drugo varchar(45)
     * koje mi sluzi za unos imena. Tada bi za insert bio sljedeci kod:<br>
     * <code><br>
     * utilDB db = new utilDB();<br>
     * db.InsDelUpd("insert into test values(2,'Neko ime')");</code>
     * @param sql upit z Bazu
     * @throws SQLException
     */
    public void InsDelUpd (String sql) throws SQLException{
        try{            
            getKon().setAutoCommit(false);
            setStmt();           
            stmt.execute(sql);
            try{
                stmt.close();             
            }
            finally{stmt=null;}
            kon.commit();
        }
        catch (SQLException ex){
            kon.rollback();           
            throw ex;
        }
        finally {
            if(stmt!=null){
                try{stmt.close();}
                catch (SQLException ex){} //do nothing                
            }
            System.out.println("U konekciji sam terminiranja!");           
            zatvoriKonekciju();
        }
    }
    /**
     * Postavlja objekat RecordSeta-a kroz izvršenje
     * prosljeđenog upita.
     * <p>
     * <b>VAŽNO::</b>
     * <p>
     * Upotrebljva se na sljedeći način:
     * <p><code>
     * db.setKon();//prvo kreirati objekat konekcije<br>
     * db.setStmt();//zatim kreirati objekat Statement<br>
     * db.setRs("Select * from test");//onda poziv ove metode<br>
     * </code> Gdje je db instanca klase koja je podklasa ove klase
     * <p>
     * Pogledajte metode ove klase :<br>
     * {@link setKon() },<br>
     * {@link setStmt() }.
     * @param sql predstavlja upi koji kada kad e izvrši puni
     * objekat RecordSet-a
     * @throws SQLException 
     *
     */
    public void setRs(String sql) throws SQLException {
        this.rs=null;
        getKon();
        setStmt();
        this.rs = getStmt().executeQuery(sql);
        zatvoriKonekciju();
    }
    public ResultSet getRs(){
        return this.rs;
    }
    
    public void zatvoriKonekciju() throws SQLException{
        kon.close();
        System.out.println("zatvaram konekciju sa DB!");
    }
     public void InsDelUpdBatch (String[] sql) throws SQLException{
        try{
            //setKon();
            kon.setAutoCommit(false);
            setStmt();
            for (String string : sql) { getStmt().addBatch(string);}
            int [] count = getStmt().executeBatch();
            try{getStmt().close();}
            finally{stmt=null;}
            kon.commit();
        }
        catch (SQLException ex){
            kon.rollback();
            throw ex;
        }
        finally {
            if(getStmt()!=null){
                try{getStmt().close();}
                catch (SQLException ex){
                    //do nothing
                }
            }
            kon.close();
        }
    }

    
}